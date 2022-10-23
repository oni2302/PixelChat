package com.oni.pixelchat;

public class User {
    String fName
            ,lName
            ,phone="";
    int day=-1,month=-1,year=-1;
    int gender=-1;

    public User(String phone, int day, int month, int year, int gender) {
        this.phone = phone;
        this.day = day;
        this.month = month;
        this.year = year;
        this.gender = gender;
    }

    public User(int gender) {
        this.gender = gender;
    }

    public User(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public User(String phone) {
        this.phone = phone;
    }

    public User(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public User() {
    }
}
