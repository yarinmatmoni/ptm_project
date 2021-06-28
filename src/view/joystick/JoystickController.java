package view.joystick;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class JoystickController {

    public double mx,my,jx,jy;
    public DoubleProperty aileron,elevators;


    @FXML
    public Canvas canvasjoy;

    @FXML
    public Slider throttle;

    @FXML
    public Slider rudder;

    @FXML
    public Circle bordercircle;
    @FXML
    public Circle circle;

    public JoystickController(){

        mx=0;
        my=0;
        aileron = new SimpleDoubleProperty();
        elevators = new SimpleDoubleProperty();
        throttle = new Slider();
        rudder = new Slider();
        this.canvasjoy = new Canvas();

        bordercircle = new Circle();
        circle = new Circle();

        circle.centerXProperty().bind(canvasjoy.widthProperty().divide(2));
        circle.centerYProperty().bind(canvasjoy.heightProperty().divide(2));
        circle.radiusProperty().bind(Bindings.min(canvasjoy.widthProperty(), canvasjoy.heightProperty()).divide(2));

    }


    public void normal(){

        mx=canvasjoy.getWidth()/2;
        my=canvasjoy.getHeight()/2;

        if(mx != 0){
            aileron.set((jx-mx)/mx);
        }
        else{
            aileron.set(0);
        }
        if(my != 0){
            elevators.set((jy-my)/my);
        }
        else{
            elevators.set(0);
        }
    }
}
