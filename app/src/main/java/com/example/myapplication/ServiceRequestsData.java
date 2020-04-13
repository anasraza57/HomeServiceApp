package com.example.myapplication;

public class ServiceRequestsData {
    String endTime;
    String startTime;
    String title;
    String userName;
    String userPhone;
    String id;

    public String getId() {
        return id;
    }

    public ServiceRequestsData(String endTime, String startTime, String title, String userName, String userPhone, String id) {
        this.endTime = endTime;
        this.startTime = startTime;
        this.title = title;
        this.userName = userName;
        this.userPhone = userPhone;
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }


    public ServiceRequestsData(String endTime, String startTime, String title, String userName, String userPhone) {

        this.endTime = endTime;
        this.startTime = startTime;
        this.title = title;
        this.userName = userName;
        this.userPhone = userPhone;

    }
}
