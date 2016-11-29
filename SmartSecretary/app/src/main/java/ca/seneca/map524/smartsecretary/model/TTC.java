package ca.seneca.map524.smartsecretary.model;

import java.util.ArrayList;

/**
 * Created by Tony on 11/14/2016.
 */

public class TTC {
    private String name;
    private ArrayList<String> times;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }

    public ArrayList<String> getTimes() {
        return times;
    }
}
