package model;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HybridAlgorithm implements TimeSeriesAnomalyDetector {

    private List<CorrelatedFeatures> cfList; // keep all the correlations of the features pairs
    private HashMap<CorrelatedFeatures, Circle> HybridMap; // keep the circles of each pair of features (hybrid algorithm)
    private HashMap<CorrelatedFeatures,SimpleAnomalyDetector> SimpleMap;
    private HashMap<String, ZScore> ZSMap;
    private List<AnomalyReport> AnomalyList; // keep all the anomalies

    ////////////////////////////////////////////////////////////////////////////////////////////

    public void DivisionLearn(TimeSeries data) {

        // First we find all the correlations of the features
        // then we run the suitable algorithm

        SimpleAnomalyDetector simple = new SimpleAnomalyDetector(data, (float)0);
        cfList = simple.getList();

        SimpleMap = new HashMap<CorrelatedFeatures,SimpleAnomalyDetector>();
        ZSMap = new HashMap<String, ZScore>();
        HybridMap = new HashMap<CorrelatedFeatures, Circle>();

        for(int i=0; i<cfList.size(); i++) {

            if(Math.abs(cfList.get(i).corrlation) >= 0.95) { // using Linear-Regression

                TimeSeries ts1 = makeTS(data ,cfList.get(i), 1);
                SimpleAnomalyDetector smp = new SimpleAnomalyDetector(ts1, 0);
                SimpleMap.put(cfList.get(i), smp);
            }

            else if(cfList.get(i).corrlation < 0.5) { // using ZScore

                TimeSeries ts2 = makeTS(data, cfList.get(i), 0);
                ZScore zs = new ZScore();
                zs.learnNormal(ts2);
                ZSMap.put(cfList.get(i).feature1, zs);
            }

            else { // using Hybrid-Algorithm

                TimeSeries ts3 = makeTS(data ,cfList.get(i), 1);
                LearnHybrid(ts3, cfList.get(i));
            }
        }
    }

//	public void DivisionDetect(TimeSeries data) {
//
//		SimpleAnomalyDetector smp = new SimpleAnomalyDetector(data, 0);
//		List<CorrelatedFeatures> list = new ArrayList<CorrelatedFeatures>();
//		list = smp.getList();
//		for(int i=0; i<list.size(); i++) {
//
//			if(list.get(i).corrlation >= 0.95) { // using SimpleAnomalyDetector
//
//				TimeSeries ts1 = makeTS(data,list.get(i), 1);
//				List<AnomalyReport> anomalysimple = SimpleMap.get(list.get(i)).detect(ts1);
//				AnomalyList.addAll(anomalysimple);
//
//			}
//			else if(list.get(i).corrlation < 0.5) { // using ZScore
//
//				TimeSeries ts2 = makeTS(data, list.get(i), 0);
//				List<AnomalyReport> anomalyzs = ZSMap.get(list.get(i).feature1).detect(ts2);
//				AnomalyList.addAll(anomalyzs);
//
//			}
//			else { // using Hybrid algoritm
//
//				TimeSeries ts3 = makeTS(data, list.get(i), 1);
//				List<AnomalyReport> anomalyhybrid = detecthybrid(ts3, list.get(i));
//				AnomalyList.addAll(anomalyhybrid);
//			}
//		}
//	}

    public List<AnomalyReport> DivisionDetect2(TimeSeries data) {

        AnomalyList = new ArrayList<AnomalyReport>();

        for(CorrelatedFeatures i: cfList) {

            if( SimpleMap.containsKey(i)) {
                TimeSeries ts1 = makeTS(data, i, 1);
                AnomalyList.addAll(SimpleMap.get(i).detect(ts1));
            }
            else if(ZSMap.containsKey(i.feature1)) {
                TimeSeries ts2 = makeTS(data, i, 0);
                AnomalyList.addAll(ZSMap.get(i.feature1).detect(ts2));
            }
            else {
                TimeSeries ts3 = makeTS(data, i, 1);
                AnomalyList.addAll(detecthybrid(ts3, i));
            }
        }

        return AnomalyList;
    }

    public TimeSeries makeTS(TimeSeries data, CorrelatedFeatures cf, int flag) {

        // The method makes TimeSeries according to the CF we got.
        // if flag == 1 - we need to consider the two features.
        // if flag == 0 - we need to consider only one feature.

        HashMap<String,ArrayList<Float>> map = new HashMap<String, ArrayList<Float>>();
        if (flag == 1) {
            map.put(cf.feature1, data.getHashMap().get(cf.feature1));
            map.put(cf.feature2, data.getHashMap().get(cf.feature2));
        }
        else {
            map.put(cf.feature1, data.getHashMap().get(cf.feature1));
        }
        TimeSeries ts = makeTimeSeries(map);
        return ts;

    }

    public TimeSeries makeTimeSeries(HashMap<String,ArrayList<Float>> map) {// make TimeSeries so
        //we could sent it to the classes and functions we want.

        TimeSeries ts = new TimeSeries();
        ArrayList<String> str = new ArrayList<String>(); // keep the names of the features
        ts.setHashMap(map);
        for(String i: map.keySet()) {
            str.add(i);
        }
        ts.setFeatures(str);
        ts.setsizeFeatures(str.size());
        int size = map.get(str.get(0)).size();
        ts.setsizeArrayList(size);

        return ts;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////


    // Algorithm 3

    public void LearnHybrid(TimeSeries data, CorrelatedFeatures cf) { // make the circle

        //SimpleAnomalyDetector simple = new SimpleAnomalyDetector(data, 0);
        List<Point> points = makeListofPoints(data, cf);
        SmallestEnclosingCircle cr = new SmallestEnclosingCircle();
        Circle circle = cr.makeCircle(points);
        HybridMap.put(cf, circle);

    }

    public List<AnomalyReport> detecthybrid(TimeSeries data, CorrelatedFeatures cf) {

        List<Point> points = makeListofPoints(data, cf);
        List<AnomalyReport> ar = new ArrayList<AnomalyReport>();
        //AnomalyList = new ArrayList<AnomalyReport>();
        for(int i=0; i<points.size(); i++) {

            if(!(HybridMap.get(cf).contains(points.get(i)))){

                String description = cf.feature1 + "-" + cf.feature2;
                AnomalyReport anr = new AnomalyReport(description, i);
                ar.add(anr);
            }
//			if(!(isInside(points.get(i), HybridMap.get(cf)))) {
//
//				String description = cf.feature1 + "-" + cf.feature2;
//				AnomalyReport anr = new AnomalyReport(description, i);
//				//AnomalyList.add(anr);
//				ar.add(anr);
//			}
        }

        return ar;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////

    public float dist(Point a, Point b){

        return (float) Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
    }

    public boolean isInside(Point p, Circle circle)
    {
        float dist = dist(circle.c, p);
        if(dist <= circle.r) { /////////////////// change
            return true;
        }
        return false;
    }

    public List<Point> makeListofPoints(TimeSeries data, CorrelatedFeatures cf){

        float[] a = SimpleAnomalyDetector.convertToFloat(data.getHashMap().get(cf.feature1));
        float[] b= SimpleAnomalyDetector.convertToFloat(data.getHashMap().get(cf.feature2));
        Point[] points = SimpleAnomalyDetector.makePointArray(a, b);
        List<Point> list = new ArrayList<Point>();
        list = Arrays.asList(points);
        return list;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void learnNormal(TimeSeries ts) {

        DivisionLearn(ts);

    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {


        DivisionDetect2(ts);
        return AnomalyList;
    }

}
