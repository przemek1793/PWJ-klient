import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
                try
                {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.println("wylogowanie");
                    out.flush();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                MainMenu menu= new MainMenu(socket);
                menu.go(frame);
            }
        });
        ZmienDane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.println("zmiana_danych");
                    out.flush();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                ZmienDane zmiana= new ZmienDane(socket);
                zmiana.go(frame);
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
