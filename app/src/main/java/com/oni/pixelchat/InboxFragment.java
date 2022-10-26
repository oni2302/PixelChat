package com.oni.pixelchat;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oni.pixelchat.databinding.FragmentInboxBinding;

import java.util.ArrayList;

public class InboxFragment extends Fragment {
    FragmentInboxBinding binding;
    RecyclerView message_box;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<MessageItem> arrayList;
    MessageInboxAdapter adapter;
    CardView btn_send;
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
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        message_box.setLayoutManager(layoutManager);

        Bundle b = this.getArguments();
        String name = b.getString("Name");
        binding.tvInboxName.setText(name);
        arrayList = MessageItem.test();
        adapter = new MessageInboxAdapter(InboxFragment.this.getContext(),R.layout.message_item_layout,arrayList);
        message_box.setAdapter(adapter);
        // Inflate the layout for this fragment
        btn_send = binding.btnSend;
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return binding.getRoot();
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
}