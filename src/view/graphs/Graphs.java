package view.graphs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Graphs extends AnchorPane {

    public final GraphsController controller;

    @FXML
    LineChart graph1;

    @FXML
    LineChart graph2;

    @FXML
    LineChart graph3;


    public Graphs(){

        super();

        FXMLLoader fxl = new FXMLLoader();
        AnchorPane anc = null;

        try {
            anc = fxl.load(getClass().getResource("Graphs.fxml").openStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(anc != null){
            controller = fxl.getController();
            this.getChildren().add(anc); // add the control-panel to the view
        }
        else {
            controller = null;
        }

    }

}
