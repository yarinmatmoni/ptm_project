package view.graphs;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
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

    public GraphsController(){


   //   chart1.setTitle("feature1");
    //  chart2.setTitle("feature2");
////       Axis<Float> axis;
////     chart1 = new LineChart(axis);
//     XYChart.Series series=new XYChart.Series();
//       series.getData().add(new XYChart.Data("1",23));
//     chart1.getData().add(series);
//
     // Axis<Integer> xAxis = new NumberAxis();
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      chart1 = new LineChart<Number, Number>(xAxis, yAxis);
     // chart1.setTitle("");

      final NumberAxis xAxis2 = new NumberAxis();
      final NumberAxis yAxis2 = new NumberAxis();
      chart2 = new LineChart<Number, Number>(xAxis2, yAxis2);
     // chart2.setTitle("");

       final NumberAxis xAxis3 = new NumberAxis();
       final NumberAxis yAxis3 = new NumberAxis();
       chart3 = new LineChart<Number, Number>(xAxis3, yAxis3);




      }






    public void setTitle1(String name1){
      chart1.setTitle(name1);
   }

    public void setTitle2(String name2){
      chart2.setTitle(name2);
   }

      public void makeGraph(){

      }

     public void cliclItem(){
        if(onClick != null){
          onClick.run();
       }
      }
}
