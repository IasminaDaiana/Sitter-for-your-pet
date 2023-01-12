package com.example.loginregister;

public class ReadWriteJobDetails {
    public String date,city,message,job;
    public ReadWriteJobDetails(){};
    public ReadWriteJobDetails(String textDate, String textCity, String textMessage, String textJob){
        this.date = textDate;
        this.city = textCity;
        this.message = textMessage;
        this.job = textJob;
    }
}
