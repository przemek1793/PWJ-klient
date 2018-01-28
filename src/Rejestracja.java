import javax.swing.*;

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

    public void go(JFrame frame) {
        frame.setTitle("Rejestracja");
        frame.setContentPane(new Rejestracja().Rejestracja);
        frame.pack();
    }
}
