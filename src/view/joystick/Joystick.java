package view.joystick;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import view_model.ViewModel;
//import oldproject.view_model.ViewModel;

import java.io.IOException;

public class Joystick extends AnchorPane {

    ViewModel vm;

    @FXML
    Canvas joystick;
    @FXML
    private Slider throtlle;
    @FXML
    private Slider rudder;

    public final JoystickController controller;
    public DoubleProperty aileron, elevators;

    public Joystick(){

        super();

        FXMLLoader fxl = new FXMLLoader();
        AnchorPane anc = null;

        try {
            anc = fxl.load(getClass().getResource("Joystick.fxml").openStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(anc != null){
            controller = fxl.getController();
           // controller.paint();
            this.getChildren().add(anc); // add the Joystick to the view

        }
        else {
            controller = null;
        }

//        aileron = controller.aileron;
//        elevators = controller.elevators;
//        rudder = controller.rudder.valueProperty();
//        throttle = controller.throttle.valueProperty();

        this.aileron = new SimpleDoubleProperty();
        this.elevators = new SimpleDoubleProperty();

    }

//    void init(ViewModel vm){
//        this.vm = vm;
//        vm.throttle.bind(throtlle.valueProperty());
//        vm.rudder.bind(rudder.valueProperty());
//        vm.aileron.bind(aileron);
//        vm.elevators.bind(elevators);
//
//    }
}
