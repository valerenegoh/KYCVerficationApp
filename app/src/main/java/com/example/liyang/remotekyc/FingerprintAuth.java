package com.example.liyang.remotekyc;

import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
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

public class FingerprintAuth extends AppCompatActivity{

    private static final String KEY_NAME = "EDMTDev";
    private Cipher cipher;
    private KeyStore keystore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerfingerprint);

        //check if device SDK is sufficient (>= 23)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            //if device does not have fingerprint sensor
            assert fingerprintManager != null;      //as it may have null pointer exception
            if(!fingerprintManager.isHardwareDetected()){
                Toast.makeText(this, "Device does not support fingerprint authentication", Toast.LENGTH_SHORT).show();
            }

            //if user has not granted fingerprint permission for the app
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Please enable fingerprint permissions in your device settings", Toast.LENGTH_SHORT).show();
            }

            //if user has not enrolled any fingerprints in device
            if(!fingerprintManager.hasEnrolledFingerprints()){
                Toast.makeText(this, "No fingerprints registered in device. Please register at least one in your device settings", Toast.LENGTH_SHORT).show();
            }

            //if device lockscreen is not secured
            if(!keyguardManager.isKeyguardSecure()){
                Toast.makeText(this, "Please enable lockscreen security in your device settings", Toast.LENGTH_SHORT).show();
            } else{
                try {
                    generateKey();
                } catch (FingerprintException e) {
                    e.printStackTrace();
                }
            }

            //if cipher is initialized successfully
            if(initCipher()){
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
                FingerprintHandler helper = new FingerprintHandler(this);
                helper.startAuth(fingerprintManager, cryptoObject);
            }
        }
    }

    //generate encryption key
    private void generateKey() throws FingerprintException {
        try {
            keystore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keystore.load(null);
            //user has to confirm identity with fingerprint each time they want to use it
            keyGenerator.init(new KeyGenParameterSpec
                    .Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (KeyStoreException |
                NoSuchAlgorithmException |
                NoSuchProviderException |
                CertificateException |
                IOException |
                InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
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
            //return true if cipher initializes successfully
            keystore.load(null);
            SecretKey key = (SecretKey) keystore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
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

    private class FingerprintException extends Exception{
        public FingerprintException(Exception e){
            super(e);
        }
    }
}