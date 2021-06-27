package view.control_panel;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControlPanel extends AnchorPane {

    //ViewModel vm;
    public final ControlPanelController controller;

    public ControlPanel() {

        super();
        FXMLLoader fxl = new FXMLLoader();
        AnchorPane anc = null;

        try {
            anc = fxl.load(getClass().getResource("ControlPanel.fxml").openStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (anc != null) {
            controller = fxl.getController();
            controller.print();
            this.getChildren().add(anc); // add the control-panel to the view
        } else {
            controller = null;
        }


    }
}
