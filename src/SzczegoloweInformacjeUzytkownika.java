import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SzczegoloweInformacjeUzytkownika {
    private JPanel SzczegoloweInformacje;
    private JTextField PoleLogin;
    private JTextField PoleImie;
    private JTextField PoleNazwisko;
    private JTextField PoleEmail;
    private JTextField PoleTyp;
    private JButton Wstecz;
    public Socket socket;
    static private JFrame frame;
    String login;

    public SzczegoloweInformacjeUzytkownika(Socket s, String login) {
        this.login=login;
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZatwierdzUzytkownikow zatwierdz=new ZatwierdzUzytkownikow(socket);
                zatwierdz.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Dodawanie nowego przedmiotu");
        frame.setContentPane(this.SzczegoloweInformacje);
        zaladujSzczegolyLogin();
        frame.pack();
    }

    private void zaladujSzczegolyLogin ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("login szczegoly");
            out.println(login);
            out.flush();

            String imie, nazwisko, email, typ;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String odpowiedz=in.readLine();
            if (odpowiedz.equals("ok"))
            {
                imie=in.readLine();
                nazwisko=in.readLine();
                email=in.readLine();
                typ=in.readLine();
                PoleLogin.setText(login);
                PoleImie.setText(imie);
                PoleNazwisko.setText(nazwisko);
                PoleEmail.setText(email);
                PoleTyp.setText(typ);
            }
            else
            {
                PoleLogin.setText(login);
                PoleImie.setText("bląd przy połączeniu z bazą");
                PoleNazwisko.setText("bląd przy połączeniu z bazą");
                PoleEmail.setText("bląd przy połączeniu z bazą");
                PoleTyp.setText("bląd przy połączeniu z bazą");
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
