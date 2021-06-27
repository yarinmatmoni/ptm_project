package model;

public class CorrelatedFeatures {
    public final String feature1,feature2;
    public final float corrlation;
    public final Line lin_reg;
    public final float threshold;// the max we found after we run all the point in dev

    public CorrelatedFeatures(String feature1, String feature2, float corrlation, Line lin_reg, float threshold) {
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.corrlation = corrlation;
        this.lin_reg = lin_reg;
        this.threshold = threshold;
    }


}