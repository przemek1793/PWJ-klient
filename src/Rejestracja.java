import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Rejestracja() {
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu= new MainMenu();
                menu.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Rejestracja");
        frame.setContentPane(new Rejestracja().Rejestracja);
        frame.pack();
    }
}
