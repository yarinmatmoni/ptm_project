package view.list;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListController {

    public Runnable onOpenList;
    public String TheTitle;

    @FXML
    public ListView<String> listview;


    public ListController(){

        listview = new ListView();
    }

    public void setList(ObservableList<String> str){

        listview.setItems(str);

    }
    public void openList(){
        if(onOpenList != null){

            onOpenList.run();
        }
    }

    public void displayTitle(){

        if(listview != null) {
            String title = listview.getSelectionModel().getSelectedItem();
            TheTitle = title;
        }
        else{
            //System.out.println("please enter CSV of learning first");
        }
    }

}
