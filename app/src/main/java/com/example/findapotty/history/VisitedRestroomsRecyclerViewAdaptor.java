package com.example.findapotty.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findapotty.Restroom;
import com.example.findapotty.user.User;
import com.example.findapotty.databinding.VisitedRestroomPreviewBinding;
import com.example.findapotty.user.VisitedRestroomsManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VisitedRestroomsRecyclerViewAdaptor extends RecyclerView.Adapter<VisitedRestroomsRecyclerViewAdaptor.ViewHolder>{

    private VisitedRestroomPreviewBinding binding;
    Context context;

    public VisitedRestroomsRecyclerViewAdaptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = VisitedRestroomPreviewBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Restroom restroom = VisitedRestroomsManager.getInstance().getRestroomByIndex(position);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        StorageReference ref = storageRef.child(restroom.getPhotoPath());
        ref.getDownloadUrl().addOnSuccessListener(uri -> {
            Glide.with(context)
                    .asBitmap()
                    .dontAnimate()
                    .load(uri)
                    .into(holder.restroomPhoto);
        });
        holder.restroomName.setText(restroom.getName());
        holder.restroomAddress.setText(restroom.getAddress());
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        ref.child("users").child(User.getInstance().getUserId()).child("visitedRestrooms").addValueEventListener(
//        )

    }

    @Override
    public int getItemCount() {
        return VisitedRestroomsManager.getInstance().getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView restroomPhoto;
        TextView restroomName;
        TextView restroomAddress;
        RelativeLayout parentLayout;

        public ViewHolder(VisitedRestroomPreviewBinding binding) {
            super(binding.getRoot());
            restroomPhoto = binding.vrpRestroomPhoto;
            restroomName = binding.vrpRestroomName;
            restroomAddress = binding.vrpRestroomAddress;
            parentLayout = binding.visitedRestroomItem;
        }
    }
}