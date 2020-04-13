package com.example.myapplication;

import java.util.Date;

public class HelperNotificationData {

    String userName;
    String userPhone;
    String title;
    String dateOfNotification;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getTitle() {
        return title;
    }

    public String getDateOfNotification() {
        return dateOfNotification;
    }

    public HelperNotificationData(String userName, String userPhone, String title, String dateOfNotification) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.title = title;
        this.dateOfNotification = dateOfNotification;
    }
}
