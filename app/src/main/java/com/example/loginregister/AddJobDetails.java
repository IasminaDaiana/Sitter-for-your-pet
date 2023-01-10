package com.example.loginregister;

import static com.example.loginregister.R.id.nav_host_fragment_content_read_write_services_details;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class AddJobDetails extends AppCompatActivity {

    EditText date;
    DatePickerDialog datePickerDialog;
    private EditText city;
private EditText message;
private CheckBox mFirstCheckBox, mSecondCheckBox, mThirdCheckBox;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth authProfile;
        StorageReference storageReference;
        FirebaseAuth mAuth = null;
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        setContentView(R.layout.activity_add_job_details);
        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.date);
        // perform click event on edit text
        Button saveBtn = (Button) findViewById(R.id.button6);
        city = (EditText) findViewById(R.id.textView21);
        message = findViewById(R.id.editTextTextPersonName10);



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddJobDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("/Jobs/");
                ReadWriteServicesDetails writePetDetails = new ReadWriteServicesDetails(date, city, message, mFirstCheckBox, mSecondCheckBox, mThirdCheckBox);
                if(mFirstCheckBox.isChecked())

                {
                    referenceProfile.child("1").setValue("DayCare");
                }

                if(mSecondCheckBox.isChecked())

                {
                    referenceProfile.child("2").setValue("DayWalking");
                }

                if(mThirdCheckBox.isChecked())

                {
                    referenceProfile.child("3").setValue("Spa pet");
                }

                Task<Void> voidTask = referenceProfile.child(firebaseUser.getUid()).setValue(nav_host_fragment_content_read_write_services_details).addOnCompleteListener(new OnCompleteListener<Void>() {



                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //i++;
                            firebaseUser.sendEmailVerification();
                            Toast.makeText(AddJobDetails.this, "Profile created successfully", Toast.LENGTH_SHORT).show();
                            Intent savedProfile = new Intent(AddJobDetails.this, Profiles.class);
                            savedProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(savedProfile);
                            finish();
                        } else {
                            Toast.makeText(AddJobDetails.this, "Saving Services  failed. Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        });

    }}