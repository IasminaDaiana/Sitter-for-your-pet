package com.example.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
public class ClientRegisterActivity extends AppCompatActivity {
    private EditText userName,password;
    private EditText editTextRegisterFullName;
    private EditText editTextRegisterMobile;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private EditText editCity;
    private ProgressBar progressBar;
    TextView AccountExists;
    Button register;


    private ProgressDialog loadingBar;//Used to show the progress of the registration process
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);
        getSupportActionBar().setTitle("Register");
        FirebaseApp.initializeApp(ClientRegisterActivity.this);



        userName = (EditText) findViewById(R.id.username2);
        editTextRegisterMobile = (EditText) findViewById(R.id.editTextPhone) ;
        password = (EditText) findViewById(R.id.Password2);
        editTextRegisterFullName = (EditText) findViewById(R.id.editTextTextPersonName2);
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();
        AccountExists = (TextView) findViewById(R.id.Already_link);
        loadingBar = new ProgressDialog(this);
        editCity = (EditText)findViewById(R.id.editTextTextPersonName7);

        register = (Button) findViewById(R.id.submit_btn);

        AccountExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(ClientRegisterActivity.this, ClientLoginActivity.class);
                startActivity(loginIntent);
            }
        });
        //When user clicks on register create a new account for user
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);
                String textFullName = editTextRegisterFullName.getText().toString();
                String email = userName.getText().toString();
                String pwd = password.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textGender ;
                String textCity = editCity.getText().toString();
                if(TextUtils.isEmpty(textFullName)){
                    Toast.makeText(ClientRegisterActivity.this, "Please enter your full name",Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                }else if(TextUtils.isEmpty(email)){
                    Toast.makeText(ClientRegisterActivity.this, "Please enter your email",Toast.LENGTH_LONG).show();
                    userName.setError("Full name is required");
                    userName.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(ClientRegisterActivity.this, "Please re-enter your email",Toast.LENGTH_LONG).show();
                    userName.setError("Valid email is required");
                    userName.requestFocus();
                } else if(radioGroupRegisterGender.getCheckedRadioButtonId()== -1){
                    Toast.makeText(ClientRegisterActivity.this, "Please enter your gender",Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();

                } else if(TextUtils.isEmpty(textMobile)){
                    Toast.makeText(ClientRegisterActivity.this, "Please enter your mobile",Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile no is required");
                    editTextRegisterMobile.requestFocus();
                } else if(textMobile.length()!= 10){
                    Toast.makeText(ClientRegisterActivity.this,"Please re-enter your mobile no.", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Valid Mobile no is required");
                    editTextRegisterMobile.requestFocus();

                } else if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(ClientRegisterActivity.this, "Please enter your mobile",Toast.LENGTH_LONG).show();
                    password.setError("Password is required");
                    password.requestFocus();
                } else if(pwd.length()<6){
                    Toast.makeText(ClientRegisterActivity.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
                    password.setError("password should be at least 6 digits");
                    password.requestFocus();

                }
                else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    //     progressBar.setVisibility(view.VISIBLE);
                    createNewAccount(email,pwd,textFullName,textGender, textMobile, textCity);

                }

            }
        });
    }

    private void createNewAccount(String email, String pwd, String textFullName, String textGender, String textMobile, String textCity ) {
        FirebaseAuth  mAuth= FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(ClientRegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Toast.makeText(SitterRegisterActivity.this,"User registered succesfully", Toast.LENGTH_LONG).show();
                        if(task.isSuccessful())//If account creation successful print message and send user to Login Activity
                        {
                            Toast.makeText(ClientRegisterActivity.this,"User registered succesfully", Toast.LENGTH_LONG).show();
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFullName, textGender, textMobile, email, pwd, "client",textCity);

                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("/Users/Client");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(ClientRegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                        Intent loginIntent = new Intent(ClientRegisterActivity.this, ClientLoginActivity.class);
                                        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(loginIntent);
                                        finish();
                                    } else{
                                        Toast.makeText(ClientRegisterActivity.this, "User registered failed. Please try again", Toast.LENGTH_LONG).show();


                                    }

                                }
                            });
                        }
                        else//Print the error message incase of failure
                        {
                            String msg = task.getException().toString();
                            Toast.makeText(ClientRegisterActivity.this,"Error: "+msg,Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });

    }


}