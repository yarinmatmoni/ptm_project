package view.list;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class List extends AnchorPane {

   public final ListController controller;

    public List() {
        super();

        FXMLLoader fxl = new FXMLLoader();
        AnchorPane anc = null;

        try {
            anc = fxl.load(getClass().getResource("List.fxml").openStream());

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
