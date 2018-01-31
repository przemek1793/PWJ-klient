import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class NowyPrzedmiot {
    private JPanel NowyPrzedmiot;
    private JTextField PoleNazwa;
    private JComboBox PoleNazwisko;
    private JButton DodajNowy;
    private JButton Wstecz;
    private JLabel Komunikat;
    public Socket socket;
    static private JFrame frame;

    public NowyPrzedmiot(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin ad = new MenuAdmin(socket);
                ad.go(frame);
            }
        });
        DodajNowy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajPrzedmiot();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Dodawanie nowego przedmiotu");
        frame.setContentPane(this.NowyPrzedmiot);
        zaladujProwadzacych();
        frame.pack();
    }

    private void zaladujProwadzacych ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista_prowadzacych");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int iluProwadzacych = Integer.parseInt(in.readLine());
            for (int i=0; i<iluProwadzacych; i++)
            {
                String nazwiskoProwadzacego=in.readLine();
                PoleNazwisko.addItem(nazwiskoProwadzacego);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void dodajPrzedmiot ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("nowy_przedmiot");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String uprawnienia = in.readLine();
            if (uprawnienia.equals("brak uprawnien"))
            {
                Komunikat.setText("Brak uprawnień do dodawania przedmiotów");
            }
            else
            {
                String nazwa=PoleNazwa.getText();
                if (nazwa.equals(""))
                {
                    Komunikat.setText("Pole Nazwa jest puste");
                    return;
                }
                out.println(nazwa);
                String nazwisko = String.valueOf(PoleNazwisko.getSelectedItem());
                out.println(nazwisko);
                out.flush();
                String odpowiedz=in.readLine();
                if (odpowiedz.equals("ok"))
                {
                    Komunikat.setText("Stworzono nowy przedmiot o podanych danych");
                }
                if (odpowiedz.equals("bledne"))
                {
                    Komunikat.setText("Nie stworzono przedmiotu");
                }
                if (odpowiedz.equals("duplikat"))
                {
                    Komunikat.setText("Przedmiot o podanej nazwie już istnieje");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
