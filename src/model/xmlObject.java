package model;

import java.util.ArrayList;
import java.util.HashMap;

public class xmlObject {


        private HashMap<String, ArrayList<Float>> map = new HashMap<>();
        private float speed;
        private String port;
        private String ip;

        public  xmlObject() {
            this.speed = 0;
            this.port = "5400";
            this.ip = "127.0.0.1";
        }

        public HashMap<String, ArrayList<Float>> getMap() {
            return map;
        }

        public void setMap(HashMap<String, ArrayList<Float>> map) {
            this.map = map;
        }

        public float getSpeed() {
            return speed;
        }

        public void setDataSample(float dataSample) {
            this.speed = dataSample;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String p) {
            this.port = p;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String i) {
            this.ip = i;
        }

//    public xmlObject(float sampleData,String normalFlight,String anomalyAlgo,HashMap<String, Pair<Float,Float>> map) {
//        this.setDataSample(sampleData);
//        this.setAnomalyAlgo(anomalyAlgo);
//        this.setNormalFlight(normalFlight);
//        this.setMap(map);
//    }

}


