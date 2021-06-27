package model;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Properties {

    private HashMap<String, Property> properties;


    public void setProperties(HashMap<String, Property> properties) {
        this.properties = properties;
    }

    public HashMap<String, Property> getProperties() {
        return properties;
    }

    public void readProperties(File csvFileName){

        try {
            BufferedReader in = new BufferedReader(new FileReader(csvFileName));
            String line = in.readLine();
            Scanner scan = new Scanner(line).useDelimiter(",");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

