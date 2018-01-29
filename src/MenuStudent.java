import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class MenuStudent {
    private JButton SprawdzPlan;
    private JButton ZmienDane;
    private JButton ZapiszNaZajecia;
    private JButton Wyloguj;
    private JPanel MenuSt;
    public Socket socket;
    static private JFrame frame;

    public MenuStudent(Socket s) {
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
        frame.setTitle("Student");
        frame.setContentPane(new MenuStudent(socket).MenuSt);
        frame.pack();
    }
}
