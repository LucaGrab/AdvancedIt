package Testat2;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(7777);

        System.out.println("Server wurde gestartet");

        while(true){
            System.out.println("Vor accept");
            Socket connection = server.accept();
            System.out.println("Nach accept");
            InputStream serverIn = connection.getInputStream();
            PrintWriter serverOut = new PrintWriter(connection.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(serverIn));
            String messageFromClient = reader.readLine();
            String[] messageArray = messageFromClient.split(" ", 2);
            String awnserFromServer;
            System.out.println(messageArray[0]);
            if(messageArray[0].equals("SAVE")){
                int randomInt = new Random().nextInt(10000);
                String filePath = "C:\\Users\\I550807\\Desktop\\messages\\" + randomInt + ".txt";
                File file = new File(filePath);
                if(file.createNewFile()){
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(messageArray[1]);
                    fileWriter.close();
                    awnserFromServer = "KEY " +randomInt;
                }else{
                    awnserFromServer = "FAILED Datei existiert bereits";
                }
            }
            else if(messageArray[0].equals("GET")){

                System.out.println(messageArray[1]);
                String path = "C:\\Users\\I550807\\Desktop\\messages\\" + messageArray[1] + ".txt";
                System.out.println(path);
                File file = new File("C:\\Users\\I550807\\Desktop\\messages\\" + messageArray[1] + ".txt");
                if(file.exists()){
                    Scanner fileReader = new Scanner(file);
                    String dataInFile = fileReader.nextLine();
                    fileReader.close();
                    awnserFromServer = "OK "+ dataInFile;
                }else{
                    awnserFromServer = "FAILED Datei existiert nicht";
                }
            }
            else{
                awnserFromServer = "FAILED Falscher Command";
            }
            serverOut.println(awnserFromServer);
            serverOut.flush();
            //server.close();
        }
    }

}
