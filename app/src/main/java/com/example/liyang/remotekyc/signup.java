package com.example.liyang.remotekyc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Li Yang on 3/2/2018.
 */

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button signup;
    private EditText email,password,name,nric,eddob;
    private FirebaseDatabase mref;
    private DatabaseReference rootRef;
    private boolean isAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_signup);
        mAuth = FirebaseAuth.getInstance();
        signup = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.edemail);
        password = (EditText) findViewById(R.id.edpassword);
        name = (EditText) findViewById(R.id.name);
        nric = (EditText) findViewById(R.id.nric);
        eddob = (EditText) findViewById(R.id.eddob);
        mref = FirebaseDatabase.getInstance();
        rootRef = mref.getReference();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getemail = email.getText().toString().trim();
                final String getpassword = password.getText().toString().trim();
                final String getname = name.getText().toString().trim();
                final String getnric = nric.getText().toString().trim();
                final String getdob = eddob.getText().toString().trim();
                checkIfdataExists("Users");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 1 seconds
                        //Toast.makeText(signup.this, "Testing: "+isAvailable, Toast.LENGTH_SHORT).show();
                        if(isAvailable&&passregex(getpassword)&&dobregex(getdob)){
                            callsignup(getemail, getpassword);
                            signupinfo signupinfo = new signupinfo(getname, getemail, getpassword, getnric, getdob);
                            rootRef.child("Users").push().setValue(signupinfo);
                            Toast.makeText(signup.this, "Creating Account..", Toast.LENGTH_SHORT).show();
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                public void run() {
                                    mAuth.signOut();
                                    Intent login = new Intent(signup.this, com.example.liyang.remotekyc.MainActivity.class);
                                    startActivity(login);
                                }
                            },4000);
                        }
                        else{
                            //if password requirement fails as well as nric is found in database
                            if(!passregex(getpassword)&&!isAvailable){
                                Toast.makeText(signup.this, "Password must contains at least a capital letter, special character and digit", Toast.LENGTH_SHORT).show();
                                Log.d("Tag", "NRIC Exists");
                                Toast.makeText(signup.this, "NRIC already exists.", Toast.LENGTH_SHORT).show();
                            }
                            //if date of birth requirement fails as well as nric is found in database
                            else if (!dobregex(getdob)&&!isAvailable){
                                Toast.makeText(signup.this, "Invalid Date of Birth", Toast.LENGTH_SHORT).show();
                                Log.d("Tag", "NRIC Exists");
                                Toast.makeText(signup.this, "NRIC already exists.", Toast.LENGTH_SHORT).show();
                            }
                            //if only password requirement fails
                            if(!passregex(getpassword)){
                                Toast.makeText(signup.this, "Password must contains at least a capital letter, special character and digit", Toast.LENGTH_SHORT).show();
                            }
                            //if only date of birth requirement fails
                            else if(!dobregex(getdob)) {
                                Toast.makeText(signup.this, "Invalid Date of Birth", Toast.LENGTH_SHORT).show();
                            }
                            //if nric is found in database
                            else{
                                Log.d("Tag", "NRIC Exists");
                                Toast.makeText(signup.this, "NRIC already exists.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        checkIfdataExists("Users");
                    }
                }, 1000);

            }
        });
    }

    //Regex to check for password contains at least
    // a capital letter, special character and digit
    private boolean passregex(String pass){
        Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
        Matcher passcheck = p.matcher(pass);
        return passcheck.matches();
    }

    //Regex to check for correct date of birth format in dd/mm/yyyy
    private boolean dobregex(String dob){
        Pattern p = Pattern.compile("^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((1[6-9]|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((1[6-9]|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((1[6-9]|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$");
        Matcher dobcheck = p.matcher(dob);
        return dobcheck.matches();
    }

    //Check if nric already exists in database
    private boolean checkIfdataExists(final String user){
        DatabaseReference ref = rootRef.child(user);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isAvailable=true;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String getnric = data.getValue(signupinfo.class).getNric();
                    if (getnric.equals(nric.getText().toString())) {
                        isAvailable = false;
                        break;
                    }
                    //Toast.makeText(signup.this, "New NRIC", Toast.LENGTH_SHORT).show();
                    isAvailable = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        return isAvailable;
    }


    //Create Account
    public void callsignup(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        checkIfdataExists("Users");
                        Log.d("Testing", "Sign Up Successful:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(signup.this, "Failed", Toast.LENGTH_SHORT).show();
                            if(!isAvailable){
                                task.isSuccessful();
                            }
                        }
                        else {
                            userprofile();
                            Toast.makeText(signup.this, "Created Account", Toast.LENGTH_SHORT).show();
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
