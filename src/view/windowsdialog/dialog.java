package view.windowsdialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;

public class dialog {

    public dialogcontroller controller;

    public dialog(){


        super();
        FXMLLoader fxl = new FXMLLoader();
        AnchorPane anc = null;

        try { // allows the addition of FlightPlayer to the fxml as tag
            anc = fxl.load(getClass().getResource("FlightPlayer.fxml").openStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

//        if(anc != null){
//            controller = fxl.getController();
//            this.getChildren().add(anc); // add the flightplayer to the view
//        }
//        else {
//            controller = null;
//        }
    }
}


