package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;



public class TimeSeries implements Serializable{ //

    private HashMap<String, ArrayList<Float>> Map;
    private ArrayList<String> Features;
    private int sizeFeatures; // how many features do we have
    private int sizeArrayList; // the size of the ArrayList


    public TimeSeries(File csvFileName) { // Constructor

        Map = new HashMap<String, ArrayList<Float>>();
        Features = new ArrayList<String>();
        loadCSV(csvFileName);
    }


    public TimeSeries() // Default Constructor // private void TimeSeries()
    {
        Map = new HashMap<String, ArrayList<Float>>();
        Features = new ArrayList<String>();

    }

    private void loadCSV(File csvFileName) { // load CSV file

        try(BufferedReader in = new BufferedReader(new FileReader(csvFileName)))
        {

            String line = new String();
            line = in.readLine();
            Scanner scan = new Scanner(line).useDelimiter(",");

            // first we read the features names

            int size = 0; // the number of features
            int sizeA = 0; // the number of lines (the number of members we have in ArrayList

            while (scan.hasNext()) // read the first line
            {
                this.Features.add(scan.next());
                size++;

            }

            this.setsizeFeatures(size);

            // initialize the ArrayList in every feature:

            for (int k = 0; k < this.sizeFeatures; k++)
            {
                ArrayList<Float> arr = new ArrayList<Float>();
                this.Map.put(this.Features.get(k), arr);
            }

            // now we read in every iteration new line from the CSV file
            // and insert the data to the map

            while ((line = in.readLine()) != null)
            {
                this.AddLine(line);
                sizeA++;
            }

            this.setsizeArrayList(sizeA);
            scan.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFeatures()
    {
        return (this.Features);
    }
    public void setFeatures(String s, int i)
    {
        this.Features.set(i, s);
    }
    public void setFeatures(ArrayList<String> arr) { // new method 18.4.21

        this.Features = arr;
    }
    public HashMap<String, ArrayList<Float>> getHashMap()
    {
        return (this.Map);
    }
    public void setHashMap(HashMap<String, ArrayList<Float>> hm)
    {
        this.Map = hm;
    }
    public int getsizefFeatures()
    {
        return Features.size();
    }
    public void setsizeFeatures(int size)
    {
        this.sizeFeatures = size;
    }
    public int getsizeArrayList()
    {
        String f = this.Features.get(0);
        int i = 0;
        return (this.Map.get(f).size());

    }
    public void setsizeArrayList(int size)
    {
        this.sizeArrayList = size;
    }

    public ArrayList<Float> getValofKey(String s) // get the ArrayList of the
    // specific feature
    {
        ArrayList<Float> list;
        list = this.Map.get(s);
        return list;
    }

//	public Float getValInFeatueByTime(String f, Float t)// by column a
//	{
//		int time = this.FindIndexinFeatureValues(this.Features.get(0), t); // find the
//		// index of the time (we send the time as feature and check where t shows up)
//		return (this.Map.get(f).get(time));
//	}

    public Float getValByFeatureAndIndex(String f, int index) // get value in f by its
    // time step
    {
        return (this.Map.get(f).get(index));
    }


    public int getIndexofFeature(String s) // Finds the index of s String in the
    //Feature ArrayList
    {
        int i = 0;
        int size = this.Features.size();
        for (i = 0; i< size; i++)
        {
            if (s == this.Features.get(i))
            {
                return i;
            }
        }
        return i;

    }


    public int FindIndexinFeatureValues(String f,float number)  // find the time-step
    //of number in feature f
    {
        int size = this.Map.values().size();
        int i = 0;
        for (i = 0; i< size; i++)
        {
            if (this.Map.get(f).get(i) == number)
            {
                return i;
            }
        }
        return i;
    }


    public void AddLine(ArrayList<Float> a) // Add line of ArrayList to the map
    {
        int size = this.Features.size();
        for (int i = 0; i< size; i++)
        {
            String s;
            s = this.Features.get(i); // the feature name in place i
            this.Map.get(s).add(a.get(i)); // add to the values of s the value of a in
            // place i
        }
    }

    public void AddLine(String line) // Add line of String to the map of values(float)
    {
        Scanner scan = new Scanner(line).useDelimiter(","); // line = A, B, C, D
        //scan.useDelimiter(",");
        for (int i = 0; i< this.sizeFeatures; i++)
        {
            String f = this.Features.get(i); // the feature name in place i
            Float val = scan.nextFloat();
            this.Map.get(f).add(val); // insert the value to the correct feature's
            // ArrayList
        }
        scan.close();
    }

    public ArrayList<Float> getRow(int index)
    {
        ArrayList<Float> row = new ArrayList<Float>();
        for (int i = 0; i<this.sizeFeatures; i++)
        {
            String f = this.Features.get(i);
            row.add(this.getValByFeatureAndIndex(f, index));
        }
        return row;
    }

    public void PrintMap()
    {
        for (int i = 0; i< this.sizeFeatures; i++)
        {
            System.out.println(this.getFeatures().get(i));
            System.out.println("->");
            String f = this.Features.get(i);
            for(int j = 0; j < this.sizeArrayList; j++)
            {
                System.out.println(this.Map.get(f).get(j) + ", ");
            }
            System.lineSeparator();
        }
    }


    public void createNewMapByFeaturesName(String firstline) { // create the keys in the map and new
        // ArrayLists to every feature

        Scanner scan = new Scanner(firstline).useDelimiter(",");
        int count = 0;
        while (scan.hasNext())
        {
            ArrayList<Float> arr = new ArrayList<Float>();
            String name = scan.next();
            this.Features.add(name); // add to the features array
            this.Map.put(name, arr); // add to the map and create new ArrayList
            count++;

        }
        this.setsizeFeatures(count);
        scan.close();
    }



}
