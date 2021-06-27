package model;

import com.sun.javafx.scene.shape.HLineToHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LinearRegHelper {

    public String f1;
    public String f2;
    public long timeStep;
    public float value;

    public  LinearRegHelper(){

    }

    public LinearRegHelper readAnomolay(AnomalyReport a, TimeSeries ts){

        LinearRegHelper lin = new LinearRegHelper();
        Scanner in = new Scanner(a.description).useDelimiter("-");
        LinearRegHelper b = new LinearRegHelper();
        b.f1=in.next();
        b.f2 = in.next();
        b.timeStep = a.timeStep;
        b.value = ts.getHashMap().get(b.f1).get((int) timeStep);

        return lin;
    }

    public List<LinearRegHelper> makeList(List<AnomalyReport> listar, TimeSeries ts) {

        List<LinearRegHelper> list = new ArrayList<LinearRegHelper>();
        for(int i=0; i<listar.size(); i++){
            LinearRegHelper lin = readAnomolay(listar.get(i), ts) ;
        }
        return list;
    }

    public int checkIfExist(List<LinearRegHelper> list, String name){

        for(int i=0; i<list.size(); i++){

            if(list.get(i).f1.equals(name) || list.get(i).f2.equals(name)){
                return i;
            }

        }

        return -1;
    }

}
