package view.control_panel;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;

public class ControlPanelController {


    @FXML
    public TextField heading;
    @FXML
    public TextField roll;
    @FXML
    public TextField airspeed;
    @FXML
    public TextField pitch;
    @FXML
    public TextField yaw;
    @FXML
    public TextField altitude;

    public ControlPanelController(){

        altitude = new TextField();
        heading = new TextField();
        airspeed = new TextField();
        roll = new TextField();
        pitch = new TextField();
        yaw = new TextField();

        // need to bind all properties here

//        TextField tx = new TextField();
//        tx.setText("0");

//        this.setAltitude(tx);
//        this.setDirection(tx);
//        this.setPitch(tx);
//        this.Roll.setText(zero);
//        this.Yaw.setText(zero);
//        this.Speed.setText(zero);
    }

//    public void setAltitude(TextField altitude) {
//        Altitude = altitude;
//    }
//
//    public void setDirection(TextField direction) {
//        Direction = direction;
//    }
//
//    public void setSpeed(TextField speed) {
//        Speed = speed;
//    }
//
//    public void setRoll(TextField roll) {
//        Roll = roll;
//    }
//
//    public void setYaw(TextField yaw) {
//        Yaw = yaw;
//    }
//
//    public void setPitch(TextField pitch) {
//        Pitch = pitch;
//    }

    public void PrintVal(String name, int timeStep){ // print value to the text-field

        // need to connect to the vm and from the vm to connect to the model and use the
        // getValByFeatureAndIndex(String f, int ts) method.

    }

    public void print(){
        System.out.println("control panel");
    }
}
