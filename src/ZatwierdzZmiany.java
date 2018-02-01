import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZatwierdzZmiany {
    private JPanel ZatwierdzZmiany;
    private JComboBox ZmianaCombo;
    private JTextField PoleWartosc;
    private JTextField PoleKolumna;
    private JTextField PoleKlucz;
    private JTextField PoleTabela;
    private JButton Wstecz;
    private JButton Zmiana;
    public Socket socket;
    static private JFrame frame;

    public ZatwierdzZmiany(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuAdmin ad = new MenuAdmin(socket);
                ad.go(frame);
            }
        });
        ZmianaCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboboxItem aktualny =(ComboboxItem) ZmianaCombo.getSelectedItem();
                PoleTabela.setText(aktualny.Tabela);
                PoleKlucz.setText(aktualny.Klucz);
                PoleKolumna.setText(aktualny.KolumnaDoZmiany);
                PoleWartosc.setText(aktualny.NowaWartosc);
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Dodawanie nowego przedmiotu");
        frame.setContentPane(this.ZatwierdzZmiany);
        zaladujZmiany();
        frame.pack();
    }

    private void zaladujZmiany ()
    {
        try
        {
            ZmianaCombo.removeAllItems();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista zmian");
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int ileZmian = Integer.parseInt(in.readLine());
            if (ileZmian==0)
                ZmianaCombo.addItem("Nie ma żadnych niezatwierdzonych zmian");
            for (int i=0; i<ileZmian; i++)
            {
                String nazwa= "zmiana"+(i+1);
                String Tabela =in.readLine();
                String Klucz =in.readLine();
                String KolumnaDoZmiany =in.readLine();
                String NowaWartosc =in.readLine();
                ComboboxItem item = new ComboboxItem(nazwa,Tabela,Klucz,KolumnaDoZmiany,NowaWartosc);
                ZmianaCombo.addItem(item);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class ComboboxItem
    {
        public String nazwa;
        public String Tabela ;
        public String Klucz ;
        public String KolumnaDoZmiany ;
        public String NowaWartosc ;

        ComboboxItem (String nazwa, String Tabela, String Klucz, String KolumnaDoZmiany, String NowaWartosc)
        {
            this.nazwa=nazwa;
            this.Tabela=Tabela;
            this.Klucz=Klucz;
            this.KolumnaDoZmiany=KolumnaDoZmiany;
            this.NowaWartosc=NowaWartosc;
        }

        public  String toString()
    {
        return nazwa;
    }
    }
}
