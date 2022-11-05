package Testat3;

import java.io.*;

public class MyFile {
    String fileName;

    public MyFile(String fileName){
        this.fileName = fileName;
    }
    public String read(int lineNo){
        String answer = "ERROR: cannot open file for reading";
        BufferedReader inFile = null;
        try{
            inFile = new BufferedReader(new FileReader(fileName));
            String line = "ERROR: READ failed, line not found in file";
            for (int i = 0; i<lineNo && line!=null; i++){
                line = inFile.readLine();
            }
            if(line!=null){
                answer = line;
            }else{
                line = "ERROR: READ failed, line not found in file";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(inFile!= null){
            try{
                inFile.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return answer;
    }

    public String write(int lineNo, String data){
        String answer= "ERROR: cannot open file for writing";
        BufferedReader inFile = null;
        PrintWriter outFile = null;
        boolean found = false;
        try{
            inFile = new BufferedReader(new FileReader(fileName));
            outFile = new PrintWriter(new FileWriter(fileName+".temp"));
            answer = "ERROR: Write failed, line not found in file";
            String line = "";
            for (int i = 0; line!=null; i++){
                line = inFile.readLine();
                if(i==lineNo-1){
                    found = true;
                    outFile.println(data);
                }else if(line!=null){//Was macht er hier????????????
                    outFile.println(line);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(inFile != null){
            try{
                inFile.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(outFile != null){
            try{
                outFile.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(found){
            answer = data;
            try{
                File f1 = new File(fileName);
                File f2 = new File(fileName+".temp");
                File f3 = new File(fileName+".bak");
                f3.delete();
                f1.renameTo(f3);
                f2.renameTo(f1);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return answer;
    }
}
