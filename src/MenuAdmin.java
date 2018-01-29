import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class MenuAdmin {
    private JButton Wyloguj;
    private JButton NowyPrzedmiot;
    private JButton Zatwierdz;
    private JButton ZmienDane;
    private JPanel MenuAd;
    public Socket socket;
    static private JFrame frame;

    public MenuAdmin(Socket s) {
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
        frame=frame1;
        frame.setTitle("Administrator");
        frame.setContentPane(new MenuAdmin(socket).MenuAd);
        frame.pack();
    }

}
