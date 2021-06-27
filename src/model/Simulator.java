package model;

import java.io.*;
import java.net.Socket;

public class Simulator implements Runnable {

    File file;

    public void setFile(File f){
        this.file = f;
    }

    @Override
    public void run() {

        String path = file.getPath();
        try{
            Socket fg=new Socket("localhost" ,5400);
            BufferedReader in=new BufferedReader(new FileReader(path));
            //BufferedReader in=new BufferedReader(new FileReader("C:/Users/Lilach/IdeaProjects/PTM2_Project/resources/anomaly_flight.csv"));
            // C:/Users/Lilach/IdeaProjects/PTM2_Project/src/reg_flight.csv
            //BufferedReader b=new BufferedReader(new FileReader(this.file));
            PrintWriter out =new PrintWriter(fg.getOutputStream());
            String line;
            while((line=in.readLine())!=null){
                out.println(line);
                out.flush();
                Thread.sleep(100);
            }
            out.close();
            in.close();
            fg.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
