package Eric;

import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main  (String[]args) throws Exception{
        System.out.println("Client startet");
        String ip = "localhost";
        int port = 7777;
        int counter = 0;
        while(counter < 5){
            Socket socket = new Socket(ip, port);
            String nachricht = counter + "Hallo Server!\n";
            counter++;
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter pw = new PrintWriter(osw);
            pw.print(nachricht);
            pw.flush();
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String antwort = br.readLine();
            System.out.println(antwort);
            Thread.sleep(5000);
        }

    }
}
