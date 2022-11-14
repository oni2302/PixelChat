package com.oni.pixelchat;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oni.pixelchat.databinding.FragmentHomeBinding;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;

public class Home_Fragment extends Fragment {
    private FragmentHomeBinding binding;
    private CardView avatar_warping;
    private RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    private ArrayList<User> userArrayList;
    private InboxItemAdapter inboxItemAdapter;
    private CardView btn_home_sign_out;
    private FirebaseUser mUser;
    private String uid;
    private TextView tv_home_displayname;
    public Home_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        uid = FirebaseAuth.getInstance().getUid();
        // Inflate the layout for this fragment
        tv_home_displayname = binding.tvHomeDisplayname;
        Date dt = new Date();
        int hour = (int)(dt.getTime()/3600000)%24+7;
        if(hour>=18 &&hour<=5){
            tv_home_displayname.setText("Chào buổi tối, "+mUser.getDisplayName());
        }else if(hour>5 &&hour<=10){
            tv_home_displayname.setText("Chào buổi sáng, "+mUser.getDisplayName());
        }else if(hour>10 && hour<=13){
            tv_home_displayname.setText("Chào buổi trưa, "+mUser.getDisplayName());
        }else{
            tv_home_displayname.setText("Chào buổi chiều, "+mUser.getDisplayName());
        }
        recyclerView = binding.inboxMessageRecycler;
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        userArrayList = new ArrayList<>();
        userArrayList.clear();
        inboxItemAdapter = new InboxItemAdapter(Home_Fragment.this.getContext(),R.layout.inbox_message_recycler_item,userArrayList);
        recyclerView.setAdapter(inboxItemAdapter);
        ReadUsers();
        btn_home_sign_out = binding.btnHomeSignOut;
        btn_home_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Fragment f = new SignIn_Fragment();
                DisplayFragment(f);
            }
        });
        return binding.getRoot();
    }
    private void DisplayFragment(Fragment fragment){
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
    private void ReadUsers(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    assert user !=null;
                    assert mUser!=null;
                    if(!user.getId().equals(mUser.getUid())){
                        userArrayList.add(user);
                    }
                }
                inboxItemAdapter = new InboxItemAdapter(Home_Fragment.this.getContext(),R.layout.inbox_message_recycler_item,userArrayList);
                recyclerView.setAdapter(inboxItemAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}