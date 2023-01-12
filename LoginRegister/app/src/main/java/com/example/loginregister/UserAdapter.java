package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.activity_user_adapter,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.name.setText(user.getFullName());
        holder.mobile.setText(user.getMobileNumber());
        holder.sendReq.setVisibility(View.VISIBLE);
        //Glide.with(context).load(user.getProfilePictureUrl()).into(holder.profilePic);
      //  Picasso.get().load(userList.get(position).getProfilePictureUrl()).into(holder.profilePic);
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profilePic;
        public TextView name, mobile;
        public Button sendReq;
        public ViewHolder(View itemView){
            super(itemView);

          //  profilePic = itemView.findViewById(R.id.imageView17);
            name = itemView.findViewById(R.id.textView24);
            sendReq = itemView.findViewById(R.id.button7);
            mobile = itemView.findViewById(R.id.textView25);

        }

    }
}