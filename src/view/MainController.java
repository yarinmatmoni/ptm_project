package view;

import com.sun.glass.ui.View;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import view.control_panel.ControlPanel;
import view.flight_player.FlightPlayer;
import view.graphs.Graphs;
import view.joystick.Joystick;
import view.list.List;
import view.mymenubar.MyMenuBar;
import view_model.ViewModel;

import javax.naming.Name;
import java.awt.font.GraphicAttribute;
import java.awt.image.VolatileImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainController implements Observer {

    @FXML
    public MyMenuBar mymenubar;
    @FXML
    public List list;
    @FXML
    public FlightPlayer flightplayer;
    @FXML
    public Joystick joystick;
    @FXML
    public ControlPanel controlpanel;
    @FXML
    public Graphs graphs;

    ViewModel viewmodel;


    @FXML
    Label alt;

    public String name;
    public StringProperty Name1;
    public StringProperty Name2;
    public XYChart.Series series;
    public XYChart.Series series2;
    public BooleanProperty nameIsChanged;
    public FloatProperty Value;
    public XYChart.Series lineseries;
    public XYChart.Series zscoreseries;
    public FloatProperty ValueZS;

    public MainController(){

    }

    public void init(){

        alt= new Label();
        viewmodel = new ViewModel();
        this.initFlightPlayer();
        this.initMyMenuBar();
        Name1 = new SimpleStringProperty();
        Name2 = new SimpleStringProperty();
        list.controller.setList(viewmodel.observableList);
        series = new XYChart.Series();
        series2 = new XYChart.Series();
        graphs.controller.chart3.setAnimated(false);
        graphs.controller.chart2.setCreateSymbols(false);
        graphs.controller.chart1.setCreateSymbols(false);
        graphs.controller.chart3.setCreateSymbols(false);
        nameIsChanged = new SimpleBooleanProperty(false);
        Value = new SimpleFloatProperty();
        lineseries = new XYChart.Series();
        zscoreseries = new XYChart.Series();
        ValueZS = new SimpleFloatProperty();
        graphs.controller.circle.setOpacity(0);


        Value.bind(viewmodel.ValueVM);
        ValueZS.bind(viewmodel.ValueZSVM);

        f1ArrayListListener();
        Bind();
        AileronAndElevator();


        viewmodel.ValueZSVM.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                if(viewmodel.timeStep.get() == 0){
                    zscoreseries.getData().clear();
                    XYChart.Data d = new XYChart.Data(0,0);
                    graphs.controller.chart3.getData().add(d);
                    graphs.controller.chart3.getData().clear();
                    zscoreseries.getData().clear();
                }

                XYChart.Data dt = new XYChart.Data(viewmodel.timeStep.getValue(), ValueZS.getValue());
                zscoreseries.getData().add(dt);
                if(!graphs.controller.chart3.getData().contains(zscoreseries)){
                    graphs.controller.chart3.getData().add(zscoreseries);
                }
            }
        });


        viewmodel.ValueVM.addListener(new ChangeListener<Number>() { // option for function
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                int size = lineseries.getData().size();

                if(viewmodel.timeStep.get() == 0){

                    lineseries.getData().clear();
                    XYChart.Data d = new XYChart.Data(0,0);
                    graphs.controller.chart3.getData().add(d);
                    graphs.controller.chart3.getData().clear();
                    lineseries.getData().clear();
                }

                XYChart.Data dt = new XYChart.Data(viewmodel.timeStep.getValue(), Value.getValue());
                lineseries.getData().add(dt);
                if(!graphs.controller.chart3.getData().contains(lineseries)){
                    graphs.controller.chart3.getData().add(lineseries);
                }
            }
        });


        viewmodel.NameAlgo.addListener((obs, old, nw)->{
            Platform.runLater(()->{
                graphs.controller.chart3.setTitle(nw);
                graphs.controller.chart3.getData().clear();
            });
        });

        viewmodel.YVM.addListener((obs, old, nw)->{

            if(viewmodel.NameAlgo.getValue().equals("Linear Regression")){

                if(!graphs.controller.chart3.getData().isEmpty()){
                    graphs.controller.chart3.getData().clear();
                    lineseries.getData().clear();
                }
                XYChart.Series data = new XYChart.Series();
                data.getData().add(new XYChart.Data(0, viewmodel.XVM.getValue()));
                data.getData().add(new XYChart.Data(2174, viewmodel.YVM.getValue()));

                graphs.controller.chart3.setAnimated(false);
                graphs.controller.chart3.setCreateSymbols(true);

                if(graphs.controller.chart2.getTitle().equals("feature2")){
                    System.out.println("not correlated");
                    data.getData().clear();
                    graphs.controller.chart3.getData().clear();
                }
                else{
                    graphs.controller.chart3.getData().add(data);
                }
            }
        });

        list.controller.listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                // first we ger the name of the selected feature from the list:

                name = list.controller.listview.getSelectionModel().getSelectedItem();
                Name1 = new SimpleStringProperty();
                Name1.set(name);

                // we set the title of the first chart:

                graphs.controller.chart1.setTitle(Name1.getValue());

                // we notify the view-model we get the first name:

                viewmodel.Name1VM.set(Name1.getValue());

                // we find if there is a correlated feature to the first feature:
                if(viewmodel.NameAlgo.getValue().equals("Linear Regression")){

                    graphs.controller.circle.setOpacity(0);
                    graphs.controller.chart3.setOpacity(1);
                    graphs.controller.chart3.getData().clear();


                    String correlated = viewmodel.getCFeature();

                    if(correlated != null){

                        graphs.controller.chart2.setOpacity(1);
                        // we set the name of the second feature:
                        Name2.set(correlated);
                        // we notify the view-model we got the second name:
                        viewmodel.Name2VM.set(correlated);
                        nameIsChanged = new SimpleBooleanProperty(true);
                        // we set the title of the second graph:
                        graphs.controller.chart2.setTitle(Name2.getValue());

                        lineseries.getData().clear();
                        graphs.controller.chart3.getData().clear();

                        viewmodel.f2ArrayList.clear();

                        if(!graphs.controller.chart2.getData().isEmpty()){
                            graphs.controller.chart2.setAnimated(false);
                            series2.getData().clear();
                            graphs.controller.chart2.getData().clear();
                            graphs.controller.chart2.setAnimated(true);
                        }
                    }
                    else{

                        graphs.controller.chart2.setOpacity(0);
                        graphs.controller.chart2.setTitle("feature2");
                        Name2.set("-1");
                        viewmodel.Name2VM.set("-1");
                        viewmodel.f2ArrayList.clear();
                        nameIsChanged.setValue(false);

                    }


                }
                else if(viewmodel.NameAlgo.getValue().equals("ZScore")){

                    graphs.controller.chart3.setOpacity(1);
                    graphs.controller.circle.setOpacity(0);
                    graphs.controller.chart2.setOpacity(0);
                    graphs.controller.chart3.getData().clear();


                }
                else if(viewmodel.NameAlgo.getValue().equals("Hybrid")){

                    graphs.controller.chart3.getData().clear();
                    graphs.controller.circle.setOpacity(0);
                    graphs.controller.chart3.setOpacity(1);




                }



                //String correlated = viewmodel.getCFeature();

