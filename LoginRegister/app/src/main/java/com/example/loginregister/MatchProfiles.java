package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchProfiles extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<User> userList ;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_profiles);

        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(MatchProfiles.this,userList);
        recyclerView.setAdapter(userAdapter);

      //  if(getIntent().getExtras()!= null ){
            readUsers();



    }

    private void readUsers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("/Jobs/")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid() );
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result;
                String city = snapshot.child("city").getValue().toString();


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("/Users/Sitter");
               // DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("/Users/Client").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                 Query query = reference.orderByChild("city").equalTo(city);
                 query.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {

                         userList.clear();
                         for(DataSnapshot dataSnapshot  : snapshot.getChildren()){
                             User user = dataSnapshot.getValue(User.class);
                             userList.add(user);

                         }
                         //userAdapter.notifyDataSetChanged();
                         userAdapter = new UserAdapter(MatchProfiles.this, userList);
                         recyclerView.setAdapter(userAdapter);
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}