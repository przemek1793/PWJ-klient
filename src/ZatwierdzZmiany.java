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
    private JLabel Komunikat;
    private JButton UsunZmiane;
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
                try
                {
                    ComboboxItem aktualny =(ComboboxItem) ZmianaCombo.getSelectedItem();
                    PoleTabela.setText(aktualny.Tabela);
                    PoleKlucz.setText(aktualny.Klucz);
                    PoleKolumna.setText(aktualny.KolumnaDoZmiany);
                    PoleWartosc.setText(aktualny.NowaWartosc);
                }
                catch (NullPointerException e1)
                {
                    PoleTabela.setText("");
                    PoleKlucz.setText("");
                    PoleKolumna.setText("");
                    PoleWartosc.setText("");
                }
            }
        });
        Zmiana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zatwierdzZmiany();
            }
        });
        UsunZmiane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usunZmiany();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Zatwierdzanie zmian");
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
        catch (Exception e1)
        {
            e1.printStackTrace();
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

    private void zatwierdzZmiany ()
    {
        try
        {
            if (ZmianaCombo.getSelectedItem().equals("Nie ma żadnych niezatwierdzonych zmian"))
            {
                Komunikat.setText("Nie ma zmian do zatwierdzenia");
            }
            else
            {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("zatwierdzanie zmian");
                String Tabela, Klucz, Kolumna, Wartosc;
                Tabela=PoleTabela.getText();
                Klucz=PoleKlucz.getText();
                Kolumna=PoleKolumna.getText();
                Wartosc=PoleWartosc.getText();
                out.println(Tabela);
                out.println(Klucz);
                out.println(Kolumna);
                out.println(Wartosc);
                out.flush();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String odpowiedz=in.readLine();
                if (odpowiedz.equals("ok"))
                {
                    Komunikat.setText("Zatwierdzono zmiane");
                    zaladujZmiany();
                }
                if (odpowiedz.equals("nie usunieto zmiany"))
                {
                    Komunikat.setText("Wykonano zmiane, ale nie udało jej się usunąć z tabeli zmian");
                    zaladujZmiany();
                }
                if (odpowiedz.equals("bledne"))
                {
                    Komunikat.setText("Nie zatwierdzono zmiany");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void usunZmiany ()
    {
        try
        {
            if (ZmianaCombo.getSelectedItem().equals("Nie ma żadnych niezatwierdzonych zmian"))
            {
                Komunikat.setText("Nie ma zmian do usunięcia");
            }
            else
            {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("usuwanie zmian");
                String Tabela, Klucz, Kolumna, Wartosc;
                Tabela=PoleTabela.getText();
                Klucz=PoleKlucz.getText();
                Kolumna=PoleKolumna.getText();
                Wartosc=PoleWartosc.getText();
                out.println(Tabela);
                out.println(Klucz);
                out.println(Kolumna);
                out.println(Wartosc);
                out.flush();

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String odpowiedz=in.readLine();
                if (odpowiedz.equals("ok"))
                {
                    Komunikat.setText("Usunięto zmiane");
                    zaladujZmiany();
                }
                if (odpowiedz.equals("nie usunieto zmiany"))
                {
                    Komunikat.setText("Nie usunięto zmiany");
                    zaladujZmiany();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
