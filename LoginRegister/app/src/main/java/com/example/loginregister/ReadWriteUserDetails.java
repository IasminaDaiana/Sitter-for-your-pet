package com.example.loginregister;

public class ReadWriteUserDetails {
    public String fullName, gender, mobile, email, pass, role, city;

    public ReadWriteUserDetails(){};
    public ReadWriteUserDetails(String textFullName,  String textGender, String textMobile, String textEmail, String textPass, String textRole, String textCity){
        this.fullName = textFullName;
        this.gender = textGender;
        this.mobile = textMobile;
        this.email = textEmail;
        this.pass = textPass;
        this.role = textRole;
        this.city= textCity;


    }
    public String getFullName(){
        return fullName;
    }

}
