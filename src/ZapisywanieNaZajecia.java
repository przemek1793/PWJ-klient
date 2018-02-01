import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZapisywanieNaZajecia {
    private JPanel ZapisywanieNaZajecia;
    private JButton Zapisz;
    private JButton Wstecz;
    private JComboBox PoleNazwa;
    private JLabel Komunikat;
    public Socket socket;
    static private JFrame frame;

    public ZapisywanieNaZajecia(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuStudent st = new MenuStudent(socket);
                st.go(frame);
            }
        });
        Zapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zapiszNaZajecia();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Zapisywanie na zajęcia");
        frame.setContentPane(this.ZapisywanieNaZajecia);
        zaladujZajecia();
        frame.pack();
    }

    private void zaladujZajecia ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista zajec na ktore nie jest zapisany");
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

    private void zapiszNaZajecia ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("zapisz na zajecia");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String uprawnienia = in.readLine();
            if (uprawnienia.equals("brak uprawnien"))
            {
                Komunikat.setText("Brak uprawnień do zapisania się na zajęcia");
            }
            else
            {
                String nazwa = String.valueOf(PoleNazwa.getSelectedItem());
                out.println(nazwa);
                out.flush();
                String odpowiedz=in.readLine();
                if (odpowiedz.equals("ok"))
                {
                    Komunikat.setText("Zmiana została przesłana do administratora w celu akceptacji");
                }
                if (odpowiedz.equals("bledne"))
                {
                    Komunikat.setText("Nie zapisano na zajęcia");
                }
                if (odpowiedz.equals("duplikat"))
                {
                    Komunikat.setText("Już oczekujesz na akceptacje administratora");
                }
                if (odpowiedz.equals("duplikat2"))
                {
                    Komunikat.setText("Jesteś już zapisany na przedmiot");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
