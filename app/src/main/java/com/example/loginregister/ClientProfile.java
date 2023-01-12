package com.example.loginregister;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ClientProfile extends AppCompatActivity {
    private final int GALLERY_REQ_CODE = 1000;
    ImageView imgGallery;
    private TextView textViewWelcome, textViewFullName, textViewEmail, textViewGender, textViewMobile;
    private ProgressBar progressBar;
    private String fullName, email, gender, mobile;
    private FirebaseAuth authProfile;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        getSupportActionBar().setTitle("My Account");
        imgGallery = findViewById(R.id.imgGallery);
        Button  btnGallery = findViewById(R.id.btnCamera);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewFullName = findViewById(R.id.textView_show_full_name);
        textViewEmail = findViewById(R.id.textView_show_mail);
        textViewGender = findViewById(R.id.textView_show_gender);
        textViewMobile = findViewById(R.id.textView_show_mobile);

        authProfile = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("client/"+authProfile.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imgGallery);
            }
        });
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(ClientProfile.this, "Something went wrong! User's details are not available at the moment", Toast.LENGTH_LONG).show();

        } else {
            showUserProfile(firebaseUser);
        }

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });
    }
    /* Future<void> reload() async {
         await _delegate.reload();
     }*/
    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        //extracting user reference from Database for "registered users"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Sitter");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);

                if (readUserDetails != null) {
                    fullName = readUserDetails.fullName;
                    email = firebaseUser.getEmail();
                    gender = readUserDetails.gender;
                    mobile = readUserDetails.mobile;


                    textViewWelcome.setText("Welcome, " + fullName + "!");
                    textViewFullName.setText(fullName);
                    textViewEmail.setText(email);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClientProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }); ;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            if(requestCode == GALLERY_REQ_CODE){

                // imgGallery.setImageURI(imageUri);
            }
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //upload image to firebaseStorage
        final StorageReference fileRef = storageReference.child("client/"+authProfile.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imgGallery);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ClientProfile.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
}