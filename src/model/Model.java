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
import view.windowsdialog.dialog;

public class Model extends Observable{

    File fileLearn, fileDetect, file, detectFile;
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

    public Float port;
    public String ip;
    public long speed;


    boolean flag = false;



    public void setProperties() {

        xmlObject xob = new xmlObject();
        ArrayList<String> names = new ArrayList<String>();
        String path = "settings.xml";

        properties = createXML.getPropertiesHashMap(path);

        port = properties.get("port").get(0);
        ip = properties.get("ip").get(0).toString() + properties.get("ip").get(1).toString();
        float spe = properties.get("speed").get(0);
        this.speed = (long) spe;

        properties.remove("port", properties.get("port"));
        properties.remove(("ip"), properties.get("ip"));
        properties.remove(("speed"), properties.get("speed"));

        for(String i: properties.keySet()){
            System.out.println(i);
        }
//        ArrayList<String> name = createXML.namesArray(path);
//        for(int i=0; i<name.size(); i++){
//            System.out.println(name.get(i));
//        }

    }

    public void setProppertiesWithPath(String path){

        xmlObject xob = new xmlObject();
        ArrayList<String> names = new ArrayList<String>();

        properties = createXML.getPropertiesHashMap(path);

        port = properties.get("port").get(0);
        ip = properties.get("ip").get(0).toString() + properties.get("ip").get(1).toString();
        float spe = properties.get("speed").get(0);
        this.speed = (long) spe;

        properties.remove("port", properties.get("port"));
        properties.remove(("ip"), properties.get("ip"));
        properties.remove(("speed"), properties.get("speed"));

        for(String i: properties.keySet()){
            System.out.println(i);
        }

    }

    public interface EventListener {
        void fireEvent (int e);
    }

    EventListener lst = new EventListener() {
        @Override
        public void fireEvent (int e) {
            //do what you want with e
        }
    };

    public Model(IntegerProperty ts ){

        this.timeStep = ts;
        this.featuresName = new ArrayList<String>();

        setProperties();

    }

    public void playModel(){


        System.out.println("play model");
        if(TSdetect != null) {


            if (timer == null) {
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("sending row " + timeStep.get());
                        clock = getDurationString(timeStep.get());
                        System.out.println(clock);
                        timeStep.set(timeStep.get() + 1);
                        notifyObservers(timeStep.get() + 1);
                    }
                }, 0, speed);


                if(!flag){
                    flag = true;
                    new Thread(() -> {
                        Simulator sim = new Simulator();
                        if (detectFile != null) {
                            sim.setFile(detectFile);
                            sim.run();
                        }

                    }).start();
                }

            }
        }
        else{
            System.out.println("please upload the TSdetect first");
        }
    }

    public void pauseModel(){
        if (timer != null) {
            System.out.println("pause model");
            timer.cancel();
            timer = null;
        }
    }
    public void stopModel(){
        if(timer != null){
            System.out.println("stop model");
            timer.cancel();
            timer = null;
            timeStep.set(0);
        }
        else {
            timeStep.set(0);
            System.out.println("stop model 2");
        }
    }
    public void backModel(){
        System.out.println("back model");
    }
    public void forwardModel(){
        System.out.println("forward model");
    }
    public void minusModel(){
        System.out.println("minus model");
        if(timeStep.get() < 10){
            timeStep.set(0);
        }
        else {
            timeStep.set(timeStep.get() - 10);
        }
    }
    public void plusModel(){
        System.out.println("plus model");
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
        System.out.println("speed is:" + sp);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public void openXMLFile(){

        FileChooser flc=new FileChooser();
        flc.setTitle("open CSV file");
        flc.setInitialDirectory(new File("./resources"));
        File file1 = flc.showOpenDialog(null);
        if(file1 == null){
            System.out.println("please upload CSV of learn");
        }
        String path = file1.getPath();
        setProppertiesWithPath(path);

    }

    public void gettingStarted(){

        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("gettingstarted.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("checkforupdates.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("check for updates");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFiles(){


        try {
            Stage stage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("dialogFiles.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Upload File");
            stage.setScene(scene);
            stage.show();

            dialog dl = new dialog();

            fileLearn = dl.controller.file1;
            fileDetect = dl.controller.file2;



        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Please enter the file of learn");


    }
    public void getCSV(){
        System.out.println("start learning");
        FileChooser fc=new FileChooser();
        fc.setTitle("open CSV file");
        fc.setInitialDirectory(new File("./resources"));
        file = fc.showOpenDialog(null);
        if(file == null){
            System.out.println("please upload CSV of learn");
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
                System.out.println("tsd ok");
            }
        }
        else{
            System.out.println("please upload the CSV of learn first");
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
            System.out.println("tsl ok");


        }
    }


    public void simpleAlgoModel(){

        if(this.checkForUseAlgo()){
            System.out.println("linear algo");
        }
    }
    public void zscoreAlgoModel(){
        if(this.checkForUseAlgo()) {
            System.out.println("zscore algo");
        }

    }
    public void hybridAlgoModel(){

        if(this.checkForUseAlgo()){
            System.out.println("hybrid algo");
        }
    }

    public boolean checkForUseAlgo(){
        if(this.TSlearn == null){
            System.out.println("Please start learning first");
            return false;
        }
        if(this.TSdetect == null){
            System.out.println("Please start detecting first");
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



}
