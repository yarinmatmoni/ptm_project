package view.graphs;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class GraphsController {

   public Runnable onClick;

   @FXML
   public LineChart<Number, Number> chart1;
   @FXML
   public LineChart chart2;
   @FXML
   public LineChart chart3;
   @FXML
   public Pane draw;
   @FXML
   public AnchorPane hybridpane;
   @FXML
   public Circle circle;
   @FXML
   public Label hybridlabel;

    public GraphsController(){


      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      chart1 = new LineChart<Number, Number>(xAxis, yAxis);


      final NumberAxis xAxis2 = new NumberAxis();
      final NumberAxis yAxis2 = new NumberAxis();
      chart2 = new LineChart<Number, Number>(xAxis2, yAxis2);


       final NumberAxis xAxis3 = new NumberAxis();
       final NumberAxis yAxis3 = new NumberAxis();
       chart3 = new LineChart<Number, Number>(xAxis3, yAxis3);

    }

}
