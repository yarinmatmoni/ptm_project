package view.flight_player;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;

public class FlightPlayerController {

    // using strategy patter:
    public Runnable onPlay, onPause, onStop, OnMoveForward, onMoveBack, onMinus, onPlus, onStartLearn,
            onStartDetect, onSpeed1, onSpeed2, onSpeed3, onTime; // here we can get the function from outside
    public Runnable onplaySimple, onplayZScore, onplayHybrid;
    public Runnable onDrag, onFiles;


    @FXML
    public Label time;
    @FXML
    public Slider timeSlider;
    @FXML
    public MenuItem linearReg;
    @FXML
    public MenuItem ZScore;
    @FXML
    public MenuItem Hybrid;
    @FXML
    public MenuButton chooseAlgo;

    public FlightPlayerController(){

        timeSlider = new Slider();
    }

    public void play(){
        if(onPlay != null) {
            onPlay.run();
        }
        else{
            System.out.println("Button onPlay isn't working");
        }
    }

    public void pause(){
        if(onPause != null) {
            onPause.run();
        }
    }

    public void stop(){
        if(onStop != null) {
            onStop.run();
        }
    }

    public void moveForward(){

        if(OnMoveForward != null){
            OnMoveForward.run();
        }

    }

    public void moveBack(){

        if(onMoveBack != null){
            onMoveBack.run();
        }
    }

    public void Minus(){

        if(onMinus != null){
            onMinus.run();
        }
    }

    public void Plus(){

        if(onPlus != null){
            onPlus.run();
        }
    }

    public void startLearn(){
        if(onStartLearn != null){
            onStartLearn.run();
        }
    }

    public void startDetect(){

        if(onStartDetect != null){
            onStartDetect.run();
        }
    }
    public void speed1(){
        if(onSpeed1 != null){
            onSpeed1.run();
        }
    }

    public void speed2(){
        if(onSpeed2 != null){
            onSpeed2.run();
        }
    }
    public void speed3(){
        if(onSpeed3 != null){
            onSpeed3.run();
        }
    }

    public  void Time(){
        if(onTime != null){
            onTime.run();
          //  time.setText();
        }
    }

    public void playSimple(){
        if(onplaySimple != null){
            onplaySimple.run();
        }
    }

    public void playZScore(){

        if(onplayZScore != null){
            onplayZScore.run();
        }
    }

    public void playHybrid(){
        if( onplayHybrid != null){
            onplayHybrid.run();
        }
    }

    public void dragSlider(){
        if(onDrag != null){
            onDrag.run();
        }
    }

    public void openFiles(){
        if(onFiles != null){
            onFiles.run();
        }
    }


}
