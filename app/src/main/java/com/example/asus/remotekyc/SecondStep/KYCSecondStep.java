package com.example.asus.remotekyc.SecondStep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.remotekyc.FacialRecognition.FacialRecognitionVerification;
import com.example.asus.remotekyc.R;
import com.example.asus.remotekyc.SomeHomePage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KYCSecondStep  extends AppCompatActivity {
    private Button fingerprint, sms, face;
    private String email, nric, phone, name;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String scanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.kyc_secondverification);

            fingerprint = (Button) findViewById(R.id.fingerprint);
            sms = (Button) findViewById(R.id.sms);
            face = (Button) findViewById(R.id.facial);
            database = FirebaseDatabase.getInstance();
            ref = database.getReference();

            //get email and/or nric from previous authentication
            Intent i = getIntent();
            email = i.getStringExtra("email");
            nric = i.getStringExtra("nric");

            //get user's phone number for sms otp verification (second step)
            ref.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //it's not toasting this
//                    Toast.makeText(KYCSecondStep.this, "I'm here", Toast.LENGTH_SHORT).show();
                    for(DataSnapshot data: dataSnapshot.getChildren()) {
                        String getEmail = (String) data.child("email").getValue();
                        String getnric = (String) data.child("nric").getValue();
//                        Toast.makeText(KYCSecondStep.this, getEmail, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(KYCSecondStep.this, getnric, Toast.LENGTH_SHORT).show();
                        if (email != null) {
                            if (getEmail.equals(email)) {
                                name = (String) data.child("name").getValue();
                                phone = (String) data.child("phone").getValue();
                            }
                        } else if (nric != null) {
                            if (getnric.equals(nric)) {
                                name = (String) data.child("name").getValue();
                                phone = (String) data.child("phone").getValue();
                            }
                        }
                        Toast.makeText(KYCSecondStep.this, name, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //navigate to fingerprint verification
            fingerprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(KYCSecondStep.this, FingerprintVerification.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }
            });

            //navigate to sms otp verification
            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(KYCSecondStep.this, SmsOtpVerfication.class);
                    i.putExtra("phone", phone);
                    i.putExtra("name", name);
                    startActivity(i);
                }
            });

            face.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(KYCSecondStep.this, FacialRecognitionVerification.class);
                    startActivity(i);
                }
            });
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

//    public void HandleClick(View arg0) {
//        String packageName = "cultoftheunicorn.marvel";
//        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
//        if(intent != null) {
//            startActivity(intent);
//        }
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            try {
                if (resultCode == RESULT_OK) {
                    this.scanned = intent.getStringExtra("SCAN_RESULT");

                    Intent i = new Intent(KYCSecondStep.this, SomeHomePage.class);
                    i.putExtra("name", scanned);
                    startActivity(i);
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}