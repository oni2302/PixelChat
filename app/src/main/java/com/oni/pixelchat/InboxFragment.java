package com.oni.pixelchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oni.pixelchat.databinding.FragmentInboxBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class InboxFragment extends Fragment {
    FragmentInboxBinding binding;
    RecyclerView message_box,recycler_grid_image;
    LinearLayoutManager layoutManager;
    ArrayList<MessageItem> arrayList;
    MessageInboxAdapter adapter;
    ImageView btn_send;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String uid;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edt_inbox_message;
    ArrayList<String> imageArrayList;
    int amountMessase = 20;
    int messageIncrease = 20;
    FrameLayout frameGetImage;
    boolean isShowImage = false;
    public InboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater,container,false);
        message_box=binding.messageBox;
        swipeRefreshLayout = binding.swiperefresh;
        edt_inbox_message = binding.edtInboxMessage;
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
        message_box.setLayoutManager(layoutManager);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        Bundle b = this.getArguments();
        String name = b.getString("Name");
        uid = b.getString("UID");
        binding.tvInboxName.setText(name);
        arrayList = new ArrayList<>();
        adapter = new MessageInboxAdapter(InboxFragment.this.getContext(),R.layout.message_item_layout,arrayList);
        message_box.setAdapter(adapter);
        // Inflate the layout for this fragment
        btn_send = binding.btnInboxSend;
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SendMessage(edt_inbox_message.getText().toString(),date.getTime());
            }
        });
        ReadMessage();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(amountMessase<arrayList.size()+ 1){
                    amountMessase+=messageIncrease;
                    ReadMessageRefreshing();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        imageArrayList = new ArrayList<>();
        frameGetImage = binding.frameGetImgInbox;
        frameGetImage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if(isShowImage){
                    imageArrayList = new ArrayList<>();
                    adapter.notifyDataSetChanged();
                    recycler_grid_image.setVisibility(View.GONE);
                }else{
                    imageArrayList = ImagePicker();
                    adapter.notifyDataSetChanged();
                    recycler_grid_image.setVisibility(View.VISIBLE);
                }
                isShowImage = !isShowImage;
            }
        });
        recycler_grid_image = binding.recyclerGridImage;
        recycler_grid_image.setVisibility(View.VISIBLE);
        imageArrayList = ImagePicker();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(binding.getRoot().getContext(),3);
        recycler_grid_image.setLayoutManager(layoutManager);
        Recycler_Grid_Img_Adapter adapter = new Recycler_Grid_Img_Adapter(binding.getRoot().getContext(),R.layout.recycler_grid_image_item,imageArrayList);
        recycler_grid_image.setAdapter(adapter);
        return binding.getRoot();
    }
    public void ReadMessage(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Messages").child(firebaseUser.getUid()).child(uid);

        reference.limitToLast(amountMessase).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MessageItem> messageList = new ArrayList<>();
                arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Log.d("DATA",dataSnapshot.getValue().toString());
                    MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);
                    messageList.add(messageItem);
                }
                for (int i =1 ;i<=arrayList.size();i++){messageList.remove(messageList.size()-1);}
                arrayList.addAll(0,messageList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void ReadMessageRefreshing(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Messages").child(firebaseUser.getUid()).child(uid);

        reference.limitToLast(amountMessase).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isComplete()){
                    ArrayList<MessageItem> messageList = new ArrayList<>();
                    DataSnapshot data = task.getResult();
                    for(DataSnapshot dataSnapshot:data.getChildren()){
                        Log.d("DATA",dataSnapshot.getValue().toString());
                        MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);
                        messageList.add(messageItem);
                    }
                    for (int i =1 ;i<=arrayList.size();i++){messageList.remove(messageList.size()-1);}
                    arrayList.addAll(0,messageList);
                    adapter.notifyItemRangeInserted(0,messageList.size());
                }
            }
        });
    }
    public void DisplayFragment(Fragment fragment){
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                );

        fragmentTransaction.replace(R.id.container_main_activity,fragment);
        fragmentTransaction.commit();
    }
    public void SendMessage(String message,long date){
        MessageItem messageItem = new MessageItem(message,true,false,date);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Messages").child(firebaseUser.getUid()).child(uid);
        reference.push().setValue(messageItem);
        messageItem = new MessageItem(message,false,false,date);
        reference = database.getReference("Messages").child(uid).child(firebaseUser.getUid());
        reference.push().setValue(messageItem);
        amountMessase = 20;
    }


    public ArrayList<String> ImagePicker(){
        ArrayList<String> listImage = new ArrayList<>();

        Uri uri;
        Cursor cursor;
        int columnIndex;
        String imagePath;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        cursor = binding.getRoot().getContext().getContentResolver().query(uri,projection,null,null,null);
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while(cursor.moveToNext()){
            imagePath = cursor.getString(columnIndex);
            listImage.add(imagePath);
            Log.d("URI_IMAGE",imagePath);
        }
        return listImage;
    }
}