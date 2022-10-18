package com.oni.pixelchat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.oni.pixelchat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private TextView move_to_signUp;
    private View view;
    Fragment welcome_Fragment
            ,signIn_Fragment
            ,signUp_Fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        welcome_Fragment = new Welcome_Fragment();
        signIn_Fragment = new SignIn_Fragment();
        signUp_Fragment = new SignUp_Fragment();
        DisplayFragment(welcome_Fragment);
        //delay logo 800ms
        final Handler handler = new Handler();
        handler.postDelayed(() -> DisplayFragment(signIn_Fragment),800);

    }
    private void DisplayFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
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