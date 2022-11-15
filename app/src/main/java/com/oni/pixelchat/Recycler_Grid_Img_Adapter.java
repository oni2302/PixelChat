package com.oni.pixelchat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.oni.pixelchat.databinding.RecyclerGridImageItemBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Recycler_Grid_Img_Adapter extends RecyclerView.Adapter<Recycler_Grid_Img_Adapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<String> imageList;
    int currentSelectPosition=-1;


    static String filePath="";
    CheckBox lastCheckBox;

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
        int currentPosition = position;
        File f = new File(imageList.get(imageList.size()-position-1));
        Picasso.get().load(f).resize(350,350).centerCrop().into(holder.image_grid_item);
        holder.recyclerGridImageItemBinding.selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if(checkBox.isChecked()) {
                    checkBox.setChecked(true);
                    currentSelectPosition=currentPosition;
                    if (lastCheckBox != null) {
                        lastCheckBox.setChecked(false);
                    }
                    lastCheckBox = checkBox;
                    filePath = imageList.get(imageList.size()-currentSelectPosition-1);
                }
                else {
                    checkBox.setChecked(false);
                    lastCheckBox = null;
                    currentSelectPosition=-1;
                    filePath = "";
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerGridImageItemBinding recyclerGridImageItemBinding;
        ImageView image_grid_item;

        public ViewHolder(@NonNull RecyclerGridImageItemBinding recyclerGridImageItemBinding) {
            super(recyclerGridImageItemBinding.getRoot());
            this.recyclerGridImageItemBinding = recyclerGridImageItemBinding;
            this.image_grid_item = recyclerGridImageItemBinding.imageGridItem;
        }
    }
}
