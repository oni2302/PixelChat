package com.oni.pixelchat;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oni.pixelchat.databinding.RecyclerGridImageItemBinding;

import java.util.ArrayList;

public class Recycler_Grid_Img_Adapter extends RecyclerView.Adapter<Recycler_Grid_Img_Adapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<String> imageList;

    public Recycler_Grid_Img_Adapter(Context context, int layout, ArrayList<String> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerGridImageItemBinding binding = RecyclerGridImageItemBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = Uri.parse(imageList.get(position));
        holder.recyclerGridImageItemBinding.imageGridItem.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerGridImageItemBinding recyclerGridImageItemBinding;

        public ViewHolder(@NonNull RecyclerGridImageItemBinding recyclerGridImageItemBinding) {
            super(recyclerGridImageItemBinding.getRoot());
            this.recyclerGridImageItemBinding = recyclerGridImageItemBinding;
        }
    }
}
