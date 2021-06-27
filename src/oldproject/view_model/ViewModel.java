//package oldproject.view_model;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Observable;
//import java.util.Observer;
//
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import model.Model;
//import model.TimeSeries;
//import oldproject.sample.Controller;
//
//public class ViewModel extends Observable implements Observer{
//
//    Model model;
//    public DoubleProperty speedf;
//    //public DoubleProperty minuteFlight;
//    //public DoubleProperty minuteSlider;
//    public DoubleProperty rudder;
//    public DoubleProperty throttle;
//    public DoubleProperty aileron;
//    public DoubleProperty elevators;
//
//    public final Runnable play, pause, stop, forward, backward, minus, plus;
//    Controller controller;
//
//    //public final Runnable creatL, creatD; /////
//    public IntegerProperty timeStep;
//    private HashMap<String, DoubleProperty> displayVariables;
//
//
//    //    public ViewModel(){
////        this.model = new Model();
////        speedf = new SimpleDoubleProperty();
////        throttle = new SimpleDoubleProperty();
////        rudder = new SimpleDoubleProperty();
////        aileron = new SimpleDoubleProperty();
////    }
//    public ViewModel(Model m) {
//        this.model=m;
//        //m.addObserver(this);
//        speedf=new SimpleDoubleProperty();
//        //minuteFlight=new SimpleDoubleProperty();
//        //minuteSlider=new SimpleDoubleProperty();
//        throttle=new SimpleDoubleProperty();
//        rudder=new SimpleDoubleProperty();
//        aileron=new SimpleDoubleProperty();
//
//        //throttle.addListener((o,ov,nv)->m.setThrottle((double)nv));
//
//        /////////////// made by me:
//
//        play = ()-> m.startFlight();
//        pause = ()-> m.pauseFlight();
//        stop = ()-> m.stopFlight();
//        forward = ()-> m.moveForward();
//        backward = ()-> m.moveBack();
//        minus = ()-> m.MinusFlight();
//        plus = ()-> m.PlusFlight();
//
//
//        timeStep = new SimpleIntegerProperty(0);
//        displayVariables = new HashMap<String, DoubleProperty>();
//
//        // read the properties from file in a loop:
//        displayVariables.put("alt", new SimpleDoubleProperty());
//
//
//        timeStep.addListener((obs, ols, nw)->{ // get data of display variables at time step (nw)
//            displayVariables.get("alt").set(nw.doubleValue());
//        });
//
//    }
//
//
//
////////////////////////////////////////////////////////////////////
//    public DoubleProperty getProperty(String name){
//    return displayVariables.get(name);
//    }
//    //////////////////////////////////////////////////////////////////
//    public Model getModel(){
//
//        return this.model;
//    }
//
//    public void example(){
//        System.out.println("good");
//    }
//
//    public void createTimeSeriesL(File file){
//
//        //creatL = () ->this.model.creatTSL(file);
//        TimeSeries tsL=new TimeSeries(file);
//        System.out.println("viewmodel");
//        this.model.setTimeSeriesL(tsL,file);
//    }
//
//    public void createTimeSeriesD(File file){
//        TimeSeries tsD=new TimeSeries(file);
//        this.model.setTimeSeriesD(tsD);
//    }
//
//
//    public void startFlight(){
//        model.startFlight();
//    }
//    public void pauseFlight(){
//        model.pauseFlight();
//    }
//    public void setSp(){
//        model.setSpeed(speedf.get());
//    }
//	/*public void displayMinute(){
//
//	}*/
//
//    public void stopFlight(){
//        model.stopFlight();
//    }
//
//    public void flightForward(){
//        model.moveForward();
//    }
//
//    public void flightBackward(){
//        model.moveBack();
//    }
//
//
//    public void ZScore(){
//        System.out.println("zscore");
//        this.model.ZScore();
//    }
//    public void LinearAlgo(){
//        System.out.println("linear");
//        this.model.LinearAlgo();
//    }
//    public void Hybrid(){
//        System.out.println("hybrid");
//        this.model.Hybrid();
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        if(o==model){
//            this.throttle.set(model.getThrottle());
//            System.out.println(throttle.doubleValue());
//            this.rudder.set(model.getRudder());
//            this.aileron.set(model.getAileron());
//            //this.elevators.set(model.getElevators());
//        }
//    }
//
//}
