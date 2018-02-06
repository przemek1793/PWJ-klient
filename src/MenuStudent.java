import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MenuStudent {
    private JButton SprawdzPlan;
    private JButton ZmienDane;
    private JButton ZapiszNaZajecia;
    private JButton Wyloguj;
    private JPanel MenuSt;
    public Socket socket;
    static private JFrame frame;

    public MenuStudent(Socket s) {
        socket=s;
        Wyloguj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.println("wylogowanie");
                    out.flush();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                MainMenu menu= new MainMenu(socket);
                menu.go(frame);
            }
        });
        ZmienDane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ZmienDane zmiana= new ZmienDane(socket, "student");
                zmiana.go(frame);
            }
        });
        ZapiszNaZajecia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZapisywanieNaZajecia zapisz = new ZapisywanieNaZajecia(socket);
                zapisz.go(frame);
            }
        });
        SprawdzPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlanLekcji lekcje = new PlanLekcji(socket,"student");
                lekcje.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame = frame1;
        frame.setTitle("Student");
        frame.setContentPane(this.MenuSt);
        frame.pack();
    }
}
