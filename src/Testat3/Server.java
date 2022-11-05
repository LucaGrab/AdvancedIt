package Testat3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

//Server ist der Dispatcher und übernimmt die Verteilung
public class Server {
    public static final int numberOfWorker = 3;
    public static void main(String[] args) {
        int port = Client.DEFAULT_PORT;
        String msg = null;
        DatagramPacket inPacket = null;
        for(int i = 0; i<numberOfWorker; i++){
            Thread worker = new Worker(i);
        }
        try{
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Server gestartet");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