//
//                    if (viewmodel.NameAlgo.getValue().equals("Linear Regression")) {
//
//                        // Important!!!
//                        ////////////////////////////////////////////////////////////
//                        lineraRegseries.getData().clear();
//                        graphs.controller.chart3.getData().clear();
//                        ////////////////////////////////////////////////////////////
//
//                        viewmodel.f2ArrayList.clear();
//
//                        if(!graphs.controller.chart2.getData().isEmpty()){
//                            graphs.controller.chart2.setAnimated(false);
//                            series2.getData().clear();
//                            graphs.controller.chart2.getData().clear();
//                            graphs.controller.chart2.setAnimated(true);
//                        }
//
//
//
//                    }
//                    else if (viewmodel.NameAlgo.getValue().equals("ZScore")) {
//
//
//                    }
//                    else if (viewmodel.NameAlgo.getValue().equals("Hybrid")) {
//
//
//                    }
//                    else {
//
//                        System.out.println("please choose algorithm");
//                    }
//                }
//                else{
//
//                    graphs.controller.chart2.setTitle("feature2");
//                    Name2.set("-1");
//                    viewmodel.Name2VM.set("-1");
//                    viewmodel.f2ArrayList.clear();
//                    nameIsChanged.setValue(false);
//
//
//                }









//               if(correlated != null){
//                   // we have a correlated feature
//
//
//
//               }
//               else{ // we don't have correlated feature
//
//                   series2.getData().clear();
//
//                   series2.getData().add(new XYChart.Data(0, 0));
//
//                   if(!graphs.controller.chart2.getData().contains(series2)){
//                       graphs.controller.chart2.getData().add(series2);
//                   }
//
//
//               }

                graphs.controller.chart1.setAnimated(false);
                series.getData().clear();
                graphs.controller.chart1.getData().clear();
                graphs.controller.chart1.setAnimated(true);

                System.out.println(viewmodel.Name1VM.getValue());
                System.out.println(viewmodel.Name2VM.getValue());
            }
        });
    }

    public void getTitle(){
        String name = list.controller.listview.getSelectionModel().getSelectedItem();
        graphs.controller.chart1.setTitle(name);
    }

    public void initFlightPlayer(){
        flightplayer.controller.onPlay = viewmodel.playVM;
        flightplayer.controller.onPause = viewmodel.pauseVM;
        flightplayer.controller.onStop = viewmodel.stopVM;
        flightplayer.controller.onMinus = viewmodel.minusVM;
        flightplayer.controller.onPlus = viewmodel.plusVM;
        flightplayer.controller.onMoveBack = viewmodel.backVM;
        flightplayer.controller.OnMoveForward = viewmodel.forwardVM;
        flightplayer.controller.onStartLearn = viewmodel.startLearnVM;
        flightplayer.controller.onStartDetect = viewmodel.startDetectVM;
        flightplayer.controller.onSpeed1= viewmodel.speed1VM;
        flightplayer.controller.onSpeed2= viewmodel.speed2VM;
        flightplayer.controller.onSpeed3= viewmodel.speed3VM;
        flightplayer.controller.onplaySimple = viewmodel.SimpleVM;
        flightplayer.controller.onplayZScore = viewmodel.ZScoreVM;
        flightplayer.controller.onplayHybrid = viewmodel.HybridVM;

        flightplayer.controller.onDrag = viewmodel.moveSliderVM;
        flightplayer.controller.onFiles = viewmodel.openFilesVM;
    }

    public void initMyMenuBar(){

        mymenubar.controller.onSimple = viewmodel.SimpleVM;
        mymenubar.controller.onZScore = viewmodel.ZScoreVM;
        mymenubar.controller.onHybrid = viewmodel.HybridVM;
        mymenubar.controller.onOpenXML = viewmodel.openXMLVM;
        mymenubar.controller.ongettingStarted = viewmodel.gettingStartedVM;
        mymenubar.controller.onAbout = viewmodel.AboutVM;
        mymenubar.controller.onCheck = viewmodel.checkForUpdatesVM;
    }

    public void Bind(){

        flightplayer.controller.time.textProperty().bind(viewmodel.clock);

        viewmodel.timeStep.bindBidirectional(flightplayer.controller.timeSlider.valueProperty());

        controlpanel.controller.altitude.textProperty().bind(viewmodel.altitude);
        controlpanel.controller.airspeed.textProperty().bind(viewmodel.airspeed);
        controlpanel.controller.yaw.textProperty().bind(viewmodel.yaw);
        controlpanel.controller.roll.textProperty().bind(viewmodel.roll);
        controlpanel.controller.pitch.textProperty().bind(viewmodel.pitch);
        controlpanel.controller.heading.textProperty().bind(viewmodel.heading);

        joystick.controller.throttle.valueProperty().bind(viewmodel.throttle);
        joystick.controller.rudder.valueProperty().bind(viewmodel.rudder);
    }

    public void AileronAndElevator(){

        viewmodel.aileron.addListener((obs, old, nw)->{
            Platform.runLater(()->{

                joystick.controller.circle.setCenterY(nw.doubleValue() * 50);
            });
        });
        viewmodel.elevator.addListener((obs, old, nw)->{
            Platform.runLater(()->{
                joystick.controller.circle.setCenterX(nw.doubleValue() * 50);
            });
        });

    }

    public void f1ArrayListListener(){


        viewmodel.f1ArrayList.addListener(new ListChangeListener<Float>() {
            @Override
            public void onChanged(Change<? extends Float> change) {

                if(viewmodel.timeStep.get() == 0){
                    if((Name2.getValue() != null) || (Name2.getValue() != "-1")){
                        if(!series2.getData().isEmpty()){
                            series2.getData().clear();
                            if(viewmodel.f2ArrayList.size() > 0){
                                series2.getData().add(new XYChart.Data(0, viewmodel.f2ArrayList.get(viewmodel.timeStep.get())));
                                if(!graphs.controller.chart2.getData().contains(series2)) {
                                    graphs.controller.chart2.getData().add(series2);
                                }
                            }
                        }
                    }
//                    if(!graphs.controller.chart2.getData().isEmpty()){
//                        graphs.controller.chart2.getData().clear();
//                    }
                    if(!series.getData().isEmpty()){
                        series.getData().clear();
                        series.getData().add(new XYChart.Data(0, viewmodel.f1ArrayList.get(viewmodel.timeStep.get())));
                        //series.getData().add(new XYChart.Data("0", 0));
                        if(!graphs.controller.chart1.getData().contains(series)){
                            graphs.controller.chart1.getData().add(series);
                        }

                    }

                }
                else{

                    int t = viewmodel.timeStep.getValue();
                    series.getData().add(new XYChart.Data((t), viewmodel.f1ArrayList.get(viewmodel.f1ArrayList.size()-1)));
                    if((!graphs.controller.chart1.getData().contains(series)) && (graphs.controller.chart1.getData() != null)){
                        graphs.controller.chart1.getData().add(series);
                    }


                    if((Name2.getValue() != null) || (Name2.getValue() != "-1")){
                        int t2 = viewmodel.timeStep.getValue();
                        //String place2 = viewmodel.timeStep.getValue().toString();
                        int i = viewmodel.f2ArrayList.size()-1;
                        float index2 = -2;
                        if(i >= 0){
                            index2 = viewmodel.f2ArrayList.get(i);
                            series2.getData().add((new XYChart.Data((t2),index2)));
                        }
                        if((!graphs.controller.chart2.getData().contains(series2))  && (graphs.controller.chart2.getData() != null)){
                            graphs.controller.chart2.getData().add(series2);
                        }

                    }
                    else{
                        series2.getData().clear();
                        graphs.controller.chart2.getData().clear();
                        if(!graphs.controller.chart2.getData().contains(series2)){
                            graphs.controller.chart2.getData().add(series2);
                        }
                    }
                    System.out.println("value changed");
                }
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {


    }
}
