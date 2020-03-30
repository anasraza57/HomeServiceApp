package com.example.myapplication;

import java.util.ArrayList;

public class JobRowData {

    ArrayList<Time> time=new ArrayList<>();
    boolean isSelected;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JobRowData(String title) {
        this.title = title;
        isSelected=false;

    }

    public ArrayList<Time> getTime() {
        return time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setTime(ArrayList<Time> time) {
        this.time = time;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public JobRowData(ArrayList<Time> time, boolean isSelected) {
        this.time = time;
        this.isSelected = isSelected;
    }

    public JobRowData(ArrayList<Time> time) {
        this.time = time;
    }
}
