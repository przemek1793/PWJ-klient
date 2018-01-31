import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ZmienPreferowaneGodziny {
    private JPanel ZmienPreferowaneGodziny;
    private JComboBox PoleNazwa;
    private JButton ZmienGodziny;
    private JButton Wstecz;
    private JTextField Od1;
    private JComboBox Dzien1;
    private JComboBox Dzień2;
    private JComboBox Dzień3;
    private JLabel Komunikat;
    private JTextField Od2;
    private JTextField Od3;
    private JTextField Do1;
    private JTextField Do2;
    private JTextField Do3;
    public Socket socket;
    static private JFrame frame;

    public ZmienPreferowaneGodziny(Socket s) {
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuProwadzacy pr = new MenuProwadzacy(s);
                pr.go(frame);
            }
        });
        ZmienGodziny.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zmianaGodzin();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Prowadzenie nowego przedmiotu");
        frame.setContentPane(this.ZmienPreferowaneGodziny);
        zaladujZajecia();
        frame.pack();
    }

    private void zaladujZajecia ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("lista prowadzonych zajec");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int ileZajec = Integer.parseInt(in.readLine());
            for (int i=0; i<ileZajec; i++)
            {
                String nazwaZajec=in.readLine();
                PoleNazwa.addItem(nazwaZajec);
            }
            if (ileZajec==0)
                PoleNazwa.addItem("Nie prowadzisz żadnych zajęć");
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void zmianaGodzin ()
    {
        String koment="";
        int ileDobrychOkresów=0;
        int [] poczatek=new int [3];
        int [] koniec=new int [3];
        String nazwa = String.valueOf(PoleNazwa.getSelectedItem());
        if (nazwa.equals("Nie prowadzisz żadnych zajęć"))
        {
            Komunikat.setText("Nie masz żadnych zajęć, którym mógłbyś ustawić preferowane godziny");
            return;
        }
        else
        {
            String [] od = new String [3];
            String [] doCzasu = new String [3];
            od[0]=Od1.getText();
            od[1]=Od2.getText();
            od[2]=Od3.getText();
            doCzasu[0]=Do1.getText();
            doCzasu[1]=Do2.getText();
            doCzasu[2]=Do3.getText();

            for (int i=0; i<3; i++)
            {
                //Ktróryś jest różny od zera
                if (!od[i].equals("") || !doCzasu[i].equals(""))
                {
                    // Któryś jest dłuższy niż 5 znakór czyli niepoprawny HH:MM
                    if (od[i].length()>5 || doCzasu[i].length()>5 || od[i].length()<4 ||doCzasu[i].length()<4)
                    {
                        koment="Niepoprawny format w okresie "+(i+1);
                    }
                    else
                    {
                        // Dwukropek w złym miejscu
                        if ((od[i].charAt(1)==':' || od[i].charAt(2)==':')&& (doCzasu[i].charAt(1)==':' || doCzasu[i].charAt(2)==':'))
                        {
                            try
                            {
                                int doMinuty, doGodzina, odMinuty, odGodzina;
                                if (od[i].charAt(1)==':')
                                {
                                    odGodzina=Character.getNumericValue(od[i].charAt(0));
                                    odMinuty=Character.getNumericValue(od[i].charAt(2))*10+Character.getNumericValue(od[i].charAt(3));
                                }
                                else
                                {
                                    odGodzina=Character.getNumericValue(od[i].charAt(0))*10+Character.getNumericValue(od[i].charAt(1));
                                    odMinuty=Character.getNumericValue(od[i].charAt(3))*10+Character.getNumericValue(od[i].charAt(4));
                                }
                                if (doCzasu[i].charAt(1)==':')
                                {
                                    doGodzina=Character.getNumericValue(doCzasu[i].charAt(0));
                                    doMinuty=Character.getNumericValue(doCzasu[i].charAt(2))*10+Character.getNumericValue(doCzasu[i].charAt(3));
                                }
                                else
                                {
                                    doGodzina=Character.getNumericValue(doCzasu[i].charAt(0))*10+Character.getNumericValue(doCzasu[i].charAt(1));
                                    doMinuty=Character.getNumericValue(doCzasu[i].charAt(3))*10+Character.getNumericValue(doCzasu[i].charAt(4));
                                }
                                if (doGodzina>23 || odGodzina>23 || doMinuty>59 || odMinuty>59)
                                {
                                    koment="Za dużą liczba w okresie "+(i+1);
                                }
                                else
                                {
                                    poczatek[i]=odGodzina*60+odMinuty;
                                    koniec[i] = doGodzina*60+doMinuty;
                                    if ((koniec[i]-poczatek[i])>=90)
                                    {
                                        ileDobrychOkresów++;

                                    }
                                    else
                                    {
                                        koment="Godzina początkowa musi być przynajmniej 90 minut wcześniej niż godzina końcowa "+(i+1);
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                koment="Niepoprawny format w okresie "+(i+1);
                            }
                        }
                        else
                        {
                            koment="Niepoprawny format w okresie "+(i+1);
                        }
                    }
                }
            }
        }
        Komunikat.setText(koment);
        // nie ma komunikatów a więc nie było problemów i można wysyłać
        if (koment.equals(""))
        {
            if (ileDobrychOkresów>0)
            {
                przeslijGodzinyNaSerwer(poczatek,koniec);
            }
            else
            {
                Komunikat.setText("Nie podano żadnych preferowanych godzin");
            }
        }
    }


    private void przeslijGodzinyNaSerwer (int [] poczatek, int [] koniec)
    {
        Komunikat.setText("Wysłano nowe preferowane godziny do administratora w celu zatwierdzenia");
    }
}
