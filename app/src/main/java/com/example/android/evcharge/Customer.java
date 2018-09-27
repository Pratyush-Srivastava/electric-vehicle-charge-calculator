package com.example.android.evcharge;

public class Customer {
    private String name;
    private String emailId;
    private String password;
    private String typeOfPlan;
    private String car;
    private String account;

    public Customer(String name, String emailId, String password, String typeOfPlan, String car, String account) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
        this.typeOfPlan = typeOfPlan;
        this.car = car;
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Customer() {
        this.emailId = "something@something.com";
        this.password = "123456";
        this.typeOfPlan = "prepaid";
        this.car = "suv";
        this.account="0";


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfPlan() {
        return typeOfPlan;
    }

    public void setTypeOfPlan(String typeOfPlan) {
        this.typeOfPlan = typeOfPlan;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
