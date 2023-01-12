package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button mClient, mSitter;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSitter = (Button) findViewById(R.id.Sitter);
        mClient = (Button) findViewById(R.id.Client);
        mAuth = FirebaseAuth.getInstance();

        mSitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SitterLoginActivity.class);
                startActivity(intent);

            }
        });

        mClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClientLoginActivity.class);
                startActivity(intent);

            }
        });


    }


}
