package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClientMenuActivity extends AppCompatActivity  {
    Button homeButton, myActivity, accountButton, makeProfileButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        homeButton = (Button) findViewById(R.id.Homebutton);
        myActivity = (Button) findViewById(R.id.myActivity);
        accountButton = (Button) findViewById(R.id.buttonMyProfile);
        makeProfileButton = (Button) findViewById(R.id.button);


        makeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(ClientMenuActivity.this, PetProfile.class);
                startActivity(profileIntent);
            }
        });
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(ClientMenuActivity.this, AccountActivity.class);
                startActivity(accountIntent);
            }
        });
    }

}