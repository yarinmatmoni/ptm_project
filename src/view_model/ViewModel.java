package view_model;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import model.*;

import javax.crypto.spec.PSource;
import javax.naming.Name;
import java.util.*;

public class ViewModel extends Observable implements Observer {

    Model model;
    public IntegerProperty timeStep;
    private HashMap<String, FloatProperty> displayVariables;
    public final Runnable playVM, pauseVM, stopVM, minusVM, plusVM, startLearnVM, startDetectVM,speed2VM,speed1VM,speed3VM ;
    public final Runnable SimpleVM, ZScoreVM, HybridVM;
    public final Runnable openXMLVM, gettingStartedVM, AboutVM, checkForUpdatesVM;
    public StringProperty clock;
    public StringProperty  altitude,heading,roll,airspeed,pitch,yaw;
    public DoubleProperty throttle, rudder, elevator, aileron;
    public List<String> featuresList;
    public ObservableList<String> observableList;
    public int timeForSlider;
    public Slider slider;
    public final Runnable moveSliderVM;
    public StringProperty Name1VM;
    public StringProperty Name2VM;
    public ObservableValue<String> name2;
    public DoubleProperty f1Vaule;
    public ObservableList<Float> f1ArrayList;
    public ObservableList<Float> f2ArrayList;
    public String checkName2;
    public StringProperty NameAlgo;
    public FloatProperty XVM, YVM;
    public ObservableList<AnomalyReport> anomalyReports;
    public HashMap<Integer, String> algoIdMap;
    public IntegerProperty chooseAlgo;
    public BooleanProperty nameChangedVM;
    public IntegerProperty endTimeVM;

    public ObservableList<Float> linearReg;

    public FloatProperty ValueVM;
    public FloatProperty ValueZSVM;

    public HashMap<Integer ,Float> listforlin; // timeStep and the value


    public ViewModel(){

        this.timeStep = new SimpleIntegerProperty(0);
        this.model = new Model(timeStep);
        this.model.addObserver(this);
        this.model.addObserver(this);
        initializeSettings();
        Name1VM = new SimpleStringProperty();
        Name1VM.set("-1");
        Name2VM = new SimpleStringProperty();
        Name2VM.set("-1");
        name2 = new SimpleStringProperty();
        f1Vaule = new SimpleDoubleProperty();
        f1ArrayList = FXCollections.observableArrayList();
        f2ArrayList = FXCollections.observableArrayList();
        NameAlgo = new SimpleStringProperty();
        XVM = new SimpleFloatProperty();
        YVM = new SimpleFloatProperty();
        algoIdMap = new HashMap<>();
        chooseAlgo = new SimpleIntegerProperty();
        nameChangedVM = new SimpleBooleanProperty();
        linearReg = new SimpleListProperty<>();
        ValueVM = new SimpleFloatProperty();
        ValueZSVM = new SimpleFloatProperty();

        if(model.properties != null){
            for(String i: model.properties.keySet()){
                displayVariables.put(i, new SimpleFloatProperty());
            }
        }


        algoIdMap.put(1, "Linear Regression");
        algoIdMap.put(2, "ZScore");
        algoIdMap.put(3, "Hybrid");

        anomalyReports = FXCollections.observableArrayList();
        observableList = FXCollections.observableArrayList();
        observableList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {}
        });


        timeStep.addListener((obs, old, nw)-> {
            if(timeStep.get() < model.TSdetect.getsizeArrayList()) { //// properties.get(ArrayList.size)

                Platform.runLater(() -> {
                    clock.set(model.clock);
                    slider.setValue(timeStep.get());
                    throttle.set(model.TSdetect.getHashMap().get("throttle").get(timeStep.get()));
                    rudder.set(model.TSdetect.getHashMap().get("rudder").get(timeStep.get()));
                    aileron.set(model.TSdetect.getHashMap().get("aileron").get(timeStep.get()));
                    elevator.set(model.TSdetect.getHashMap().get("elevator").get(timeStep.get()));

                    if(NameAlgo.getValue().equals("Linear Regression")){

                        if(model.simpleAnomaly.anomalymap.containsKey(timeStep.get())){
                            ValueVM.set(model.simpleAnomaly.anomalymap.get(timeStep.get()));
                        }
                    }

                    if(NameAlgo.getValue().equals("ZScore")){
                        ValueZSVM.set(model.zscore.zmap1.get(Name1VM.getValue()).get(timeStep.get()));
                    }

                    if (Name1VM.getValue() != null || Name1VM.getValue() != "-1") {
                        // f1Vaule.setValue(model.TSdetect.getHashMap().get(Name1VM.getValue()).get(timeStep.get()));
                        String name = Name1VM.getValue();
                        if (model.TSdetect.getHashMap().get(name) != null) {
                            f1ArrayList. add(model.TSdetect.getHashMap().get(name).get(timeStep.get()));
                        }

                    }

                    if ((Name2VM.getValue() != "-1") && (timeStep.getValue() != null)) {
                        f2ArrayList.add(model.TSdetect.getHashMap().get(Name2VM.getValue()).get(timeStep.get()));
                    }
                });
            }
            else{
                model.stopModel();
            }
        });


        slider.valueProperty().addListener((obs, old, nw)->{
            Platform.runLater(()->{
                timeStep.set((int) slider.getValue());
            });
        });


