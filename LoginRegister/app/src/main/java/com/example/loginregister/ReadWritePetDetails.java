package com.example.loginregister;

public class ReadWritePetDetails {
    public String type, size, vaccinated, age, description, gender,name;
    public ReadWritePetDetails(){};
    public ReadWritePetDetails(String textType,  String textSize, String textVaccinated, String textAge, String textDescription, String textGender, String textName){
        this.type = textType;
        this.size = textSize;
        this.vaccinated = textVaccinated;
        this.age = textAge;
        this.description = textDescription;
        this.gender= textGender;
        this.name = textName;


    }
}
