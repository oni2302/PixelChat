package com.oni.pixelchat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.oni.pixelchat.databinding.ActivityMainBinding;
import com.oni.pixelchat.databinding.FragmentInboxBinding;

import java.util.ArrayList;

public class InboxItemAdapter extends RecyclerView.Adapter<InboxItemAdapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<InboxItem> arrayList;
    public InboxItemAdapter(Context context, int layout, ArrayList<InboxItem> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InboxItem inboxItem = arrayList.get(position);
        holder.inbox_message_recycler_item_imgMain.setImageResource(inboxItem.getPic());
        holder.inbox_message_recycler_item_edtName.setText(inboxItem.getName());
        holder.inbox_message_recycler_item_edtLastMessage.setText(inboxItem.getLastMessage());
        Bundle b = new Bundle();
        b.putString("Username",inboxItem.getUserName());
        b.putString("Name",inboxItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new InboxFragment();
                fragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_main_activity,fragment).addToBackStack("Home-Inbox").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView inbox_message_recycler_item_imgMain;
        public TextView inbox_message_recycler_item_edtName
                ,inbox_message_recycler_item_edtLastMessage;

        public ViewHolder(View v) {
            super(v);
            inbox_message_recycler_item_imgMain = v.findViewById(R.id.inbox_message_recycler_item_imgMain);
            inbox_message_recycler_item_edtName = v.findViewById(R.id.inbox_message_recycler_item_edtName);
            inbox_message_recycler_item_edtLastMessage = v.findViewById(R.id.inbox_message_recycler_item_edtLastMessage);

        }
    }

}
