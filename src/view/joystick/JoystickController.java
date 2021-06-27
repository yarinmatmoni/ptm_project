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
    public DoubleProperty aileron,elevators; // check if we need to make it public or not
    //public boolean mousePressed;

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

//
//        GraphicsContext gc= canvasjoy.getGraphicsContext2D();
//        if(gc==null)
//            System.out.println("good");

//        double jx, jy;
//        gc.clearRect(0, 0, canvasjoy.getWidth(), canvasjoy.getHeight());
//        gc.strokeOval(-60, -10, 65, 65);
        //gc.strokeOval(-(-60), -(-10), 65, 65)


        bordercircle = new Circle();
        circle = new Circle();

        circle.centerXProperty().bind(canvasjoy.widthProperty().divide(2));
        circle.centerYProperty().bind(canvasjoy.heightProperty().divide(2));
        circle.radiusProperty().bind(Bindings.min(canvasjoy.widthProperty(), canvasjoy.heightProperty()).divide(2));


//        circle.setCenterY(elevators.getValue());
//        circle.setCenterX(aileron.getValue());

    }


    public void normal(){

//        circle.centerXProperty().bind(canvasjoy.widthProperty().divide(2));
//        circle.centerYProperty().bind(canvasjoy.heightProperty().divide(2));

        mx=canvasjoy.getWidth()/2;
        my=canvasjoy.getHeight()/2;


//        gc.strokeOval(0, 0, canvasjoy.getWidth(),canvasjoy.getHeight());
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

        //System.out.println(aileron+","+elevators);
    }

//
//    public void mouseDown(MouseEvent me){
//        if(!mousePressed){
//            mousePressed=true;
//            System.out.println("mouse is down");
//        }
//    }
//
//    public void mouseUp(MouseEvent me){
//        if(mousePressed){
//            mousePressed=false;
//            System.out.println("mouse is up");
//            jx=mx;
//            jy=my;
//            paint();
//        }
//    }
//
//    public void mouseMove(MouseEvent me){
//        if(mousePressed){
//            jx=me.getX();
//            jy=me.getY();
//            paint();
//        }
//    }
}
