import java.io.*;
import java.net.Socket;

public class Klient
{
    static Socket sock;

    public static void main(String[] args)
    {
        try
        {
            Socket s = new Socket("192.168.0.15",4255);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String ip = in.readLine();
            if (ip.equals("Brak serwerów"))
            {
                System.out.println("Brak dostępnych serwerów");
            }
            else
            {
                sock = new Socket(ip,4355);
                MainMenu Menu= new MainMenu(sock);
                Menu.go();
            }
        }
        catch (IOException ex)
        {
            System.out.println("Problem z połączeniem się z serwerem");
        }
    }
}
