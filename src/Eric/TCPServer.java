package Eric;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.*;
import java.util.Random;

//Server auf dem die Daten abgespeichert werden
//Server soll non persistent sein = Nach Übertrag, Verbindung schließen.
public class TCPServer {
    public static int port = 7777;
    public static String pathString = "C:\\Users\\I550807\\Desktop\\messages\\";
    public static Path path = null;

    public static void main (String[]args) throws Exception {
        System.out.println("Server startet");
        ServerSocket serverSocket = new ServerSocket(TCPServer.port); //Stecker
        while(true){
            //Server vorbereiten
            Socket client = serverSocket.accept();
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println(message);
            //Key anlegen
            int key = generateKey(message);
            //Datei anlegen
            Path path = Paths.get(pathString + key + ".txt");
            Files.writeString(path, message, StandardCharsets.UTF_8);
            //Datei auslesen
            String messageDesktop = Files.readString(path);
            System.out.println("server got: " + messageDesktop);

            //Nachricht an Server schicken
            OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
            PrintWriter pw = new PrintWriter(osw);
            pw.print(key);
            pw.flush();
            client.close();
        }

    }

    public static int generateKey(String salt){
        Random random = new Random();
        System.out.println(salt);
        return  + salt.length() + random.nextInt(10000);

    }

}
