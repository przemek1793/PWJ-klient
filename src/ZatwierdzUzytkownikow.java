import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZatwierdzUzytkownikow {
    private JPanel ZatwiedzUzytkownikow;
    private JComboBox PoleLogin;
    private JButton Zatwierdz;
    private JButton Wstecz;
    private JButton Szczegóły;
    private JLabel Komunikat;
    public Socket socket;
    static private JFrame frame;

    public ZatwierdzUzytkownikow(Socket s) {
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
        frame.setContentPane(this.ZatwiedzUzytkownikow);
        zaladujNiezatwierdzonych();
        frame.pack();
    }

    private void zaladujNiezatwierdzonych ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista niezatwierdzonych");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int iluNiezatwierdzonych = Integer.parseInt(in.readLine());
            for (int i=0; i<iluNiezatwierdzonych; i++)
            {
                String login=in.readLine();
                PoleLogin.addItem(login);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
