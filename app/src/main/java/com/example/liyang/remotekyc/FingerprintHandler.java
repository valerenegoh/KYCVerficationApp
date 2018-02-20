package com.example.liyang.remotekyc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

//This class processes the various callback events during authentication
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{
    private  Context context;
    private CancellationSignal cancellationSignal;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        //cancels the app's access to touch sensor & other user input if app is not in use.
        cancellationSignal = new CancellationSignal();
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    //for fatal errors
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(context, "Authentication error: " + errString, Toast.LENGTH_LONG).show();
    }

    //for non-fatal errors
    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Toast.makeText(context, "Authentication help: " + helpString, Toast.LENGTH_LONG).show();
    }

    //fingerprint matches one registered on device
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
    }

    //fingerprint doesn't match any registered on device
    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication Failed!", Toast.LENGTH_SHORT).show();
    }
}
