package com.example.liyang.remotekyc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        final TextView registerlink = (TextView) findViewById(R.id.register);
        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this, com.example.liyang.remotekyc.signup.class);
                startActivity(register);
            }
        });

        //Check if the User is logged in
        if(mAuth.getCurrentUser() != null){
            //User is not logged in
            finish();
            startActivity(new Intent(getApplicationContext(),verification.class));
        }
        try {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String getemail = email.getText().toString().trim();
                    String getpassword = password.getText().toString().trim();
                    callsignin(getemail, getpassword);
                }
            });
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void callsignin(String email, String password){
        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Testing", "Sign In Successful:" + task.isSuccessful());

                            //If Sign In failed, displays a message to the user
                            if (!task.isSuccessful()) {
                                Log.v("Testing", "SignInwithEmail:Failed", task.getException());
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent verification = new Intent(MainActivity.this, com.example.liyang.remotekyc.verification.class);
                                finish();
                                startActivity(verification);
                            }
                        }
                    });
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
