package Boris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageClient {
    public static void main(String[] args) {
        try {
            //Tastatur Eingabe
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            Socket socket;
            BufferedReader networkIn;
            PrintWriter networkOut;


            while(true){
                String userLine = userIn.readLine();

                socket = new Socket("localhost", MessageServer.DEFAULT_PORT);
                networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                networkOut = new PrintWriter(socket.getOutputStream());
                //Sendet Nachricht ab
                networkOut.println(userLine);
                networkOut.flush();
                //Empänft Antwort vom Server
                System.out.println(networkIn.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
