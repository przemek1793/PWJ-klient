import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Rejestracja {
    private JPanel Rejestracja;
    private JTextField LoginPole;
    private JTextField HasłoPole;
    private JTextField ImięPole;
    private JTextField NazwiskoPole;
    private JTextField EmailPole;
    private JRadioButton radioStudent;
    private JRadioButton radioProwadzący;
    private JRadioButton radioAdministrator;
    private JLabel Rejestrajca;
    private JButton Rejestruj;
    private JButton Wstecz;
    private JLabel Komunikat;
    static private JFrame frame;
    public Socket socket;

    public Rejestracja(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu= new MainMenu(socket);
                menu.go(frame);
            }
        });
        Rejestruj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rejestruj();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Rejestracja");
        frame.setContentPane(new Rejestracja(socket).Rejestracja);
        frame.pack();
    }

    private void rejestruj()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("rejestracja");
            String login=LoginPole.getText();
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
            String Imie=ImięPole.getText();
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
            String typ="";
            if (radioAdministrator.isSelected())
                typ=radioAdministrator.getText();
            if (radioProwadzący.isSelected())
                typ=radioProwadzący.getText();
            if (radioStudent.isSelected())
                typ=radioStudent.getText();
            if (typ.equals(""))
            {
                Komunikat.setText("Nie wybrano typu użytkownika");
                return;
            }
            out.println(typ);
            MainMenu menu= new MainMenu(socket);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String odpowiedz=in.readLine();
            if (odpowiedz.equals("ok"))
            {
                Komunikat.setText("Stworzono nowe konto o podanych danych");
            }
            if (odpowiedz.equals("bledne"))
            {
                Komunikat.setText("Nie zarejestrowano użytkownika");
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
