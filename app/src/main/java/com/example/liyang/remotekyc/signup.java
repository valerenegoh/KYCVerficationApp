package com.example.liyang.remotekyc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by Li Yang on 3/2/2018.
 */

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button signup;
    private EditText email,password,name;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_signup);
        mAuth = FirebaseAuth.getInstance();
        signup = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.edemail);
        password = (EditText) findViewById(R.id.edpassword);
        name = (EditText) findViewById(R.id.name);
        login = (Button) findViewById(R.id.login);
        login.setVisibility(View.INVISIBLE);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = email.getText().toString().trim();
                String getpassword = password.getText().toString().trim();
                callsignup(getemail,getpassword);
                if(login.getVisibility() == View.INVISIBLE){
                    login.setVisibility(View.VISIBLE);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(signup.this, com.example.liyang.remotekyc.MainActivity.class);
                startActivity(login);
            }
        });

    }

    //Create Account
    public void callsignup(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Testing","Sign Up Successful:" + task.isSuccessful());
                        if(!task.isSuccessful()){
                            Toast.makeText(signup.this, "Failed",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            userprofile();
                            Toast.makeText(signup.this, "Created Account",Toast.LENGTH_SHORT).show();
                            Log.d("Testing", "Created Account");

                        }
                    }
                });

    }

    public void userprofile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim())
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("Testing","User Profile Updated");
                    }
                }
            });

        }
    }
}
