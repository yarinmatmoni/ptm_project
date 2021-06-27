package model;

public class Property {

  private String name;
  private float min;
  private float max;

    public Property(String nm, float min, float max) {
        this.name = nm;
        this.min = min;
        this.max = max;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMin(float val1) {
        this.min = val1;
    }

    public void setMax(float val2) {
        this.max = val2;
    }

    public String getName() {
        return name;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
