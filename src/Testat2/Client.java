package Testat2;
import java.io.*;
import java.net.*;

public class Client {
    private static Socket socket;
    private static String data = new String("Das sind meine Daten");
    private static String messageFromServer;
    private static String sendedAwnserFromServer;
    private static String[] sendAwnserFromServerArray;
    private static String commandFromServer;
    private static String contentFromServer;
    public static void main(String[] args) throws IOException {

        sendMessage(data);
        System.out.println(sendedAwnserFromServer);
        if(commandFromServer.equals("KEY")){
            getMessage(contentFromServer);
            switch (commandFromServer){
                case "OK":
                    System.out.println("Anfrage war erfolreich, Inhalt:" + contentFromServer);
                    break;
                case "FAILED":
                    System.out.println("Fehler ist aufgetreten");
            }
        }
    }
    public static void sendMessage(String data) throws IOException {
        socket  = new Socket("localhost", 7777);
        PrintWriter networkOut = new PrintWriter(socket.getOutputStream());
        BufferedReader networkIn = new BufferedReader(new InputStreamReader( socket.getInputStream()));
        networkOut.println("SAVE " + data);
        networkOut.flush();
        sendedAwnserFromServer = networkIn.readLine();
        sendAwnserFromServerArray = sendedAwnserFromServer.split(" ", 2);
        commandFromServer = sendAwnserFromServerArray[0];
        contentFromServer = sendAwnserFromServerArray[1];

    }
    public static void getMessage(String keyFromServer) throws IOException {
        socket  = new Socket("localhost", 7777);
        PrintWriter networkOut = new PrintWriter(socket.getOutputStream());
        BufferedReader networkIn = new BufferedReader(new InputStreamReader( socket.getInputStream()));
        networkOut.println("GET " + keyFromServer);
        networkOut.flush();
        sendedAwnserFromServer = networkIn.readLine();
        sendAwnserFromServerArray = sendedAwnserFromServer.split(" ", 2);
        commandFromServer = sendAwnserFromServerArray[0];
        contentFromServer = sendAwnserFromServerArray[1];
    }
}
