package view.mymenubar;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class MyMenuBar extends AnchorPane {


    public  final MyMenuBarController controller;

    public MyMenuBar() {
        super();

        FXMLLoader fxl = new FXMLLoader();
        AnchorPane anc = null;

        try {
            anc = fxl.load(getClass().getResource("MyMenuBar.fxml").openStream());

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        if (anc != null) {
            controller = fxl.getController();
            this.getChildren().add(anc); // add the control-panel to the view
        } else {
            controller = null;
        }
    }
}
