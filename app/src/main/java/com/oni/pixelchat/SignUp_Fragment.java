package com.oni.pixelchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.AsyncListUtil.DataCallback;

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
import com.oni.pixelchat.databinding.FragmentSignUpBinding;

public class SignUp_Fragment extends Fragment {

    // Khoi tao firebase Auth
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FragmentSignUpBinding binding;
    Button btn_signUp;
    EditText edt_SignUp_firstName
            ,edt_SignUp_lastName
            ,edt_SignUp_email
            ,edt_SignUp_password,
            edt_SignUp_reEnter_Pass;
    TextView tv_exception;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public SignUp_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Khởi tạo Binding
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        // Lấy các view
        btn_signUp = binding.btnSignUp;
        edt_SignUp_firstName = binding.edtSignUpFirstName;
        edt_SignUp_lastName = binding.edtSignUpLastName;
        edt_SignUp_email = binding.edtSignUpEmail;
        edt_SignUp_password= binding.edtSignUpPassword;
        edt_SignUp_reEnter_Pass = binding.edtSignUpRePassword;
        tv_exception = binding.tvException;

        // Cài đặt nút Đăng kí
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_SignUp_email.getText().toString();
                String password = edt_SignUp_password.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();
                if(emailCheck()&&passwordCheck()){
                    tv_exception.setText("signing up...");
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                tv_exception.setText("Sign Up Completed");
                                final Handler handler = new Handler();
                                handler.postDelayed(()->tv_exception.setText("Direct to sign in..."),500);
                                Fragment fragment = new SignIn_Fragment();
                                handler.postDelayed(()->DisplayFragment(fragment),300);
                            }
                            else{
                                tv_exception.setText(""+task.getException().toString().substring(task.getException().toString().lastIndexOf(": ")+1));
                            }
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }

    public boolean emailCheck(){
        String email = edt_SignUp_email.getText().toString();
        if(!email.matches(emailPattern)) {
            tv_exception.setText("Incorrect email form!");
            return false;
        }
        tv_exception.setText("");
        return true;
    }
    public boolean passwordCheck(){
        String password = edt_SignUp_password.getText().toString();
        String confirmPassword = edt_SignUp_reEnter_Pass.getText().toString();
        if(password.length()<8 || password.length()>16){
            tv_exception.setText("Password must be 8 - 16 characters!");
            return false;
        }
        if(!password.equals(confirmPassword)){
            tv_exception.setText("Two password do not match!");
            return false;
        }
        tv_exception.setText("");
        return true;
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

}