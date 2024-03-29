package com.oni.pixelchat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oni.pixelchat.databinding.ActivityMainBinding;
import com.oni.pixelchat.databinding.FragmentInboxBinding;

import java.util.ArrayList;

public class InboxItemAdapter extends RecyclerView.Adapter<InboxItemAdapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<User> arrayList;
    User user;

    public InboxItemAdapter(Context context, int layout, ArrayList<User> arrayList) {
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
        user = arrayList.get(position);
        String name;
        name =user.getfName()+" "+user.getlName();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child(user.getId()).limitToLast(1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Log.d("DEBUG",dataSnapshot.getValue().toString());
                        MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);
                        holder.inbox_message_recycler_item_edtLastMessage.setText(messageItem.getMessage());
                    }
                }
                else{

                }
            }
        });
        holder.inbox_message_recycler_item_imgMain.setImageResource(R.drawable.test_avt_img);
        holder.inbox_message_recycler_item_edtName.setText(name);
        Bundle b = new Bundle();
        b.putString("Name",name);
        b.putString("UID",user.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new InboxFragment();
                fragment.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_main_activity,fragment).addToBackStack("Home-Inbox").setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                ).commit();
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
