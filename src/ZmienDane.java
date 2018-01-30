import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZmienDane {
    private JPanel ZmienDane;
    private JTextField LoginPole;
    private JTextField HasłoPole;
    private JTextField ImiePole;
    private JTextField NazwiskoPole;
    private JTextField EmailPole;
    private JLabel Komunikat;
    private JButton Zmiana;
    private JButton Wstecz;
    static private JFrame frame;
    public Socket socket;
    private String typ;

    public ZmienDane(Socket s, String typ) {
        this.typ=typ;
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typ.equals("student"))
                {
                    MenuStudent st = new MenuStudent(socket);
                    st.go(frame);
                }
                else if (typ.equals("prowadzacy"))
                {
                    MenuProwadzacy pr = new MenuProwadzacy(socket);
                    pr.go(frame);
                }
                else if (typ.equals("admin"))
                {
                    MenuAdmin ad = new MenuAdmin(socket);
                    ad.go(frame);
                }
                else
                {
                    MainMenu menu= new MainMenu(socket);
                    menu.go(frame);
                }
            }
        });
        Zmiana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zmienDane();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Zmiana danych");
        frame.setContentPane(this.ZmienDane);
        zaladujDane();
        frame.pack();
    }

    private void zaladujDane ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("zmiana_danych");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            LoginPole.setText(in.readLine());
            HasłoPole.setText(in.readLine());
            ImiePole.setText(in.readLine());
            NazwiskoPole.setText(in.readLine());
            EmailPole.setText(in.readLine());
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void zmienDane ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String login=LoginPole.getText();
            out.println("zmiana");
            if (login.equals(""))
            {
                Komunikat.setText("Pole Login jest puste");
                return;
            }
            out.println(login);
            String haslo=HasłoPole.getText();
            if (haslo.equals(""))
            {
                Komunikat.setText("Pole Hasło jest puste");
                return;
            }
            out.println(haslo);
            String Imie=ImiePole.getText();
            if (Imie.equals(""))
            {
                Komunikat.setText("Pole Imię jest puste");
                return;
            }
            out.println(Imie);
            String Nazwisko=NazwiskoPole.getText();
            if (Nazwisko.equals(""))
            {
                Komunikat.setText("Pole Nazwisko jest puste");
                return;
            }
            out.println(Nazwisko);
            String email=EmailPole.getText();
            if (email.equals(""))
            {
                Komunikat.setText("Pole Email jest puste");
                return;
            }
            if (!email.contains("@") || !email.contains("."))
            {
                Komunikat.setText("Niepoprawny adres email");
                return;
            }
            out.println(email);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String odpowiedz=in.readLine();
            if (odpowiedz.equals("ok"))
            {
                Komunikat.setText("Zmieniono dane");
            }
            if (odpowiedz.equals("bledne"))
            {
                Komunikat.setText("Nie zmieniono danych");
            }
            if (odpowiedz.equals("duplikat"))
            {
                Komunikat.setText("Podany login lub email już znajdują się w naszej bazie danych");
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
