package com.example.liyang.remotekyc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SomeHomePage extends AppCompatActivity {
    Button signout;
    private FirebaseAuth mAuth;
    TextView name;
    private String getname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_success);
        mAuth = FirebaseAuth.getInstance();
        name = (TextView)findViewById(R.id.name);
        signout = (Button)findViewById(R.id.signout);

        //Get the Name of the Current User
/*        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            name.setText("Welcome, " + user.getDisplayName());
        }*/

        Intent i = getIntent();
        getname = i.getExtras().getString("name");
        //Toast.makeText(SomeHomePage.this,getname, Toast.LENGTH_LONG).show();
        name.setText("Welcome, " + "Valerene");

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}