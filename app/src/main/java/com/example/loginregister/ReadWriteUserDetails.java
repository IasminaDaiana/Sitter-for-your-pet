package com.example.loginregister;

public class ReadWriteUserDetails {
    public String fullName, gender, mobile, email, pass, role;

    public ReadWriteUserDetails(String textFullName, String textGender, String textMobile){};
    public ReadWriteUserDetails(String textFullName,  String textGender, String textMobile, String textEmail, String textPass, String textRole){
        this.fullName = textFullName;
        this.gender = textGender;
        this.mobile = textMobile;
        this.email = textEmail;
        this.pass = textPass;
        this.role = textRole;


    }
}
