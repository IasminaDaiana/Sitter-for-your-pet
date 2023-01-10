package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

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