package com.example.android.evcharge;

public class Transaction {
    private String power;
    private String duration;
    private String money;
    private String location;
    private String timeStamp;

    public Transaction() {
        power="0";
        duration="0";
        money="0";
        location="0";
        timeStamp="0";

    }

    public Transaction(String power, String duration, String money, String location, String timeStamp) {
        this.power = power;
        this.duration = duration;
        this.money = money;
        this.location = location;
        this.timeStamp = timeStamp;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
