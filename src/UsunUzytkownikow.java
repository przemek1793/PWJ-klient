import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class UsunUzytkownikow {
    private JPanel UsunUzytkownikow;
    private JComboBox PoleLogin;
    private JTextField PoleImie;
    private JTextField PoleNazwisko;
    private JTextField PoleEmail;
    private JTextField PoleTyp;
    private JLabel Komunikat;
    private JTextField PoleZatwierdzony;
    private JButton UsunUzytkownika;
    private JButton Wstecz;
    public Socket socket;
    static private JFrame frame;

    public UsunUzytkownikow(Socket s) {
        socket = s;
        UsunUzytkownika.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usun();
            }
        });
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin ad = new MenuAdmin(socket);
                ad.go(frame);
            }
        });
        PoleLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ComboboxItem aktualny = (ComboboxItem) PoleLogin.getSelectedItem();
                    PoleEmail.setText(aktualny.email);
                    PoleImie.setText(aktualny.imie);
                    PoleTyp.setText(aktualny.typ);
                    PoleNazwisko.setText(aktualny.nazwisko);
                    PoleZatwierdzony.setText(aktualny.czyZatwierdzony);
                } catch (NullPointerException e1) {
                    PoleEmail.setText("");
                    PoleImie.setText("");
                    PoleTyp.setText("");
                    PoleNazwisko.setText("");
                    PoleZatwierdzony.setText("");
                }
            }
        });
    }

    public void go(JFrame frame1) {
        frame = frame1;
        frame.setTitle("Zatwierdzanie użytkownika");
        frame.setContentPane(this.UsunUzytkownikow);
        zaladujUzytkownikow();
        frame.pack();
    }

    private void zaladujUzytkownikow() {
        try {
            PoleLogin.removeAllItems();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista uzytkownikow");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int iluNiezatwierdzonych = Integer.parseInt(in.readLine());
            if (iluNiezatwierdzonych == 0)
                PoleLogin.addItem("Nie ma użytkowników");
            for (int i = 0; i < iluNiezatwierdzonych; i++) {
                String login = in.readLine();
                String imie = in.readLine();
                String nazwisko = in.readLine();
                String email = in.readLine();
                String typ = in.readLine();
                String czyZatwierdzony = in.readLine();
                ComboboxItem item = new ComboboxItem(login, imie, nazwisko, email, typ, czyZatwierdzony);
                PoleLogin.addItem(item);
            }
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void usun() {
        try {
            String login = String.valueOf(PoleLogin.getSelectedItem());
            if (login.equals("Nie ma użytkowników")) {
                Komunikat.setText("Nie ma kogo usuwać");
            } else {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("usuwanie uzytkownika");
                out.println(login);
                out.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String odpowiedz = in.readLine();
                if (odpowiedz.equals("ok")) {
                    Komunikat.setText("Usunięto użytkownika");
                    zaladujUzytkownikow();
                }
                if (odpowiedz.equals("nie usunieto zmiany")) {
                    Komunikat.setText("Nie usunięto użytkownika");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ComboboxItem {
        public String login;
        public String imie;
        public String nazwisko;
        public String email;
        public String typ;
        public String czyZatwierdzony;

        ComboboxItem(String login, String imie, String nazwisko, String email, String typ, String czyZatwierdzony) {
            this.login = login;
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.email = email;
            this.typ = typ;
            if (czyZatwierdzony.equals("1"))
            {
                this.czyZatwierdzony = "zatwierdzony";
            }
            else
            {
                this.czyZatwierdzony = "niezatwierdzony";
            }
        }

        public String toString() {
            return login;

        }
    }
}
