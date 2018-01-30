import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZmienDane {
    private JPanel ZmienDane;
    private JTextField LoginPole;
    private JTextField HasłoPole;
    private JTextField ImiePole;
    private JTextField NazwiskoPole;
    private JTextField EmailPole;
    private JRadioButton radioStudent;
    private JRadioButton radioProwadzacy;
    private JRadioButton radioAdministrator;
    private JLabel Komunikat;
    private JButton Zmiana;
    private JButton Wstecz;
    static private JFrame frame;
    public Socket socket;
    private String typ;

    public ZmienDane(Socket s, String typ) {
        this.typ=typ;
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typ.equals("student"))
                {
                    MenuStudent st = new MenuStudent(socket);
                    st.go(frame);
                }
                else if (typ.equals("prowadzacy"))
                {
                    MenuProwadzacy pr = new MenuProwadzacy(socket);
                    pr.go(frame);
                }
                else if (typ.equals("admin"))
                {
                    MenuAdmin ad = new MenuAdmin(socket);
                    ad.go(frame);
                }
                else
                {
                    MainMenu menu= new MainMenu(socket);
                    menu.go(frame);
                }
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Zmiana danych");
        frame.setContentPane(new ZmienDane(socket,typ).ZmienDane);
        frame.pack();
    }

    private void zmienDane ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("zmiana_danych");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
