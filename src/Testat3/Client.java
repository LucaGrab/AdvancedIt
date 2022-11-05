package Testat3;

import java.net.*;
import java.io.*;
//Schickt normal Anfragen an den Server
public class Client {
    public final static int DEFAULT_PORT = 5999;
    public final static int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args) {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        String hostname = "localhost";
        int port = DEFAULT_PORT;
        String userInLine;
        try{
            DatagramSocket socket = new DatagramSocket();
            InetAddress host = InetAddress.getByName(hostname);
            System.out.println("Ready");
            System.out.println("READ file,lineNo or WRITE file,lineNo,data or exit");
            do{
                userInLine = userIn.readLine();
                if(userInLine==null || userInLine.equals("exit")){
                    break;
                }
                byte[] data = userInLine.getBytes();
                int length = data.length;
                if(length>MAX_PACKET_SIZE){
                    throw new Exception("DATA to large to send");
                }
                DatagramPacket outPacket = new DatagramPacket(data, length, host,port);
                socket.send(outPacket);
                byte buffer[] = new byte[MAX_PACKET_SIZE];
                DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(inPacket);
                String answer = new String(inPacket.getData(),0, inPacket.getLength());
                System.out.println("Received answer: " + answer);
            }while(true);
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
