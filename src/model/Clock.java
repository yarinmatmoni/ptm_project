package model;

public class Clock {

    Seconds seconds;
    Minutes minutes;
    Hours hours;

    public class Seconds{
        Integer i;
        Integer j;
    }
    public class Minutes{
        int i;
        int j;
    }
    public class Hours{
        int i;
        int j;
    }

    public Clock(){

        this.seconds.i = 0;
        this.seconds.j = 0;
        this.minutes.i = 0;
        this.minutes.j = 0;
        this.hours.i = 0;
        this.hours.j = 0;
    }



}
