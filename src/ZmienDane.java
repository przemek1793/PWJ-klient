import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class ZmienDane {
    private JPanel ZmienDane;
    private JTextField LoginPole;
    private JTextField HasłoPole;
    private JTextField ImiePole;
    private JTextField NazwiskoPole;
    private JTextField EmailPole;
    private JRadioButton radioStudent;
    private JRadioButton radioProwadzacy;
    private JRadioButton radioAdministrator;
    private JLabel Komunikat;
    private JButton Zmiana;
    private JButton Wstecz;
    static private JFrame frame;
    public Socket socket;

    public ZmienDane(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Zmiana danych");
        frame.setContentPane(new ZmienDane(socket).ZmienDane);
        frame.pack();
    }
}
