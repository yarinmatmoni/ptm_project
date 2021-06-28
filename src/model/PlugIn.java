package model;

import javafx.css.converter.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.Buffer;

public class PlugIn {

    public void createPlugIn(){

        String input, className;

        try {
            System.out.println("Please enter a class directory");
            BufferedReader in = new BufferedReader((new InputStreamReader(System.in)));
            input = in.readLine();
            System.out.println("Please enter the class name");
            className = in.readLine();
            in.close();

            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {
                    new URL("file://"+input)
            });

            Class<?> c= null;
            try {
                c = urlClassLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // create a StringConverter instance
            StringConverter sc=(StringConverter) c.newInstance();
            //System.out.println(sc.convert("Hello World!"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }


//    System.out.println("enter a class directory");
//    BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
//    input=in.readLine(); // get user input
//System.out.println("enter the class name");
//    className=in.readLine();
//in.close();


}
