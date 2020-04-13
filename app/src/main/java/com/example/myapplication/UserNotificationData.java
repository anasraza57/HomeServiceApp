package com.example.myapplication;

import java.util.Date;

public class UserNotificationData {

    String helperName;
    String helperPhone;
    String helperGender;
    String helperCNIC;

    public String getHelperGender() {
        return helperGender;
    }

    public String getHelperCNIC() {
        return helperCNIC;
    }

    public String getId() {
        return id;
    }

    String title;
    String dateOfNotification;

    public UserNotificationData(String helperName, String helperPhone, String title, Date dateOfNotification, String id) {
        this.helperName = helperName;
        this.helperPhone = helperPhone;
        this.title = title;
       // this.dateOfNotification = dateOfNotification;
        this.id = id;
    }

    public UserNotificationData(String helperName, String helperPhone, String helperGender, String helperCNIC, String title, String dateOfNotification, String id) {
        this.helperName = helperName;
        this.helperPhone = helperPhone;
        this.helperGender = helperGender;
        this.helperCNIC = helperCNIC;
        this.title = title;
        this.dateOfNotification = dateOfNotification;
        this.id = id;
    }

    String id;


    public String getHelperName() {
        return helperName;
    }

    public String getHelperPhone() {
        return helperPhone;
    }

    public String getTitle() {
        return title;
    }

    public String getDateOfNotification() {
        return dateOfNotification;
    }

    public UserNotificationData(String helperName, String helperPhone, String title, Date dateOfNotification) {
        this.helperName = helperName;
        this.helperPhone = helperPhone;
        this.title = title;
      //  this.dateOfNotification = dateOfNotification;
    }
}
