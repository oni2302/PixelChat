package com.oni.pixelchat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oni.pixelchat.databinding.FragmentWelcomeBinding;

public class Welcome_Fragment extends Fragment {
    FragmentWelcomeBinding binding;
    Fragment signIn_Fragment
            ,home_Fragment;
    public Welcome_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        signIn_Fragment = new SignIn_Fragment();
        home_Fragment = new Home_Fragment();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //delay logo 800ms
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(user==null){
                DisplayFragment(signIn_Fragment);
            }else{
                DisplayFragment(home_Fragment);

            }
        },500);
        return binding.getRoot();
    }
    private void DisplayFragment(Fragment fragment){
        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("SignInUp")
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