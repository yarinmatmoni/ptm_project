//package oldproject.sample;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.Slider;
//import javafx.scene.control.TextField;
//import javafx.scene.image.ImageView;
//import javafx.stage.FileChooser;
//import model.TimeSeries;
//import view.control_panel.ControlPanel;
//import view.flight_player.FlightPlayer;
//import view.graphs.Graphs;
//import view.joystick.Joystick;
//import oldproject.view_model.ViewModel;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.net.URL;
//import java.util.Observable;
//import java.util.Observer;
//import java.util.ResourceBundle;
//import java.util.Scanner;
//
//public class Controller implements Observer, Initializable {
//
//    private ViewModel viewmodel;
//    File choose1;
//    File choose2;
//    double mx,my,jx,jy;
//    boolean mousePressed;
//    String[] properties;
//    GraphicsContext gc;
//    double aileron,elevators;
//
//    @FXML
//    ControlPanel controlPanel;
//
//    @FXML
//    Joystick joystick;
//
//    @FXML
//    FlightPlayer flightplayer;
//
//    @FXML
//    Graphs graphs;
//
//    @FXML
//    ListView<String> a;
//
//    @FXML
//    private Slider throtlle;
//
//    @FXML
//    private Slider rudder;
//
//    @FXML
//    TextField speedDisplay;
//
//    @FXML
//    ImageView stopF;
//
//    @FXML
//    Label minutes;
//
//    @FXML
//    Slider FlightTime;
//
//    @FXML
//    private LineChart<?, ?> chart;
//
//    @FXML
//    private CategoryAxis AxixC;
//
//    @FXML
//    private NumberAxis AxixN;
//
////    public Controller() {
////        mousePressed=false;
////        aileron=0;
////        elevators=0;
////        jx=50;
////        jy=20;
////
////        ////////////////////////////////////////////////
////        this.flightplayer = new FlightPlayer();
////        this.joystick = new Joystick();
////       // this.graphs = new Graphs();
////        this.controlPanel = new ControlPanel();
////        ////////////////////////////////////////////////
////
////        this.flightplayer.controller.fatherController = this;
////
////    }
//
//    public void init(ViewModel vm){ // init()
//
//        // this.viewmodel = new ViewModel();
//        this.viewmodel = vm;
//
//        this.viewmodel.example();
//        this.throtlle = new Slider();
//        this.rudder = new Slider();
//        speedDisplay = new TextField();
//        //viewmodel.speedf.bind(Double.parseDouble(speedDisplay.textProperty().getValue()));
//        //System.out.println(throtlle);
//        //System.out.println(viewmodel.throttle);
//        throtlle.valueProperty().bind(viewmodel.throttle);
//        //throtlle.valueProperty().addListener((n,ov,nv)->throtlle.setValue((double)nv));
//        rudder.valueProperty().bind(viewmodel.rudder);
//
//
//        //this.flightplayer = new FlightPlayer();
//        //flightplayer.controller.onPlay = ()-> System.out.println("play");
//
//        flightplayer.controller.onPlay = this.viewmodel.play;
//        flightplayer.controller.onPause = this.viewmodel.pause;
//        flightplayer.controller.onStop = this.viewmodel.stop;
//        flightplayer.controller.onMoveBack = this.viewmodel.backward;
//        flightplayer.controller.OnMoveForward = this.viewmodel.forward;
//        flightplayer.controller.onMinus = this.viewmodel.minus;
//        flightplayer.controller.onPlus = this.viewmodel.plus;
//
//    }
//
//
//    public void PlayButtonWasPressed(){
//
//        System.out.println("blabla");
//    }
//
//
//
//    public void print(){
//        System.out.println(throtlle.getValue());
//    }
//
//    public void openFile(){
//        FileChooser fc=new FileChooser();
//        fc.setTitle("open CSV file");
//        fc.setInitialDirectory(new File("./resources"));
//        choose1=fc.showOpenDialog(null);
//        if(choose1!=null){
//            try {
//                Scanner in= new Scanner(new BufferedReader(new FileReader(choose1)));
//                String st=in.next();
//                properties=st.split(","); ////////////////// have already method that separate names of features
//                ObservableList<String> items = FXCollections.observableArrayList ();
//                items.addAll(properties);
//                a.getItems().addAll(items);
//                a.getSelectionModel().select(items.size());
//                //displayMinute();
//                this.viewmodel.createTimeSeriesL(choose1); // here we have an null exception
//                //this.viewmodel.example();
//                TimeSeries tsL=new TimeSeries(choose1);
//                System.out.println(tsL);
//                System.out.println("view");
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public void PrintTitle(int i){
//        System.out.println(a.getItems().get(i));
//    }
//
//    public void openFile2(){
//        FileChooser fc=new FileChooser();
//        fc.setTitle("open CSV file");
//        fc.setInitialDirectory(new File("./resources"));
//        choose2=fc.showOpenDialog(null);
//        this.viewmodel.createTimeSeriesD(choose2);
//    }
//
//
//    public void showAttributes(){
//        System.out.println("this is a grapg of the attributes");
//        System.out.println(a.getSelectionModel().getSelectedItem());
//        String feature = a.getSelectionModel().getSelectedItems().toString();
//
//        // how to send the TimeSeries to the graph:
//
//
//
//    }
//
//    //we need to set the range of times of length of the flight.
//    public void displayMinute(){
//        double time=FlightTime.getValue();
//        //System.out.println(time);
//        time=0.5;
//        minutes.setText(String.valueOf(time));
//        //System.out.println(String.valueOf(time));
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//
//    }
//
//    public void startFlight(){
//        this.viewmodel.startFlight();
//    }
//
//    public void pauseFlight(){
//        this.viewmodel.pauseFlight();
//    }
//
//    public void stopFlight(){
//        viewmodel.stopFlight();
//    }
//
//    public void flightForward(){
//        viewmodel.flightForward();
//    }
//
//    public void flightBackward(){
//        viewmodel.flightBackward();
//    }
//
//    public void ZScore(){
//        this.viewmodel.ZScore();
//        System.out.println("zscore");
//    }
//    public void LinearAlgo(){
//        this.viewmodel.LinearAlgo();
//        System.out.println("linear");
//    }
//    public void Hybrid(){
//        this.viewmodel.Hybrid();
//        System.out.println("hybrid");
//    }
//
//    public void openXML(){
//        FileChooser fc=new FileChooser();
//        fc.setTitle("open XML file");
//        fc.setInitialDirectory(new File("./resources"));
//        File chooseX=fc.showOpenDialog(null);
//        if(chooseX!=null){
//            System.out.println(chooseX.getName());
//        }
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//        // makeGraph();
//        XYChart.Series series=new XYChart.Series();
//
//        series.getData().add(new XYChart.Data("1",23));
//        series.getData().add(new XYChart.Data("64",783));
//        series.getData().add(new XYChart.Data("94",773));
//
//        chart.getData().addAll(series);
//
//    }
//
//    public void makeGraph(){  // how to send the TimeSeries to the graph /////////////////
//
//        XYChart.Series series=new XYChart.Series();
//        System.out.println(a.getSelectionModel().getSelectedItem());
//        String feature = a.getSelectionModel().getSelectedItems().toString();
////        Model m = this.viewmodel.getModel();
////        TimeSeries tsl = m.getTSL();
////        TimeSeries tsd = m.getTSD();
//
////        for(int i=0; i<tsl.getsizeArrayList(); i++){
////            float y = tsl.getHashMap().get(feature).get(i);
////            series.getData().add(new XYChart.Data(i, y));
////            chart.getData().add(series);
////        }
//
//
//
//
//    }
//
//
//
//
//}
