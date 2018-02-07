import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class PlanLekcji {
    private JPanel PlanLekcji;
    private JLabel Pon730;
    private JLabel Pon915;
    private JLabel Pon11;
    private JLabel Pon1245;
    private JLabel Pon1430;
    private JLabel Pon1615;
    private JLabel Pon18;
    private JLabel Pon1945;
    private JLabel Wt1945;
    private JLabel Wt18;
    private JLabel Wt1615;
    private JLabel Wt1430;
    private JLabel Wt1245;
    private JLabel Wt11;
    private JLabel Wt915;
    private JLabel Wt730;
    private JLabel Sr730;
    private JLabel Sr915;
    private JLabel Sr11;
    private JLabel Sr1245;
    private JLabel Sr1430;
    private JLabel Sr1615;
    private JLabel Sr18;
    private JLabel Sr1945;
    private JLabel Czw1945;
    private JLabel Czw18;
    private JLabel Czw1615;
    private JLabel Czw1430;
    private JLabel Czw1245;
    private JLabel Czw11;
    private JLabel Czw915;
    private JLabel Czw730;
    private JLabel Pia730;
    private JLabel So730;
    private JLabel Pia915;
    private JLabel Pia11;
    private JLabel So915;
    private JLabel Nie730;
    private JLabel Nie915;
    private JLabel Nie11;
    private JLabel So11;
    private JLabel So1245;
    private JLabel Nie1245;
    private JLabel Nie1430;
    private JLabel Nie1615;
    private JLabel Nie18;
    private JLabel Nie1945;
    private JLabel So1945;
    private JLabel So18;
    private JLabel So1615;
    private JLabel So1430;
    private JLabel Pia1245;
    private JLabel Pia1430;
    private JLabel Pia1615;
    private JLabel Pia18;
    private JLabel Pia1945;
    private JButton Wstecz;
    private JButton Drukuj;
    static private JFrame frame;
    public Socket socket;
    private String typ;
    private HashMap componentMap;

    public PlanLekcji(Socket s, String typ) {
        this.typ=typ;
        socket=s;
        Wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                else
                {
                    MainMenu menu= new MainMenu(socket);
                    menu.go(frame);
                }
            }
        });
        Drukuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new WatekDrukarki(frame)).start();
            }
        });
    }

    public void go(JFrame frame1) {
        frame=frame1;
        frame.setTitle("Plan lekcji");
        frame.setContentPane(this.PlanLekcji);
        nazwijKomponenty();
        createComponentMap();
        zaladujPlanLekcji();
        frame.pack();
    }

    private void zaladujPlanLekcji ()
    {
        try
        {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("plan lekcji");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int size = Integer.parseInt(in.readLine());
            for (int i=0;i<size;i++)
            {
                String nazwa=in.readLine();
                String pole=in.readLine();

                JLabel aktualny=(JLabel) getComponentByName(pole);
                aktualny.setText(nazwa);
            }
            frame.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void createComponentMap() {
        componentMap = new HashMap<String,Component>();
        Component[] components = frame.getContentPane().getComponents();
        for (int i=0; i < components.length; i++) {
            componentMap.put(components[i].getName(), components[i]);
        }
    }

    public Component getComponentByName(String name) {
        if (componentMap.containsKey(name)) {
            return (Component) componentMap.get(name);
        }
        else return null;
    }

    private void nazwijKomponenty() {
        Pon730.setName("Pon730");
        Pon915.setName("Pon915");
        Pon11.setName("Pon11");
        Pon1245.setName("Pon1245");
        Pon1430.setName("Pon1430");
        Pon1615.setName("Pon1615");
        Pon18.setName("Pon18");
        Pon1945.setName("Pon1945");
        Wt1945.setName("Wt1945");
        Wt18.setName("Wt18");
        Wt1615.setName("Wt1615");
        Wt1430.setName("Wt1430");
        Wt1245.setName("Wt1245");
        Wt11.setName("Wt11");
        Wt915.setName("Wt915");
        Wt730.setName("Wt730");
        Sr730.setName("Sr730");
        Sr915.setName("Sr915");
        Sr11.setName("Sr11");
        Sr1245.setName("Sr1245");
        Sr1430.setName("Sr1430");
        Sr1615.setName("Sr1615");
        Sr18.setName("Sr18");
        Sr1945.setName("Sr1945");
        Czw1945.setName("Czw1945");
        Czw18.setName("Czw18");
        Czw1615.setName("Czw1615");
        Czw1430.setName("Czw1430");
        Czw1245.setName("Czw1245");
        Czw11.setName("Czw11");
        Czw915.setName("Czw915");
        Czw730.setName("Czw730");
        Pia730.setName("Pia730");
        So730.setName("So730");
        Pia915.setName("Pia915");
        Pia11.setName("Pia11");
        So915.setName("So915");
        Nie730.setName("Nie730");
        Nie915.setName("Nie915");
        Nie11.setName("Nie11");
        So11.setName("So11");
        So1245.setName("So1245");
        Nie1245.setName("Nie1245");
        Nie1430.setName("Nie1430");
        Nie1615.setName("Nie1615");
        Nie18.setName("Nie18");
        Nie1945.setName("Nie1945");
        So1945.setName("So1945");
        So18.setName("So18");
        So1615.setName("So1615");
        So1430.setName("So1430");
        Pia1245.setName("Pia1245");
        Pia1430.setName("Pia1430");
        Pia1615.setName("Pia1615");
        Pia18.setName("Pia18");
        Pia1945.setName("Pia1945");
    }
}
