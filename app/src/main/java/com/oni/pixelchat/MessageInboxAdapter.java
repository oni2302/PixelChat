package com.oni.pixelchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oni.pixelchat.databinding.MessageItemLayoutBinding;

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
        holder.messageItemLayoutBinding.setMessages(messageItem);
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
