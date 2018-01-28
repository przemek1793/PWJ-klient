import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JButton Rejestracja;
    private JPanel MenuM;
    private JButton Logowanie;
    static private JFrame frame;

    public MainMenu() {
        Rejestracja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rejestracja rej= new Rejestracja();
                rej.go(frame);
            }
        });
    }

    public void go() {
        frame = new JFrame("PWJ-grafik zajęć");
        frame.setContentPane(new MainMenu().MenuM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("PWJ-grafik zajęć");
        frame.setContentPane(new MainMenu().MenuM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
