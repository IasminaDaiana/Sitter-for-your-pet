package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SitterMenuActivity extends AppCompatActivity {
    Button myProfile;
    TextView textView;
    TextView personalUserName;
    private FirebaseAuth authProfile;
    private String fullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_menu);
        myProfile = (Button) findViewById(R.id.buttonMyProfile);
        textView = (TextView) findViewById(R.id.textView3) ;



        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();


        if(firebaseUser == null){
            Toast.makeText(SitterMenuActivity.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_LONG).show();

        } else {
            showUserProfile(firebaseUser);
        }






        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(SitterMenuActivity.this, SitterProfile.class);
                startActivity(profileIntent);
            }
        });
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        //extracting user reference from Database for "registered users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users/Sitter");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);

                if (readUserDetails != null) {
                   fullName = readUserDetails.fullName;

                    textView.setText("Welcome, " + fullName + "!");

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SitterMenuActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }); ;
    }
}