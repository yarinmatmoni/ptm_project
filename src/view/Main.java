package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.createXML;
import model.xmlObject;

import java.util.ArrayList;
//import oldproject.sample.Controller;
//import view.view_model.ViewModel;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader f = new FXMLLoader();
            AnchorPane root = f.load(getClass().getResource("view.fxml").openStream());
            Scene scene = new Scene(root, 950, 600);
            primaryStage.setTitle("Flight World");
            MainController mainController = f.getController();


//            IntegerProperty i = new SimpleIntegerProperty();
//            i.set(0);
//            Model model = new Model(i);
//            ViewModel viewmodel = new ViewModel(model);
            mainController.init();

           // Model m = new Model();
            //ViewModel vm = new ViewModel(m);
            //m.addObserver(vm);
            //mainController.init(vm); // init()
            //vm.addObserver(mainController);
            //m.readXML();

            primaryStage.setScene(scene);
            primaryStage.show();
//
//            new Thread(() -> {
//                Simulator sim = new Simulator();
//                sim.run();
//            }).start();




        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void main (String[]args){
        launch(args);
    }
}
