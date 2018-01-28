import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Rejestracja");
        frame.setContentPane(new Rejestracja(socket).Rejestracja);
        frame.pack();
    }
}
