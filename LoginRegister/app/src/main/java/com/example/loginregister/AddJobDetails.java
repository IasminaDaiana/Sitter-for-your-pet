package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddJobDetails extends AppCompatActivity {
    private EditText dateText, cityText,messageText;
    private RadioGroup radioJob;
    private RadioButton selectedCheckBox;
    private Button findButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_details);

        dateText = (EditText) findViewById(R.id.editTextDate);
        cityText = (EditText) findViewById(R.id.editTextTextPersonName8);
        messageText = (EditText) findViewById(R.id.editTextTextPersonName10);
        radioJob = (RadioGroup) findViewById(R.id.radioGroup2);
        radioJob.clearCheck();
        findButton = (Button)findViewById(R.id.button6);





        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int variantSelected = radioJob.getCheckedRadioButtonId();
                selectedCheckBox = findViewById(variantSelected);
                String date = dateText.getText().toString();
                String city = cityText.getText().toString();
                String message= messageText.getText().toString();
                String job;
                job = selectedCheckBox.getText().toString();
                FirebaseAuth  mAuth= FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("/Jobs/");
                ReadWriteJobDetails readWriteJobDetails = new ReadWriteJobDetails(date,city,message,job);
                referenceProfile.child(firebaseUser.getUid()).setValue(readWriteJobDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            firebaseUser.sendEmailVerification();
                            Toast.makeText(AddJobDetails.this, "Job created successfully", Toast.LENGTH_SHORT).show();
                            Intent savedProfile = new Intent(AddJobDetails.this, MatchProfiles.class);
                            savedProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(savedProfile);
                            finish();
                        }
                        else {
                            Toast.makeText(AddJobDetails.this, "Saving job details failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
    }


}