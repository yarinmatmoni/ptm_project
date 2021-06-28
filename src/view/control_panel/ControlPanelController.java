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


    }

}
