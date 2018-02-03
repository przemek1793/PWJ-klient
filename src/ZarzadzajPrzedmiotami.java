import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class ZarzadzajPrzedmiotami {
    private JPanel ZarządzajPrzedmiotami;
    private JComboBox PoleNazwa;
    private JTextField PoNazwisko;
    private JLabel Komunikat;
    private JButton UsunPrzedmiot;
    private JButton Wstecz;
    private JTextField PoleGodziny;
    private JTextField PoleIleStudentow;
    public Socket socket;
    static private JFrame frame;

    public ZarzadzajPrzedmiotami(Socket s) {
        socket = s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin ad = new MenuAdmin(socket);
                ad.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame = frame1;
        frame.setTitle("Zatwierdzanie użytkownika");
        frame.setContentPane(this.ZarządzajPrzedmiotami);
  //      zaladujUzytkownikow();
        frame.pack();
    }
}
