package com.example.liyang.remotekyc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;


/**
 * Created by Li Yang on 4/4/2018.
 */

public class securityinfo extends AppCompatActivity {
    private TextView publickey,privatekey;
    private Query addData;
    private FirebaseDatabase mref;
    private DatabaseReference mDatabase;
    private String email,key,publick;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securityinfo);
        publickey = (TextView) findViewById(R.id.publickey);
        privatekey = (TextView) findViewById(R.id.privatekey);
        login = (Button) findViewById(R.id.login);
        mref = FirebaseDatabase.getInstance();
        mDatabase = mref.getReference();
        try{
            rsakeypair();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmailPasswordVerification.class));
            }
        });
        //Retrieve Email from signup activity
        Intent in = getIntent();
        email = in.getExtras().getString("Email");
        //Toast.makeText(this, email, Toast.LENGTH_LONG).show();
        addData =
                FirebaseDatabase.getInstance().getReference("Users").orderByChild("email").equalTo(email);
        addData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    key = data.getKey();

                }
                //Toast.makeText(securityinfo.this,key, Toast.LENGTH_LONG).show();
                mDatabase.child("Users").child(key).child("Publickey").setValue(publick);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void rsakeypair() throws Exception {
        //Setup Spongy castle as a Security Provider
        Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","SC");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
        // Initialize the key generator and generate a KeyPair
        keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encodedPublicKey = publicKey.getEncoded();
        byte[] encodedPrivateKey = privateKey.getEncoded();
        publick = Base64.encodeToString(encodedPublicKey, Base64.DEFAULT);
        String privatek = Base64.encodeToString(encodedPrivateKey, Base64.DEFAULT);
        publickey.setText("PUBLIC KEY: "+ publick);
        privatekey.setText("PRIVATE KEY: "+privatek);
        //For Test and Debugging
        Log.i("Public Key",publick);
        Log.i("Private Key",privatek);
    }
}