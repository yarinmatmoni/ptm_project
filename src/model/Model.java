package model;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.stage.Stage;

public class Model extends Observable{

    File file, detectFile;
    public Timer timer = null;
    public IntegerProperty timeStep;
    public TimeSeries TSlearn, TSdetect;
    public String clock="00 : 00 : 00";
    public HashMap<String, List<AnomalyReport>> algoDetectMap; //keep the anomalyReport of each one of the algorithms

    public ZScore zscore;
    public SimpleAnomalyDetector simpleAnomaly;
    public HybridAlgorithm hybrid;

    public List<String> featuresName;
    public HashMap<String, ArrayList<Float>> properties;

    public int port;
    public String ip;
    public long speed;

    Thread t;
    Simulator simulator;


    public void setProperties() {

        xmlObject xob = new xmlObject();
        ArrayList<String> Snames = new ArrayList<String>();
        String path = "settings.xml";

        properties = createXML.getPropertiesHashMap(path);

        port =Math.round(properties.get("port").get(0));
        ip = properties.get("ip").get(0).toString() +"."+ properties.get("ip").get(1).toString();
        float spe = properties.get("speed").get(0);
        this.speed = (long) spe;

        properties.remove("port", properties.get("port"));
        properties.remove(("ip"), properties.get("ip"));
        properties.remove(("speed"), properties.get("speed"));

    }

    public void setProppertiesWithPath(String path){

        xmlObject xob = new xmlObject();
        ArrayList<String> names = new ArrayList<String>();

        properties = createXML.getPropertiesHashMap(path);
        port = Math.round(properties.get("port").get(0));
        ip = properties.get("ip").get(0).toString() + properties.get("ip").get(1).toString();
        float spe = properties.get("speed").get(0);
        this.speed = (long) spe;

        properties.remove("port", properties.get("port"));
        properties.remove(("ip"), properties.get("ip"));
        properties.remove(("speed"), properties.get("speed"));

    }

    public Model(IntegerProperty ts ){
        this.timeStep = ts;
        this.featuresName = new ArrayList<String>();
        //setProperties();
        this.port = 5400;
        this.ip="127.0.0.1";
        this.speed = 500;
    }

    public void playModel(){

        if(TSdetect != null) {
            if (timer == null) {
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        clock = getDurationString(timeStep.get());
                        timeStep.set(timeStep.get() + 1);
                        notifyObservers(timeStep.get() + 1);
                    }
                }, 0, speed);

                if(timeStep.get() == 0){

                    t = new Thread(()->{
                        simulator = new Simulator(detectFile, ip, port, speed);
                        if (detectFile != null) {
                            simulator.run();
                        }
                    });
                    t.start();
                }
            }
        }

        else{
            if(TSlearn == null){
                info("learn");
            }
            if(TSdetect == null){
                info("detect");
            }
        }
    }

    public void pauseModel(){
        if (timer != null) {
            timer.cancel();
            timer = null;
            t.interrupt();
        }
    }
    public void stopModel(){
        if(timer != null){
            clock = "00 : 00 : 00";
            timer.cancel();
            timer = null;
            timeStep.set(0);
            t.interrupt();
        }
        else {
            timeStep.set(0);
        }
    }

    public void minusModel(){
        if(timeStep.get() < 10){
            timeStep.set(0);
        }
        else {
            timeStep.set(timeStep.get() - 10);
        }
    }
    public void plusModel(){
        timeStep.set(timeStep.get() + 10);
    }

    public void changeSpeed(long sp){

        if(sp == 1){
            this.speed = 1000;
        }
        else if(sp == 2){
            this.speed = 750;
        }
        else if(sp == 3){
            this.speed = 500;
        }
        if(timer != null){

            timer.cancel();
            timer = null;
            this.playModel();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public void openXMLFile(){

        FileChooser flc=new FileChooser();
        flc.setTitle("open CSV file");
        flc.setInitialDirectory(new File("./resources"));
        File file1 = flc.showOpenDialog(null);
        if(file1 == null){
            info("xmlcheck");
        }
        String path = file1.getPath();
        setProppertiesWithPath(path);
    }

    public void gettingStarted(){

        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../xmlsaver/gettingstarted.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Getting Started");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void About(){

        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../xmlsaver/About.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("About");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkForUpdates(){

        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../xmlsaver/checkforupdates.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("check for updates");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCSV(){
        FileChooser fc=new FileChooser();
        fc.setTitle("open CSV file");
        fc.setInitialDirectory(new File("./resources"));
        file = fc.showOpenDialog(null);
        if(file == null){
           // System.out.println("please upload CSV file");
        }
    }

    public void startDetectModel() {

        if (TSlearn != null) {
            getCSV();
            if (file != null) {
                detectFile = file;
                TimeSeries tsd = new TimeSeries(file);

                TSdetect = tsd;
                algoDetectMap = new HashMap<String, List<AnomalyReport>>();
                List<AnomalyReport> listAnomaly = new ArrayList<AnomalyReport>();
                listAnomaly = this.simpleAnomaly.detect(TSdetect);
                LinearRegHelper helper = new LinearRegHelper();

                algoDetectMap.put("Linear Regression", listAnomaly);
                listAnomaly.clear();
                listAnomaly = this.zscore.detect(TSdetect);
                algoDetectMap.put("ZScore", listAnomaly);
                listAnomaly.clear();
                listAnomaly = this.hybrid.detect(TSdetect);
                algoDetectMap.put("Hybrid", listAnomaly);
            }
        }
        else{
            info("detect");
        }
    }

    public void startLearnModel(){

        getCSV();
        if(file != null){
            TimeSeries tsl = new TimeSeries(file);
            TSlearn = tsl;
            this.simpleAnomaly = new SimpleAnomalyDetector(TSlearn);
            this.zscore = new ZScore();
            this.zscore.learnNormal(TSlearn);
            this.hybrid = new HybridAlgorithm();
            this.hybrid.learnNormal(TSlearn);
            this.featuresName = TSlearn.getFeatures();
        }
        else{
            info("learn");
        }
    }

    public void simpleAlgoModel(){

        if(this.checkForUseAlgo()){
        }
    }
    public void zscoreAlgoModel(){
        if(this.checkForUseAlgo()) {
        }

    }
    public void hybridAlgoModel(){

        if(this.checkForUseAlgo()){
        }
    }

    public boolean checkForUseAlgo(){
        if(this.TSlearn == null){
            info("learn");
            return false;
        }
        if(this.TSdetect == null){
            info("detect");
            return false;
        }
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    // clock:

    public String getDurationString(int seconds) { //stopwatch func

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    public String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean containsTimeStepInAnomalyList(long t){

        for(int i=0; i<algoDetectMap.get("Linear Regression").size(); i++){

            if(algoDetectMap.get("Linear Regression").get(i).timeStep == t){
                return true;
            }
        }
        return false;
    }


    public void info(String str){
        Stage stage = new Stage();
        try {
            String path = "../xmlsaver/" + str + ".fxml";
            Parent root1 = FXMLLoader.load(getClass().getResource(path));
            Scene scene1 = new Scene(root1);
            stage.setScene(scene1);
            stage.setTitle("Information");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
