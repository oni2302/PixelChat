package com.oni.pixelchat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.oni.pixelchat.databinding.MessageItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageInboxAdapter extends RecyclerView.Adapter<MessageInboxAdapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<MessageItem> arrayList;
    public MessageInboxAdapter(Context context, int layout, ArrayList<MessageItem> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MessageItemLayoutBinding binding = MessageItemLayoutBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageItem messageItem = arrayList.get(position);
        holder.messageItemLayoutBinding.setMessageItem(messageItem);
        if(messageItem.isPicture()){
            Picasso.get().load(messageItem.getMessage()).fit().centerCrop().into(holder.messageItemLayoutBinding.ivPicture);
            Bundle b = new Bundle();
            b.putString("DownloadUrl",messageItem.getMessage());
            holder.messageItemLayoutBinding.ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisplayImageFragment fragment = new DisplayImageFragment();
                    fragment.setArguments(b);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_main_activity,fragment).addToBackStack("Inbox->DisplayImage").commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MessageItemLayoutBinding messageItemLayoutBinding;
        public ViewHolder(@NonNull MessageItemLayoutBinding messageItemLayoutBinding) {
            super(messageItemLayoutBinding.getRoot());
            this.messageItemLayoutBinding = messageItemLayoutBinding;
        }
    }
}
