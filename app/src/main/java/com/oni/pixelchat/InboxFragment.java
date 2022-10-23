package com.oni.pixelchat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        Bundle b = this.getArguments();
        String username = b.getString("Username");
        String name = b.getString("Name");
        binding.tvInboxName.setText(name);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    public void DisplayFragment(Fragment fragment){
        FragmentManager fragmentManager =getFragmentManager();
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