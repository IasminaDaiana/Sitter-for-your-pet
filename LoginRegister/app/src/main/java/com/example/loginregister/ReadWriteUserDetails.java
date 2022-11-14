package com.example.loginregister;

public class ReadWriteUserDetails {
    public String fullName, gender, mobile;

    public ReadWriteUserDetails(){};
    public ReadWriteUserDetails(String textFullName,  String textGender, String textMobile){
        this.fullName = textFullName;
        this.gender = textGender;
        this.mobile = textMobile;
    }
}
