package com.example.loginregister;

public class User {
    public String fullName,profilePictureUrl, city, mobileNumber;
    public User(){};
    public User(String textFullName, String profilePictureUrl, String city, String mobileNumber){
        this.fullName = textFullName;
        this.profilePictureUrl = profilePictureUrl;
        this.city = city;
        this.mobileNumber = mobileNumber;
    }
    public String getFullName(){
        return fullName;
    }
    public String getProfilePictureUrl(){
        return profilePictureUrl;
    }
    public String getCity(){return city;}
    public String getMobileNumber(){return mobileNumber;}
}
