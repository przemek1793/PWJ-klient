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
        Zatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zatwierdz();
            }
        });
        Szczegóły.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SzczegoloweInformacjeUzytkownika szczegoly= new SzczegoloweInformacjeUzytkownika(socket,String.valueOf(PoleLogin.getSelectedItem()));
                szczegoly.go(frame);
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
            PoleLogin.removeAllItems();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista niezatwierdzonych");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int iluNiezatwierdzonych = Integer.parseInt(in.readLine());
            if (iluNiezatwierdzonych==0)
                PoleLogin.addItem("Nie ma niezatwierdzonych użytkowników");
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

    private void zatwierdz ()
    {
        try
        {
            String login = String.valueOf(PoleLogin.getSelectedItem());
            if (login.equals("Nie ma niezatwierdzonych użytkowników"))
            {
                Komunikat.setText("Nie ma kogo zatwierdzac");
            }
            else
            {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("zatwierdzanie uzytkownika");
                out.println(login);
                out.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String odpowiedz=in.readLine();
                if (odpowiedz.equals("ok"))
                {
                    Komunikat.setText("Zatwierdzono użytkownika");
                    zaladujNiezatwierdzonych();
                }
                if (odpowiedz.equals("bledne"))
                {
                    Komunikat.setText("Nie zatwierdzono użytkownika");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
