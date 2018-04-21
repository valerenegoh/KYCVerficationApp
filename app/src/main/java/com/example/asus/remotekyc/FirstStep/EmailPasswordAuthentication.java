package com.example.asus.remotekyc.FirstStep;

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

import com.example.asus.remotekyc.SecondStep.KYCSecondStep;
import com.example.asus.remotekyc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailPasswordAuthentication extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button login;
    private TextView forgetpass;
    String getemail, getpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.emailpw_authentication);

            mAuth = FirebaseAuth.getInstance();
            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
            login = (Button) findViewById(R.id.login);
            forgetpass = (TextView) findViewById(R.id.forgetpass);

            //Go to resetpassword activity
            forgetpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent resetpassword = new Intent(EmailPasswordAuthentication.this, com.example.asus.remotekyc.resetpassword.class);
                    startActivity(resetpassword);
                }
            });

            //Check if the User is logged in
/*            if (mAuth.getCurrentUser() != null) {
                //User is logged in
                finish();
                startActivity(new Intent(getApplicationContext(), KYCSecondStep.class));
            }*/

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getemail = email.getText().toString().trim();
                    getpassword = password.getText().toString().trim();
                    callsignin(getemail, getpassword);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(EmailPasswordAuthentication.this, R.string.login_invalid, Toast.LENGTH_SHORT).show();
                        } else {
                            Intent verification = new Intent(EmailPasswordAuthentication.this, KYCSecondStep.class);
                            finish();
                            verification.putExtra("email", getemail);
                            startActivity(verification);
                        }
                    }
                });
        } catch (Exception e){
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(this,R.string.login_empty, Toast.LENGTH_LONG).show();
            }
            else if (email.isEmpty()) {
                Toast.makeText(this, R.string.login_emptyemail, Toast.LENGTH_LONG).show();
            }
            else if (password.isEmpty()) {
                Toast.makeText(this, R.string.login_emptypass, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,R.string.login_invalid, Toast.LENGTH_LONG).show();
                Log.i("invalid signin", e.getMessage());
            }
        }
    }
}