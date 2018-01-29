import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Logowanie");
        frame.setContentPane(new Logowanie(socket).Logowanie);
        frame.pack();
    }
}
