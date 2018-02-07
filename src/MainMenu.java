import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class MainMenu {
    private JButton Rejestracja;
    private JPanel MenuM;
    private JButton Logowanie;
    private JButton Przypomnienie;
    static private JFrame frame;
    public Socket socket;

    public MainMenu(Socket s) {
        socket=s;
        Rejestracja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rejestracja rej= new Rejestracja(socket);
                rej.go(frame);
            }
        });
        Logowanie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Logowanie log = new Logowanie(socket);
                log.go(frame);
            }
        });
        Przypomnienie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrzypomnijHaslo przyp = new PrzypomnijHaslo(socket);
                przyp.go(frame);
            }
        });
    }

    public void go() {
        frame = new JFrame("PWJ-grafik zajęć");
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setContentPane(this.MenuM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("PWJ-grafik zajęć");
        frame.setContentPane(this.MenuM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
