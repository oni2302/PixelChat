package com.oni.pixelchat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oni.pixelchat.databinding.FragmentInboxBinding;

public class InboxFragment extends Fragment {
    FragmentInboxBinding binding;
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
        Bundle b = new Bundle();
        b.getBundle("itemInbox");
        String name ;///dang lam do cho nay
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}