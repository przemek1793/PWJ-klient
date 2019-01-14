import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZarzadzajPrzedmiotami {
    private JPanel ZarządzajPrzedmiotami;
    private JComboBox PoleNazwa;
    private JTextField PoleNazwisko;
    private JLabel Komunikat;
    private JButton UsunPrzedmiot;
    private JButton Wstecz;
    private JTextField PoleGodziny;
    private JTextField PoleIleStudentow;
    private JButton ListaStudentow;
    public Socket socket;
    static private JFrame frame;

    public ZarzadzajPrzedmiotami(Socket s) {
        socket = s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin ad = new MenuAdmin(socket);
                ad.go(frame);
            }
        });
        PoleNazwa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ComboboxItem aktualny =(ComboboxItem) PoleNazwa.getSelectedItem();
                    PoleGodziny.setText(aktualny.godziny);
                    PoleIleStudentow.setText(aktualny.iloscZapisanych);
                    PoleNazwisko.setText(aktualny.nazwisko);
                } catch (NullPointerException e1) {
                    PoleGodziny.setText("");
                    PoleIleStudentow.setText("");
                    PoleNazwisko.setText("");
                }
            }
        });
        UsunPrzedmiot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usunZajecia();
            }
        });
    }

    public void go(JFrame frame1) {
        frame = frame1;
        frame.setTitle("Zarządzanie przedmiotami");
        frame.setContentPane(this.ZarządzajPrzedmiotami);
        zaladujZajecia();
        frame.pack();
    }

    private void zaladujZajecia ()
    {
        try
        {
            PoleNazwa.removeAllItems();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista zajec ze szczegolowymi informacjami");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int ileZajec = Integer.parseInt(in.readLine());
            if (ileZajec==0)
            {
                PoleNazwa.addItem("Nie ma żadnych zajęć");
            }
            for (int i = 0; i < ileZajec; i++) {
                String nazwa = in.readLine();
                String nazwisko = in.readLine();
                String godziny = in.readLine();
                String iloscZapisanych = in.readLine();
                ComboboxItem item = new ComboboxItem(nazwa, nazwisko, godziny, iloscZapisanych);
                PoleNazwa.addItem(item);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class ComboboxItem {
        public String nazwa;
        public String nazwisko;
        public String godziny;
        public String iloscZapisanych;

        ComboboxItem(String nazwa, String nazwisko, String godziny, String iloscZapisanych) {
            this.nazwa = nazwa;
            this.nazwisko = nazwisko;
            this.godziny = godziny;
            this.iloscZapisanych = iloscZapisanych;
        }

        public String toString() {
            return nazwa;

        }
    }

    private void usunZajecia ()
    {
        try {
            String nazwa = String.valueOf(PoleNazwa.getSelectedItem());
            if (nazwa.equals("Nie ma żadnych zajęć")) {
                Komunikat.setText("Nie ma zajęć do usunięcia");
            } else {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("usuwanie zajec");
                out.println(nazwa);
                out.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String odpowiedz = in.readLine();
                if (odpowiedz.equals("ok")) {
                    Komunikat.setText("Usunięto przedmiot");
                    zaladujZajecia();
                }
                if (odpowiedz.equals("nie usunieto")) {
                    Komunikat.setText("Nie usunięto przedmiotu");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
