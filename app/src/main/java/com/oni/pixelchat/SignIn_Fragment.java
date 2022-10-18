package com.oni.pixelchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oni.pixelchat.databinding.FragmentSignInBinding;

public class SignIn_Fragment extends Fragment {

    FragmentSignInBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText edt_signIn_Email
            ,edt_signIn_Password;
    TextView move_to_signUp
            ,tv_exception;
    Button btn_SignIn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public SignIn_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater,container,false);
        move_to_signUp = binding.moveToSignUp;
        Fragment signUp_Fragment = new SignUp_Fragment();
        move_to_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayFragment(signUp_Fragment);
            }
        });

        edt_signIn_Email = binding.edtSignInEmail;
        edt_signIn_Password = binding.edtSignInPassword;
        tv_exception = binding.tvExceptionSignIn;
        btn_SignIn = binding.btnSignIn;

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();
                String email = edt_signIn_Email.getText().toString();
                String password = edt_signIn_Password.getText().toString();
                email="phuongtky2003@gmail.com";
                password = "onionioni";
                if(emailCheck()&&passwordCheck()||true){
                    tv_exception.setText("Signing in...");
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                tv_exception.setText("Signing in successful");
                                final Handler handler = new Handler();
                                Fragment fragment = new Home_Fragment();
                                handler.postDelayed(()->DisplayFragment(fragment),400);
                            }
                            else {
                                tv_exception.setText(""+task.getException().toString().substring(task.getException().toString().lastIndexOf(": ")+1));
                            }
                        }
                    });
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void DisplayFragment(Fragment fragment){
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
    public boolean emailCheck(){
        String email = edt_signIn_Email.getText().toString();
        if(!email.matches(emailPattern)) {
            tv_exception.setText("Incorrect email form!");
            return false;
        }
        tv_exception.setText("");
        return true;
    }
    public boolean passwordCheck(){
        String password = edt_signIn_Password.getText().toString();
        if(password.length()<8 || password.length()>16){
            tv_exception.setText("Password must be 8 - 16 characters!");
            return false;
        }
        tv_exception.setText("");
        return true;
    }
}