//////////////////////////////////////////////////////////////////////////////////////////////////
        for(String i: displayVariables.keySet()){
            timeStep.addListener((obs, old, nw)-> {
                Platform.runLater(()->displayVariables.get(i).set(nw.floatValue()));
            });
        }
//////////////////////////////////////////////////////////////////////////////////////////////////

        openXMLVM = ()-> model.openXMLFile();
        gettingStartedVM = ()->model.gettingStarted();
        AboutVM = ()->model.About();
        checkForUpdatesVM = ()-> model.checkForUpdates();

        clock = new SimpleStringProperty();

        playVM = ()-> model.playModel();
        pauseVM = ()->model.pauseModel();
        stopVM = ()->model.stopModel();
        minusVM = ()->model.minusModel();
        plusVM = ()->model.plusModel();

        startLearnVM = (()->{
            model.startLearnModel();
            endTimeVM.set(model.TSlearn.getsizeArrayList());
            this.featuresList = new ArrayList<>();
            if(model.TSlearn != null){
                this.featuresList = model.TSlearn.getFeatures();
                observableList.setAll(featuresList);
                timeForSlider = model.TSlearn.getsizeArrayList();
            }
        });
        startDetectVM = (()->{
            model.startDetectModel();
            timeStep.addListener((obs, old, nw)->{
                Platform.runLater(()->{
                    altitude.set(model.TSdetect.getHashMap().get("altimeter_indicated-altitude-ft").get(timeStep.get()).toString());
                    airspeed.set(model.TSdetect.getHashMap().get("airspeed-indicator_indicated-speed-kt").get(timeStep.get()).toString());
                    roll.set(model.TSdetect.getHashMap().get("attitude-indicator_indicated-roll-deg").get(timeStep.get()).toString());
                    yaw.set(model.TSdetect.getHashMap().get("side-slip-deg").get(timeStep.get()).toString());
                    pitch.set(model.TSdetect.getHashMap().get("attitude-indicator_internal-pitch-deg").get(timeStep.get()).toString());
                    heading.set(model.TSdetect.getHashMap().get("indicated-heading-deg").get(timeStep.get()).toString());

                });
            });
        });

        Name1VM.addListener((obs, old, nw)->{
            Platform.runLater(()->{

                if(NameAlgo.getValue().equals("Linear Regression")) {
                    int index = model.simpleAnomaly.getIndex(Name1VM.getValue()) + 1;
                    XVM.set(model.simpleAnomaly.getList().get(index).lin_reg.f(0));
                    YVM.set(model.simpleAnomaly.getList().get(index).lin_reg.f(2174));

                    listforlin = new HashMap<Integer, Float>();
                    List<AnomalyReport> listanomaly = model.algoDetectMap.get("Linear Regression");
                    for(int i=0; i<listanomaly.size(); i++){

                        long timeStepoflist = listanomaly.get(i).timeStep;
                        int timestep2 = ((int) timeStepoflist);
                        model.TSdetect.getHashMap().get(Name1VM.getValue()).get(i);
                        listforlin.put(timestep2, model.TSdetect.getHashMap().get(Name1VM.getValue()).get(timestep2));
                    }
                }
                if(NameAlgo.getValue().equals("ZScore")){
                    ValueZSVM.set(model.zscore.zmap1.get(Name1VM.getValue()).get(timeStep.get()));
                }
            });
        });

        speed1VM = ()->model.changeSpeed(1);
        speed2VM = ()->model.changeSpeed(2);
        speed3VM = ()->model.changeSpeed(3);

        SimpleVM = ()->{
            model.simpleAlgoModel();
            NameAlgo.set("Linear Regression");
        };
        ZScoreVM = ()->{
            model.zscoreAlgoModel();
            NameAlgo.set("ZScore");
        };
        HybridVM = ()->{
            model.hybridAlgoModel();
            NameAlgo.set("Hybrid");
        };

        moveSliderVM = (()->{ slider.getValue(); });
    }

    public String getCFeature(){
        return model.simpleAnomaly.getCorrelatedFeature(Name1VM.getValue());
    }

    public void initializeSettings(){

        displayVariables = new HashMap<String, FloatProperty>();
        altitude = new SimpleStringProperty();
        airspeed = new SimpleStringProperty();
        roll = new SimpleStringProperty();
        yaw = new SimpleStringProperty();
        pitch = new SimpleStringProperty();
        heading = new SimpleStringProperty();
        slider = new Slider();
        throttle = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        endTimeVM = new SimpleIntegerProperty();
    }
    @Override
    public void update(Observable o, Object arg) {

    }

}
