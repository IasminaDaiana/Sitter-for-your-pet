package com.example.loginregister;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class PetProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int pic_id = 123;
    Button cameraOpen;
    Spinner spino;
    Spinner Vspino;
    String[] sizes = { "Small(0-7kg)", "Medium(7-15kg)", "Big(15-20kg)", "Huge(20+kg)" };
    String[] vacc = {"yes", "no"};
    ImageView clickImg;
    EditText typePet;
    EditText name;
    private FirebaseAuth authProfile;
    StorageReference storageReference;
    Button saveBtn;
    EditText ageText;
    EditText breed;
    EditText descr;
    private RadioGroup radioButtonGender;
     RadioButton radioButtonRegisterGenderSelected;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQ_CODE = 102;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        cameraOpen = (Button)findViewById(R.id.button2);
        spino = (Spinner)findViewById(R.id.spinner);
        Vspino = (Spinner) findViewById(R.id.spinner2);
        clickImg = (ImageView)findViewById(R.id.click_image);
        saveBtn = (Button)findViewById(R.id.button3);
        typePet = (EditText)findViewById(R.id.editTextTextPersonName) ;
        ageText = (EditText)findViewById(R.id.editTextTextPersonName3);
        breed = (EditText)findViewById(R.id.editTextTextPersonName4);
        descr = (EditText) findViewById(R.id.editTextTextPersonName5) ;
        name = (EditText) findViewById(R.id.editTextTextPersonName6);
        radioButtonGender = (RadioGroup) findViewById(R.id.radioGroup);
        radioButtonGender.clearCheck();
        
        


        spino.setOnItemSelectedListener(this);
        Vspino.setOnItemSelectedListener(this);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                sizes);
        ArrayAdapter ad2
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                vacc);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        ad2.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spino.setAdapter(ad);
        Vspino.setAdapter(ad2);

        cameraOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth  mAuth= FirebaseAuth.getInstance();
               int  selectedGenderId = radioButtonGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);
                String type = typePet.getText().toString();
                String size = spino.getSelectedItem().toString();
                String vaccinated = Vspino.getSelectedItem().toString();
                String age = ageText.getText().toString();
                String description = descr.getText().toString();
                String genderChoice = radioButtonRegisterGenderSelected.getText().toString();
                String Pname = name.getText().toString();
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
               // String[] array = new String[] {"1","2","3","4","5","6"};


                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("/Pets/");
                ReadWritePetDetails writePetDetails = new ReadWritePetDetails(type, size, vaccinated,age, description, genderChoice,Pname);
                referenceProfile.child(firebaseUser.getUid()).setValue(writePetDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //i++;
                            firebaseUser.sendEmailVerification();
                            Toast.makeText(PetProfile.this, "Profile created successfully", Toast.LENGTH_SHORT).show();
                            Intent savedProfile = new Intent(PetProfile.this, Profiles.class);
                            savedProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(savedProfile);
                            finish();
                        }
                        else {
                            Toast.makeText(PetProfile.this, "Saving Pet profile  failed. Please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }

    });
    }

    private void saveInFireBase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference profileRef = storageReference.child("Pets/"+authProfile.getCurrentUser().getUid()+"profile.jpg");

        UploadTask uploadTask = profileRef.putBytes(data);

    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to use camera ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQ_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap image = null;
        if (requestCode == CAMERA_REQ_CODE) {
            image = (Bitmap) data.getExtras().get("data");
            clickImg.setImageBitmap(image);
        }
        encodeBitmapAndSaveToFirebase(image);
    }

    private void encodeBitmapAndSaveToFirebase(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("/Pets/photo")
              //  .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0,
                               View arg1,
                               int position,
                               long id)
    {

        // make toastof name of course
        // which is selected in spinner
        Toast.makeText(getApplicationContext(),
                        sizes[position],
                        Toast.LENGTH_LONG)
                .show();
        Toast.makeText(getApplicationContext(),
                vacc[position],
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // Auto-generated method stub
    }
}