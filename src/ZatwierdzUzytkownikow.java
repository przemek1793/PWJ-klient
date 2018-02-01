import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZatwierdzUzytkownikow {
    private JPanel ZatwiedzUzytkownikow;
    private JComboBox PoleLogin;
    private JButton Zatwierdz;
    private JButton Wstecz;
    private JButton Szczegóły;
    private JLabel Komunikat;
    private JButton UsunUzytkownika;
    private JTextField PoleImie;
    private JTextField PoleNazwisko;
    private JTextField PoleEmail;
    private JTextField PoleTyp;
    public Socket socket;
    static private JFrame frame;

    public ZatwierdzUzytkownikow(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin ad = new MenuAdmin(socket);
                ad.go(frame);
            }
        });
        Zatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zatwierdz();
            }
        });
        PoleLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    ComboboxItem aktualny =(ComboboxItem) PoleLogin.getSelectedItem();
                    PoleEmail.setText(aktualny.email);
                    PoleImie.setText(aktualny.imie);
                    PoleTyp.setText(aktualny.typ);
                    PoleNazwisko.setText(aktualny.nazwisko);
                }
                catch (NullPointerException e1)
                { }
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Dodawanie nowego przedmiotu");
        frame.setContentPane(this.ZatwiedzUzytkownikow);
        zaladujNiezatwierdzonych();
        frame.pack();
    }

    private void zaladujNiezatwierdzonych ()
    {
        try
        {
            PoleLogin.removeAllItems();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista niezatwierdzonych");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int iluNiezatwierdzonych = Integer.parseInt(in.readLine());
            if (iluNiezatwierdzonych==0)
                PoleLogin.addItem("Nie ma niezatwierdzonych użytkowników");
            for (int i=0; i<iluNiezatwierdzonych; i++)
            {
                String login=in.readLine();
                String imie=in.readLine() ;
                String nazwisko=in.readLine() ;
                String email=in.readLine() ;
                String typ=in.readLine() ;
                ComboboxItem item = new ComboboxItem(login,imie,nazwisko,email,typ);
                PoleLogin.addItem(item);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void zatwierdz ()
    {
        try
        {
            String login = String.valueOf(PoleLogin.getSelectedItem());
            if (login.equals("Nie ma niezatwierdzonych użytkowników"))
            {
                Komunikat.setText("Nie ma kogo zatwierdzac");
            }
            else
            {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("zatwierdzanie uzytkownika");
                out.println(login);
                out.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String odpowiedz=in.readLine();
                if (odpowiedz.equals("ok"))
                {
                    Komunikat.setText("Zatwierdzono użytkownika");
                    zaladujNiezatwierdzonych();
                }
                if (odpowiedz.equals("bledne"))
                {
                    Komunikat.setText("Nie zatwierdzono użytkownika");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class ComboboxItem
    {
        public String login;
        public String imie ;
        public String nazwisko ;
        public String email ;
        public String typ ;

        ComboboxItem (String login, String imie, String nazwisko, String email, String typ)
        {
            this.login=login;
            this.imie=imie;
            this.nazwisko=nazwisko;
            this.email=email;
            this.typ=typ;
        }

        public  String toString()
        {
            return login;
        }
    }
}
