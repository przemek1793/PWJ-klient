import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class PrzypomnijHaslo {
    private JPanel Przypomnienie;
    private JTextField PoleEmail;
    private JButton Przypomnij;
    private JButton Wstecz;
    private JLabel Komunikat;
    static private JFrame frame;
    public Socket socket;

    public PrzypomnijHaslo(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu= new MainMenu(socket);
                menu.go(frame);
            }
        });
        Przypomnij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                przypomnij();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Przypominanie hasła");
        frame.setContentPane(new PrzypomnijHaslo(socket).Przypomnienie);
        frame.pack();
    }

    private void przypomnij ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("przypomnienie");
            String email=PoleEmail.getText();
            if (email.equals(""))
            {
                Komunikat.setText("Pole Email jest puste");
                return;
            }
            out.println(email);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String odpowiedz=in.readLine();
            if (odpowiedz.equals("poprawne"))
            {
                Komunikat.setText("Wysłano email z hasłem na podany adres");
            }
            else
            {
                Komunikat.setText("Brak podanego adresu email w bazie danych");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
