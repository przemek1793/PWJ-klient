import java.io.*;
import java.net.Socket;

public class Klient
{
    static Socket s;

    public static void main(String[] args)
    {
        try
        {
            s = new Socket("192.168.0.13",4255);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            MainMenu Menu= new MainMenu();
            Menu.go();
        }
        catch (IOException ex)
        {
            System.out.println("Problem z połączeniem się z serwerem");
        }
    }
}
