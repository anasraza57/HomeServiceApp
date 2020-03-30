package com.example.myapplication;

import java.util.ArrayList;

public class ServicesRowData {

    ArrayList<Time> times=new ArrayList<>();
    String title;

    public void setTimes(ArrayList<Time> times) {
        this.times = times;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ServicesRowData(String title) {
        this.title = title;
    }

    public ArrayList<Time> getTimes() {
        return times;
    }

    public String getTitle() {
        return title;
    }
}
