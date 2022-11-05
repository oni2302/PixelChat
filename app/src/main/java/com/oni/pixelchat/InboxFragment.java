package com.oni.pixelchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oni.pixelchat.databinding.FragmentInboxBinding;

import java.util.ArrayList;
import java.util.Date;

public class InboxFragment extends Fragment {
    FragmentInboxBinding binding;
    RecyclerView message_box;
    LinearLayoutManager layoutManager;
    ArrayList<MessageItem> arrayList;
    MessageInboxAdapter adapter;
    CardView btn_send;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String uid;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edt_inbox_message;
    int amountMessase =20;
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
        btn_send = binding.btnSend;
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SendMessage("Nhà cái đến từ châu Âu uy tín hàng đầu Vịt Lam\nBạn có muốn làm giàu chỉ với 2 bàn tay trắng\n Đến với chúng tôi!\n\n"+edt_inbox_message.getText().toString(),date.getTime());
            }
        });
        ReadMessage(amountMessase);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(amountMessase<arrayList.size()+amountMessase){
                    amountMessase+=20;
                    ReadMessage(amountMessase);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }
    public void ReadMessage(int amountMessase){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Messages").child(firebaseUser.getUid()).child(uid);

        reference.limitToLast(amountMessase).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Log.d("DATA",dataSnapshot.getValue().toString());
                    MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);
                    arrayList.add(messageItem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
    }
}