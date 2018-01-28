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
            MainMenu Menu= new MainMenu(s);
            Menu.go();
        }
        catch (IOException ex)
        {
            System.out.println("Problem z połączeniem się z serwerem");
        }
    }
}
