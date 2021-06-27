package model;

import java.util.List;

import model.TimeSeries;

public interface TimeSeriesAnomalyDetector {
    void learnNormal(TimeSeries ts);
    List<AnomalyReport> detect(TimeSeries ts);

}
