package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Profiles extends AppCompatActivity {
    Button jobDetailsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        jobDetailsButton = (Button)findViewById(R.id.button5);

        jobDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobdetails = new Intent(Profiles.this, AddJobDetails.class);
                startActivity(jobdetails);
            }
        });
    }
}