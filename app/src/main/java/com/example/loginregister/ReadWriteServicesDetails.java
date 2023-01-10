package com.example.loginregister;

import android.widget.CheckBox;
import android.widget.EditText;

public class ReadWriteServicesDetails  {

    public String date, city, message;
    CheckBox mFirstCheckBox, mSecondCheckBox, mThirdCheckbox;

    public ReadWriteServicesDetails(){};
    public ReadWriteServicesDetails(String textDate,  String textCity, String textMessage, CheckBox textmFirstCheckBox, CheckBox textmSecondCheckBox, CheckBox textmThirdCheckbox){
        this.date= textDate;
        this.city = textCity;
        this.message = textMessage;
        this.mFirstCheckBox = textmFirstCheckBox;
        this.mSecondCheckBox = textmSecondCheckBox;
        this.mThirdCheckbox = textmThirdCheckbox;
        }

    public ReadWriteServicesDetails(EditText date, EditText city, EditText message, CheckBox mFirstCheckBox, CheckBox mSecondCheckBox, CheckBox mThirdCheckbox) {}
    }