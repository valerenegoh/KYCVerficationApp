package com.example.liyang.remotekyc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class SmsOtpVerfication extends AppCompatActivity {
    private static final String TAG = "PhoneLogin";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    TextView textphone,textverify,otp,phoneo;
    EditText phoneverify,otpverify;
    Button enterotp,sendotp;
    String phone, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);
        textphone = (TextView)findViewById(R.id.number);
        textverify = (TextView)findViewById(R.id.verify);
        phoneverify = (EditText) findViewById(R.id.phoneverify);
        phoneo = (TextView) findViewById(R.id.phone);
        otpverify = (EditText) findViewById(R.id.otpverify);
        enterotp = (Button) findViewById(R.id.enterotp);
        sendotp = (Button) findViewById(R.id.sendotp);
        otp = (TextView) findViewById(R.id.otp);
        mAuth = FirebaseAuth.getInstance();

        //get phone number
        Intent i = getIntent();
        phone = i.getStringExtra("phone");
        name = i.getStringExtra("name");
//        Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                Toast.makeText(SmsOtpVerfication.this,R.string.phone_success,Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(SmsOtpVerfication.this,R.string.phone_fail,Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(SmsOtpVerfication.this,R.string.phone_invalid,Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(SmsOtpVerfication.this,"Verification code has been send on your number",Toast.LENGTH_SHORT).show();
                // Save SomeHomePage ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                phoneverify.setVisibility(View.GONE);
                sendotp.setVisibility(View.GONE);
                //textphone.setText("Please enter OTP to Verify");
                textphone.setVisibility(View.GONE);
                otp.setVisibility(View.GONE);
                phoneo.setVisibility(View.GONE);
                textverify.setVisibility(View.VISIBLE);
                otpverify.setVisibility(View.VISIBLE);
                enterotp.setVisibility(View.VISIBLE);
            }
        };

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    if (phone.equals(phoneverify.getText().toString().trim())) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phoneverify.getText().toString(),
                                60,
                                java.util.concurrent.TimeUnit.SECONDS,
                                SmsOtpVerfication.this,
                                mCallbacks);
                    sendotp.setVisibility(View.GONE);
                    otp.setVisibility(View.GONE);
                    textverify.setVisibility(View.VISIBLE);
                    otpverify.setVisibility(View.VISIBLE);
                    enterotp.setVisibility(View.VISIBLE);
//                    } else {
//                        Toast.makeText(SmsOtpVerfication.this, "This is not your phone number!", Toast.LENGTH_SHORT).show();
//                    }
                } catch(Exception e){
                    Toast.makeText(SmsOtpVerfication.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        enterotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpverify.getText().toString());
                // [END verify_with_code]
                signInWithPhoneAuthCredential(credential);
            }
        });
}

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "signInWithCredential:success");
                            Intent i = new Intent(SmsOtpVerfication.this,SomeHomePage.class);
                            i.putExtra("name", name);
                            startActivity(i);
                            Toast.makeText(SmsOtpVerfication.this,"Verification Done",Toast.LENGTH_SHORT).show();
                            // ...
                        } else {
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The SomeHomePage code entered was invalid
                                Toast.makeText(SmsOtpVerfication.this,"Invalid Verification",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}