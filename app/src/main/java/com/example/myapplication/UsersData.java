package com.example.myapplication;

public class UsersData {

    String phone;
    String password;
    String name;
    String address;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public UsersData(String phone, String password, String name, String address) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
    }
}
