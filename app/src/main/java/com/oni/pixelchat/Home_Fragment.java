package com.oni.pixelchat;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oni.pixelchat.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class Home_Fragment extends Fragment {
    private FragmentHomeBinding binding;
    private CardView avatar_warping;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<InboxItem> inboxItemList;
    private InboxItemAdapter inboxItemAdapter;

    public Home_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        recyclerView = binding.inboxMessageRecycler;
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        inboxItemList = InboxItem.test();
        inboxItemAdapter = new InboxItemAdapter(Home_Fragment.this.getContext(),R.layout.inbox_message_recycler_item,inboxItemList);
        recyclerView.setAdapter(inboxItemAdapter);
        return binding.getRoot();
    }

}