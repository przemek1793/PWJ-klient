import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
                try
                {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.println("koniec");
                    out.flush();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
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
        Zmiana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zmienDane();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Zmiana danych");
        frame.setContentPane(this.ZmienDane);
        zaladujDane();
        frame.pack();
    }

    private void zaladujDane ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("zmiana_danych");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            LoginPole.setText(in.readLine());
            HasłoPole.setText(in.readLine());
            ImiePole.setText(in.readLine());
            NazwiskoPole.setText(in.readLine());
            EmailPole.setText(in.readLine());
            if (typ.equals("student"))
            {
                radioStudent.setSelected(true);
            }
            else if (typ.equals("prowadzacy"))
            {
                radioProwadzacy.setSelected(true);
            }
            else if (typ.equals("admin"))
            {
                radioAdministrator.setSelected(true);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void zmienDane ()
    {
    }
}
