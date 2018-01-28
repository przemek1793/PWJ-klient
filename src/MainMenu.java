import javax.swing.*;

public class MainMenu {
    private JButton Rejestracja;
    private JPanel MenuM;
    private JButton Logowanie;

    public static void go() {
        JFrame frame = new JFrame("PWJ-grafik zajęć");
        frame.setContentPane(new MainMenu().MenuM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
