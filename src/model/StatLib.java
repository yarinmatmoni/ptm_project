package model;


public class StatLib {



    // simple average
    public static float avg(float[] x){

        float count = 0;
        for (int i = 0; i<x.length; i++)
        {
            count += x[i];
        }
        count = (count / x.length);
        return count;

    }

    // returns the variance of X and Y
    public static float var(float[] x){
        float count = 0;
        for (int i = 0; i<x.length; i++)
        {
            count+= (x[i] * x[i]);
        }
        count = (count / x.length);
        float u = (avg(x) * avg(x));
        float V = (count - u);
        return V;


    }

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y){

        if (x == null || y == null)
        {
            return -1;
        }
        float ex = avg(x);
        float ey = avg (y);

        if (x.length > y.length)
        {
            float count1 = 0;
            for(int j = 0; j<y.length; j++)
            {
                count1 = (x[j] * y[j]);
            }
            count1 = count1 / y.length;

            return (count1 - (ex*ey));
        }
        if (x.length == y.length)
        {
            float count2 = 0;
            for (int i = 0; i<x.length; i++)
            {
                count2 += (x[i] * y[i]);
            }
            count2 = count2 / x.length;

            return (count2 - (ex*ey));


        }
        if(x.length < y.length)
        {
            float count3 = 0;
            for (int t = 0; t < x.length; t++ )
            {
                count3 += (x[t] * y[t]);
            }
            count3 = count3 / x.length;

            return (count3 - (ex*ey));

        }
        return 0;

    }


    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y){

        float V1 = (float) Math.sqrt(var(x));
        float V2 = (float) Math.sqrt(var(y));
        float C = cov(x,y);
        return (C/ (V1 * V2));

    }

    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points){


        float[] arrx = new float[points.length];
        float[] arry = new float[points.length];
        for (int i = 0; i< points.length; i++)
        {
            arrx[i] = points[i].x;
            arry[i] = points[i].y;
        }
        float a, b;
        a = cov(arrx, arry) / var(arrx);
        float avgx, avgy;
        avgx = avg(arrx);
        avgy = avg(arry);
        b = avgy - (a * avgx);

        Line line = new Line(a,b);
        return line;

    }

    // returns the deviation between point p and the line equation of the points
    public static float dev(Point p,Point[] points){
        Line l = linear_reg(points);
        float dev = dev(p, l);
        return dev;

    }

    // returns the deviation between point p and the line
    public static float dev(Point p,Line l){
        float fx = l.f(p.x);
        float dev = fx - p.y;
        if (dev < 0)
        {
            return (-1)*dev;
        }
        return dev;

    }

}