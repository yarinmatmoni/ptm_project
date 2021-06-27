package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ZScore implements TimeSeriesAnomalyDetector {

	public ArrayList<Float> zmap = new ArrayList<>();
	HashMap<String, Float> ZScoreMap;// save the name of the feature and the "tx".
	List<AnomalyReport> AnomalyList; // save the anomalies


	public static float FindMaxZS(float[] x) { // count the z-score from time 0
		//to the time that x[i] appeared (not include the x[i] itself) for one column.

		float max = 0; // keep the maximum "tx"

		for(int i=1; i<x.length; i++) {

			float[] partofarray = makePartOfArray(x, i);
			float zs = CountZS(partofarray, x);

			if(zs == -1) {
				continue;
			}
			if (zs > max) {
				max = zs;
			}
		}
		return max;
	}

	public ArrayList<Float> giveZScoreforTimeStep(float[] x){

		ArrayList<Float> arr = new ArrayList<Float>();

		for(int i=1; i<x.length; i++) {

			float[] partofarray = makePartOfArray(x, i);
			float zs = CountZS(partofarray, x);
			if(zs == -1) {
				continue;
			}
			else{
				arr.add(zs);
			}

		}
		return arr;
	}

	public HashMap<String, ArrayList<Float>> giveMapforDetectAlgoPaint(TimeSeries ts){

		HashMap<String, ArrayList<Float>> map = new HashMap<String, ArrayList<Float>>();
		for(String i: ts.getHashMap().keySet()){

			float[] f = SimpleAnomalyDetector.convertToFloat(ts.getHashMap().get(i));
			ArrayList<Float> arr = giveZScoreforTimeStep(f);
			map.put(i, arr);
		}

		return map;
	}

	public static float CountZS(float[] partofx, float[] fullx) { // do only the ZScore count itself (without finding the max)

		float avg = StatLib.avg(partofx);
		float denominator = StatLib.var(partofx);
		denominator = Math.abs(denominator);
		denominator = (float) Math.sqrt(denominator);
		if(denominator == 0) {
			return -1;
		}
		float numerator = Math.abs(fullx[partofx.length] - avg);
		float zs = numerator / denominator;

		return zs;
	}

	public static float[] makePartOfArray(float[] x, int i) {

		float[] y = new float[i];
		for(int j=0; j<i; j++) {

			y[j]=x[j];
		}

		return y;
	}

	public float[] ChangeArrayListtoArrayFloat(ArrayList<Float> arr) {

		// this is the same as the method "converttofloat" in "SimpleAnomalyAdetector"

		float[] x = new float[arr.size()];
		for(int i=0; i<arr.size(); i++) {
			x[i] = arr.get(i);
		}

		return x;
	}

	///////////////////////////////////// Plug-In /////////////////////////////////////////////

	@Override
	public void learnNormal(TimeSeries data) {

		ZScoreMap = new HashMap<String, Float>();
		for(int i=0; i<data.getsizefFeatures(); i++) {

			String featurename = data.getFeatures().get(i);
			float[] featuresNamevalue = SimpleAnomalyDetector.convertToFloat(data.getHashMap().get(featurename));
			float tx = FindMaxZS(featuresNamevalue);
			ZScoreMap.put(featurename, tx);
		}
	}

	@Override
	public List<AnomalyReport> detect(TimeSeries data) { // check if the ZScore is higher than the maximum
		//Anomalies = new HashMap<String,Float>();
		AnomalyList = new ArrayList<AnomalyReport>();

		for(int i=0; i<data.getsizefFeatures(); i++) {

			String feature = data.getFeatures().get(i);
			float[] featurevalue = SimpleAnomalyDetector.convertToFloat(data.getHashMap().get(feature));
			for(int j=1; j<data.getsizeArrayList(); j++) { // j = time-step

				float[] partofarray = makePartOfArray(featurevalue, j);
				float zs = CountZS(partofarray, featurevalue);
				zmap.add(zs);
				if(zs > ZScoreMap.get(feature)) {
					AnomalyReport ar = new AnomalyReport(feature, j);
					AnomalyList.add(ar);
				}
			}
		}

		return AnomalyList;
	}
}
