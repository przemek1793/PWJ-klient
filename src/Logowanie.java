import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Logowanie {
    private JPanel Logowanie;
    private JButton LogButton;
    private JButton Wstecz;
    private JTextField PoleHasło;
    private JTextField PoleLogin;
    private JLabel Komunikat;
    static private JFrame frame;
    public Socket socket;

    public Logowanie(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu= new MainMenu(socket);
                menu.go(frame);
            }
        });
        LogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loguj();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Logowanie");
        frame.setContentPane(new Logowanie(socket).Logowanie);
        frame.pack();
    }

    private void loguj()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("logowanie");
            String login=PoleLogin.getText();
            if (login.equals(""))
            {
                Komunikat.setText("Pole Login jest puste");
                return;
            }
            out.println(login);
            String haslo=PoleHasło.getText();
            if (haslo.equals(""))
            {
                Komunikat.setText("Pole Hasło jest puste");
                return;
            }
            out.println(haslo);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String odpowiedz=in.readLine();
            if (odpowiedz.equals("poprawne"))
            {
                MainMenu menu= new MainMenu(socket); //zamień potem menu na to na co ma przechodzić
                menu.go(frame);
            }
            else
            {
                Komunikat.setText("Niepoprawne dane");
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
