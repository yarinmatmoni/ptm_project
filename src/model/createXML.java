package model;

import javafx.util.Pair;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class createXML {

   public HashMap<String, Pair<Float,Float>>names = new HashMap<>();
   public HashMap<String, String> settings = new HashMap<>();

        public static void encoder(String path){

            xmlObject xmlo = new xmlObject();
            HashMap<String, ArrayList<Float>> list = new HashMap<>();
            for(int i=0;i<42;i++){
                try {
                    System.out.println("enter name:" + i);
                    Scanner in = new Scanner(System.in);
                    String name = in.next();
                    System.out.println("enter number1:");
                    Float num1 =  in.nextFloat();
                    System.out.println("enter number2:");
                    Float num2 =  in.nextFloat();
                    ArrayList<Float> arr2  = new ArrayList<>();
                    arr2.add(num1);
                    arr2.add(num2);
                    xmlo.getMap().put(name,arr2);
                }catch (Exception e){

                }
            }
            Scanner in = new Scanner(System.in);
            System.out.println("enter port:");
            Float port = in.nextFloat();
            System.out.println("enter ip:");
            String ip = in.next();
            System.out.println("enter speed:");
            Float speed = in.nextFloat();



//            Scanner in = new Scanner(System.in);
//            System.out.println("enter data Sample and name of normal flight:");
//            Float r = in.nextFloat();
//            String s = in.next();
//            xmlo.setNormalFlight(s);
//            xmlo.setDataSample(r);

            try { XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
                encoder.writeObject(xmlo);
                encoder.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        public static xmlObject loadXML(String path) { // read the file
            xmlObject loadxmlFile = new xmlObject();
            try {
                XMLDecoder decoder = new XMLDecoder(new FileInputStream(path));
                loadxmlFile = (xmlObject) decoder.readObject();
                decoder.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return loadxmlFile;
        }

        public static ArrayList<String> namesArray(String path){
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> settings = new ArrayList<>();
            xmlObject xml = new xmlObject();
            xml = loadXML(path);

            names.addAll(xml.getMap().keySet());
            ArrayList<String>namesrel = new ArrayList<>();
            for(int i=0; i<names.size(); i++){
//                if(names.get(i).equals("port") || names.get(i).equals("speed") || names.get(i).equals("ip")){
//
//                    continue;
//                }
                namesrel.add(names.get(i));
            }
            return namesrel;
        }

        public static HashMap<String, ArrayList<Float>> getPropertiesHashMap(String path){

            xmlObject xml = new xmlObject();
            xml = loadXML(path);
            HashMap<String, ArrayList<Float>> map = new HashMap<String, ArrayList<Float>>();

            for(String i: xml.getMap().keySet()){

//                if(names.get(i).equals("port") || names.get(i).equals("speed") || names.get(i).equals("ip")){
//
//                    continue;
//                }
                ArrayList<Float> arr = new ArrayList<>();
                arr.add(xml.getMap().get(i).get(0));
                arr.add(xml.getMap().get(i).get(1));
                map.put(i, arr);

            }

            return map;
        }


        public HashMap<String, String> settings(String path){
            xmlObject xml = new xmlObject();
            xml = loadXML(path);
            HashMap<String, String> map = new HashMap<String, String>();

            for(String i: xml.getMap().keySet()){
                if(names.get(i).equals("port") || names.get(i).equals("speed") || names.get(i).equals("ip")){

                    map.put(i, xml.getMap().get(i).get(0).toString());
                }
            }
            return map;
        }

        public static ArrayList<Float> ranges(String path, String name){
            xmlObject xml = new xmlObject();
            xml = loadXML(path);
            Set<String> set = xml.getMap().keySet();
            if(!set.contains(name)){
                System.out.println("The name is not found");
                return null;
            }
            return (xml.getMap().get(name));
        }
}




