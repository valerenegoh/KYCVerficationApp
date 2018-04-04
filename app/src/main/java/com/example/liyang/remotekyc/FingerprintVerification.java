package com.example.liyang.remotekyc;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerprintVerification extends AppCompatActivity{

    private static final String KEY_NAME = "EDMTDev";
    private Cipher cipher;
    private KeyStore keystore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private String name;

    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fingerprint_verfication);

            Intent i = getIntent();
            name = i.getStringExtra("name");

            //check if device SDK is sufficient (>= 23)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

                //if device does not have fingerprint sensor
                assert fingerprintManager != null;      //as it may have null pointer exception
                if (!fingerprintManager.isHardwareDetected()) {
                    Toast.makeText(this, "Device does not support fingerprint authentication", Toast.LENGTH_SHORT).show();
                }

                //if user has not granted fingerprint permission for the app
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Please enable fingerprint permissions in your device settings", Toast.LENGTH_SHORT).show();
                }

                //if user has not enrolled any fingerprints in device
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(this, "No fingerprints registered in device. Please register at least one in your device settings", Toast.LENGTH_SHORT).show();
                }

                //if device lockscreen is not secured
                if (!keyguardManager.isKeyguardSecure()) {
                    Toast.makeText(this, "Please enable lockscreen security in your device settings", Toast.LENGTH_SHORT).show();
                } else {
                    generateKey();
                    //if cipher is initialized successfully
                    if (initCipher()) {
                        cryptoObject = new FingerprintManager.CryptoObject(cipher);
                        FingerprintHandler helper = new FingerprintHandler(this);
                        helper.startAuth(fingerprintManager, cryptoObject);
                    }
                }
            }
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //generate encryption key
    private void generateKey(){
        try {
            keystore = KeyStore.getInstance("AndroidKeyStore");
        } catch(KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            keystore.load(null);
            //user has to confirm identity with fingerprint each time they want to use it
            keyGenerator.init(new KeyGenParameterSpec
                    .Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (CertificateException |
                IOException |
                InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean initCipher() {
        try {
            //configure cipher to fingerprint-auth requirements
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
            + KeyProperties.BLOCK_MODE_CBC + "/"
            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create cipher", e);
        }

        try {
            keystore.load(null);
            SecretKey key = (SecretKey) keystore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //return true if cipher initializes successfully
            return true;
        } catch (KeyPermanentlyInvalidatedException e){
            //return false if cipher initialization fails
            return false;
        } catch (IOException |
                CertificateException |
                NoSuchAlgorithmException |
                UnrecoverableKeyException |
                KeyStoreException |
                InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize cipher");
        }
    }

    class FingerprintHandler extends FingerprintManager.AuthenticationCallback{
        private Context context;
        private CancellationSignal cancellationSignal;

        public FingerprintHandler(Context context) {
            this.context = context;
        }

        public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
            //cancels the app's access to touch sensor & other user input if app is not in use.
            cancellationSignal = new CancellationSignal();
            if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
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
            Intent i = new Intent(context, SomeHomePage.class);
            i.putExtra("name", name);
            startActivity(i);
        }

        //fingerprint doesn't match any registered on device
        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(context, "Authentication Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}