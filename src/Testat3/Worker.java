package Testat3;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

//Mehrere werden am Anfang vom Server erzeugt
//Müssen sich melden wenn sie mit der Aufgabe fertig sind
public class Worker extends Thread{
    DatagramPacket dp;
    DatagramSocket ds;
    int id;

    public Worker(int id){
        this.id = id;
    }
    public Worker(int id,DatagramPacket dp, DatagramSocket ds){
        this.id = id;
        this.dp = dp;
        this.ds = ds;
    }
    public void run(){
        String fileSeparator = File.separator;
        String userDirectory = System.getProperty("user.home") + fileSeparator + "Desktop" + fileSeparator + "MessagesTestat3" + fileSeparator;
        String dpData = "";
        String answer = "ERROR: unknown";
        String filename = "";
        String newData = "";
        int lineNo = -1;
        MyFile file = null;
        String param[] = null;
        String param2[] = null;
        DatagramPacket dp2 = null;
        try{
            dpData = new String(dp.getData(),0,dp.getLength()).trim();
            if(dpData.startsWith("READ ")){
                try{
                    param = dpData.split(" ", 2);
                    param2 = param[1].split(" ", 2);
                    filename = param2[0].trim();
                    lineNo = Integer.parseInt(param2[1].trim());
                    file = new MyFile(userDirectory + filename);
                    answer = file.read(lineNo);
                }catch(Exception e){
                    answer = "ERROR: Wrong READ command";
                    throw new Exception(e);
                }
            }else if(dpData.startsWith("WRITE ")){
                try {
                    param = dpData.split(" ", 2);
                    param2 = param[1].split(" ", 2);
                    filename = param2[0].trim();
                    lineNo = Integer.parseInt(param2[1].trim());
                    file = new MyFile(userDirectory + filename);
                    answer = file.write(lineNo, newData);
                }catch(Exception e){
                    answer = "ERROR: Wrong WRITE command";
                    throw new Exception(e);
                }
            }else{
                answer = "ERROR: Command unknown";
                throw new Exception("Command unknown");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            dp2 = new DatagramPacket(answer.getBytes(), answer.length(), dp.getAddress(), dp.getPort());
            ds.send(dp2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
