package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {

    public HashMap<Integer, Float> anomalymap;

    private List<CorrelatedFeatures> listCF;

    //private float threshold =(float) 0.9;
    private float threshold;

    public SimpleAnomalyDetector(TimeSeries ts) {

        this.setThreshold((float)0.9);
        this.learnNormal(ts);
        this.anomalymap = new HashMap<>();
    }
    public SimpleAnomalyDetector(TimeSeries ts, float tr) {

        this.setThreshold(tr);
        this.learnNormal(ts);
        this.anomalymap = new HashMap<>();
    }

    @Override
    public void learnNormal(TimeSeries ts) {

        // we built new array to store the correlative pairs
        // we need in every iteration to check pearson(valfreature1, valfreature2).
        // if it past the threshold and it is the most correlative pair - we
        // make new CorrelatedFeature member in the array.

        listCF = new ArrayList<CorrelatedFeatures>();

        for (String i: ts.getFeatures())
        {
            float checkCorrelation = 0;
            float max = 0; // will store the max value of the correlations
            float[] a = convertToFloat(ts.getValofKey(i));
            float[] b = convertToFloat(ts.getValofKey(i));
            String feature1, feature2;
            feature1 = i;
            feature2 = i;
            for (String j: ts.getFeatures())
            {
                if (i == j) // i = feature1, j = featrue2
                {
                    continue;
                }
                else // we check the correlation
                {
                    b = convertToFloat(ts.getValofKey(j));
                    checkCorrelation =Math.abs(StatLib.pearson(a, b)); // correlation
                    if (checkCorrelation > max)
                    {
                        max = checkCorrelation;
                        feature2 = j;
                    }
                }
            }

            if (max > this.threshold) // we found correlated features and now we keep them in the list
            {
                if(isReverseInlistCF(feature1, feature2) == true) // if the correlation is already exist //////////
                {
                    continue;
                }
                // first, we will make all the variables necessary to create
                // CorrelatedFeature
                b = convertToFloat(ts.getValofKey(feature2));
                Point points[] = makePointArray(a, b);
                float cor = max;
                Line lin_reg = StatLib.linear_reg(points);
                float dev = getMaxDev(points, lin_reg);
                // now we make the CorrelatedFeature object:
                CorrelatedFeatures cf = new CorrelatedFeatures(feature1, feature2, cor, lin_reg, dev);
                listCF.add(cf);
            }
        }
    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        // we have the List of the correlated features
        // we have a new time series that every line in it is a new flight

        List<AnomalyReport> ARlist = new ArrayList<AnomalyReport>();
        int i = 0;
        int size = ts.getsizeArrayList();
        while (i < size)
        {
            //ArrayList<Float> row = ts.getRow(i);
            for (int j = 0; j<listCF.size(); j++)
            {
                String f1 = listCF.get(j).feature1;
                String f2 = listCF.get(j).feature2;
                float valf1 = ts.getValByFeatureAndIndex(f1, i);
                float valf2 = ts.getValByFeatureAndIndex(f2, i);
                if (isAnomaly(j, valf1, valf2) == true)
                {
                    // we make new AnomalyReport and insert it to the ARlist
                    String description = f1+"-"+f2;
                    long timestep = i + 1;
                    AnomalyReport ar = new AnomalyReport(description, timestep);
                    ARlist.add(ar);

                    anomalymap.put((int) timestep, ts.getHashMap().get(f1).get((int)timestep));

                }
            }

            i++;
        }
        return ARlist;
    }

    public List<CorrelatedFeatures> getNormalModel(){
        return listCF;
    }


    public List<CorrelatedFeatures> getList() {
        return listCF;
    }


    public void setList(List<CorrelatedFeatures> list) {
        this.listCF = list;
    }

    public static float[] convertToFloat(ArrayList<Float> vector)
    {
        float[] arr = new float[vector.size()];
        for (int i = 0; i< vector.size(); i++)
        {
            arr[i] = vector.get(i);
        }
        return arr;
    }


    public static Point[] makePointArray(float[] a, float[] b)
    {
        Point points[] = new Point[a.length];
        for (int i = 0; i< a.length; i++)
        {
            points[i] = new Point(a[i], b[i]); // change it
        }

        return points;

    }

    public float getMaxDev(Point[] points, Line lin_reg)
    {
        float dev1 = 0;
        float dev2 = 0;
        for (int i = 0; i< points.length; i++)
        {
            dev2 = StatLib.dev(points[i], lin_reg);
            if (dev2 > dev1)
            {
                dev1 = dev2;
            }
        }
        return dev1;
    }

    public boolean isAnomaly(int j, float valf1, float valf2)
    {
        Point p = new Point(valf1, valf2);
        Line lin = listCF.get(j).lin_reg;
        float dev = StatLib.dev(p, lin);
        if (dev > listCF.get(j).threshold + 0.1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isReverseInlistCF(String f1, String f2)// check if the correlated feature is exist
    {
        for (int i = 0; i< listCF.size(); i++)
        {
            if ((listCF.get(i).feature1 == f2) && (listCF.get(i).feature2 == f1))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isInlistCF(CorrelatedFeatures a)
    {
        for (int i = 0; i< listCF.size(); i++)
        {
            if ((listCF.get(i).feature1 == a.feature1) && (listCF.get(i).feature2 == a.feature2))
            {
                return true;
            }
        }

        return false;
    }

    public float getThreshold() {
        return threshold;
    }


    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public String getCorrelatedFeature(String name){

        for (int i = 0; i< listCF.size(); i++)
        {
            if(listCF.get(i).feature1 == name){
                return listCF.get(i).feature2;
            }
            if ((listCF.get(i).feature2 == name)) {
                return listCF.get(i).feature1;
            }
        }
        return null;
    }

    public int getIndex(String feature){

        for(int i = 0; i<listCF.size(); i++){

            if(listCF.get(i).feature1 == feature || listCF.get(i).feature2 == feature){
                return i;
            }
        }
        return -1;
    }


}