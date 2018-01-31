import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class NowyPrzedmiot {
    private JPanel NowyPrzedmiot;
    private JTextField PoleNazwa;
    private JComboBox PoleNazwisko;
    private JButton DodajNowy;
    private JButton Wstecz;
    private JLabel Komunikat;
    public Socket socket;
    static private JFrame frame;

    public NowyPrzedmiot(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin ad = new MenuAdmin(socket);
                ad.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Dodawanie nowego przedmiotu");
        frame.setContentPane(this.NowyPrzedmiot);
        frame.pack();
    }
}
