import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class MenuProwadzacy {
    private JButton SprawdzPlan;
    private JButton Wyloguj;
    private JButton ZmienDane;
    private JButton ZapiszProwadzenie;
    private JButton ZmienGodziny;
    private JPanel MenuPR;
    public Socket socket;
    static private JFrame frame;

    public MenuProwadzacy(Socket s) {
        socket=s;
        Wyloguj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu= new MainMenu(socket);
                menu.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame = frame1;
        frame.setTitle("Prowadzący");
        frame.setContentPane(new MenuProwadzacy(socket).MenuPR);
        frame.pack();
    }
}
