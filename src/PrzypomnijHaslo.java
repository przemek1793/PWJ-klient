import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Przypominanie hasła");
        frame.setContentPane(new PrzypomnijHaslo(socket).Przypomnienie);
        frame.pack();
    }
}
