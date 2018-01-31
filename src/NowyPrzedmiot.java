import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
        zaladujProwadzacych();
        frame.pack();
    }

    private void zaladujProwadzacych ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista_prowadzacych");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int iluProwadzacych = Integer.parseInt(in.readLine());
            for (int i=0; i<iluProwadzacych; i++)
            {
                String nazwiskoProwadzacego=in.readLine();
                PoleNazwisko.addItem(nazwiskoProwadzacego);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
