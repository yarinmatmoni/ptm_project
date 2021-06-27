package view.mymenubar;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MyMenuBarController {

    public Runnable onSimple, onZScore, onHybrid, onOpenXML;
    public Runnable ongettingStarted, onCheck, onAbout;


    public MyMenuBarController(){

    }

    public void playSimple(){
        if(onSimple != null){
            onSimple.run();
        }
    }
    public void playZScore(){
        if(onZScore != null){
            onZScore.run();
        }
    }
    public void playHybrid(){
        if(onHybrid != null){
            onHybrid.run();
        }
    }

    public void openXML(){

        if(onOpenXML != null){
            onOpenXML.run();
        }
    }

    public void gettingStarted(){

        if(ongettingStarted != null){
            ongettingStarted.run();
        }
    }

    public void about(){

        if(onAbout != null){
            onAbout.run();
        }
    }
    public void checkUpdates(){
        if(onCheck != null){
            onCheck.run();
        }
    }


}
