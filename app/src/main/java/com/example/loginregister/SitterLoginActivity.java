package com.example.loginregister;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SitterLoginActivity extends AppCompatActivity {
    EditText userName, password;
    Button login;
    TextView register, forgotPassword;
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication
    ProgressDialog loadingBar;
    TextView personalUserName;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_login);
        userName = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.registerLink);


        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        currentUser = mAuth.getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegister();
            }
        });


    }


    private void sendUserToRegister() {
        //When user wants to create a new account send user to Register Activity
        Intent registerIntent = new Intent(SitterLoginActivity.this, SitterRegisterActivity.class);
        startActivity(registerIntent);
    }

    private void AllowUserToLogin() {
        String email = userName.getText().toString().trim();
        String pwd = password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SitterLoginActivity.this, "Please enter email id", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(SitterLoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {

            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait ,Because Good things always take time");
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())//If account login successful print message and send user to main Activity
                            {
                                sendToMainActivity();
                                //   Toast.makeText(SitterLoginActivity.this,"Welcome to Reference Center",Toast.LENGTH_SHORT).show();
                                // sendToMainActivity();
                                loadingBar.dismiss();
                            } else//Print the error message incase of failure
                            {
                                String msg = task.getException().toString();
                                Toast.makeText(SitterLoginActivity.this, "Error: " + msg, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }
    }


    private void sendToMainActivity() {
        //This is to send user to MainActivity
        Intent MainIntent = new Intent(SitterLoginActivity.this, SitterMenuActivity.class);

        startActivity(MainIntent);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}