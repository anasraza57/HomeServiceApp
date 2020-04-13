package com.example.myapplication;

public class HelpersData {

    String cnic;
    String name;
    String password;
    String phone;
    String gender;

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }

    public HelpersData() {
    }

    public String getGender() {
        return gender;
    }

    public HelpersData(String cnic, String name, String password, String phone, String gender) {
        this.cnic = cnic;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
    }
}
