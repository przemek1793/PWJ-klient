import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ProwadzNoweZajecia {
    private JPanel ProwadzNowezajecia;
    private JComboBox PoleNazwa;
    private JButton ProwadzNowe;
    private JButton Wstecz;
    private JLabel Komunikat;
    public Socket socket;
    static private JFrame frame;

    public ProwadzNoweZajecia(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuProwadzacy pr = new MenuProwadzacy(s);
                pr.go(frame);
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Prowadzenie nowego przedmiotu");
        frame.setContentPane(this.ProwadzNowezajecia);
        zaladujZajecia();
        frame.pack();
    }

    private void zaladujZajecia ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista_zajec");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int ileZajec = Integer.parseInt(in.readLine());
            for (int i=0; i<ileZajec; i++)
            {
                String nazwaZajec=in.readLine();
                PoleNazwa.addItem(nazwaZajec);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
