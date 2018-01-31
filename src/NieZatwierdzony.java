import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class NieZatwierdzony {
    private JPanel NieZatwierdzony;
    private JButton Wstecz;
    public Socket socket;
    static private JFrame frame;

    public NieZatwierdzony(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu= new MainMenu(socket);
                menu.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Dodawanie nowego przedmiotu");
        frame.setContentPane(this.NieZatwierdzony);
        frame.pack();
    }
}
