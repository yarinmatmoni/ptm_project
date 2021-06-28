package view;

import javafx.application.Application;
import javafx.css.converter.StringConverter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.TimeSeriesAnomalyDetector;
import model.createXML;
import model.xmlObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
//import oldproject.sample.Controller;
//import view.view_model.ViewModel;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {


//        String input, className;
//        URLClassLoader urlclasslaoder = null;
//        try {
//            System.out.println("Please enter the class name");
//            BufferedReader in = new BufferedReader((new InputStreamReader(System.in)));
//            className = in.readLine();
//            urlclasslaoder = URLClassLoader.newInstance(new URL[]{
//                new URL("file://C://Users//Lilach//IdeaProjects//Algotithms//out//artifacts//Algotithms_jar")
//            });
//            Class<?> c = null;
//            try {
//                c = urlclasslaoder.loadClass("algotithms."+className);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            if(c != null){
//                TimeSeriesAnomalyDetector sc=(TimeSeriesAnomalyDetector) c.newInstance();
//            }

//        try{
//            System.out.println("Please enter a class directory");
//            BufferedReader in = new BufferedReader((new InputStreamReader(System.in)));
//            input = in.readLine();
//            System.out.println("Please enter the class name");
//            className = in.readLine();
//            in.close();
//
//            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {
//                    new URL("file://"+input)
//            });
//
//            Class<?> c= null;
//            try {
//                c = urlClassLoader.loadClass(className);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            // create a TimeSeriesAnomalyDetector instance
//            TimeSeriesAnomalyDetector sc=(TimeSeriesAnomalyDetector) c.newInstance();
//
//            urlClassLoader.close();
//            System.out.println("success");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }

        try {
            FXMLLoader f = new FXMLLoader();
            AnchorPane root = f.load(getClass().getResource("view.fxml").openStream());
            Scene scene = new Scene(root, 950, 600);
            primaryStage.setTitle("Flight World");
            MainController mainController = f.getController();

            mainController.init();

            primaryStage.setScene(scene);
            primaryStage.show();

            Stage stage = new Stage();
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("../xmlsaver/gettingstarted.fxml"));
                Scene scene1 = new Scene(root1);
                stage.setScene(scene1);
                stage.setTitle("Getting Started");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main (String[]args){
        launch(args);
    }
}
