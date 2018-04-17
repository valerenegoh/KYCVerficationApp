package com.example.liyang.remotekyc.FirstStep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyang.remotekyc.SecondStep.KYCSecondStep;
import com.example.liyang.remotekyc.R;

public class BarcodeAuthentication extends AppCompatActivity {
    //can only be run on actual phone and not emulator
    //this code uses the Google Barcode Scanner app to scan the barcode
    //to run this code on phone, the scanner needs to be installed
    //search for Barcode Scanner on Google Play and install it (published by the Google ZXing team)
    //link: https://play.google.com/store/apps/developer?id=ZXing%20Team

    //NRIC barcode is converted to string NRIC (nricBarcode)
    //use getter() to refer to nricBarcode from other classes
    private String nricBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_authentication);
    }

    //call this method to use the NRIC barcode in other classes
    public String getNricBarcode() {
        return nricBarcode;
    }

    public void HandleClick(View arg0) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        switch(arg0.getId()){
            case R.id.butQR: //scanning QR code
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                break;
//            case R.id.butBar: //scanning NRIC barcode
//                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
//                break;
            case R.id.butOther: //other barcode scan_formats (just in case)
                intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR");
                break;
        }
        startActivityForResult(intent, 0); //Barcode Scanner to scan for us
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            TextView tvStatus = (TextView)findViewById(R.id.tvStatus); //scan_format used to scan barcode
            TextView tvResult = (TextView)findViewById(R.id.tvResult); //generated string from barcode --> NRIC
            try {
                if (resultCode == RESULT_OK) {
                    this.nricBarcode = intent.getStringExtra("SCAN_RESULT");
                    tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
                    tvResult.setText(nricBarcode);

                    Intent i = new Intent(BarcodeAuthentication.this, KYCSecondStep.class);
                    i.putExtra("nric", nricBarcode);
                    startActivity(i);
                } else if (resultCode == RESULT_CANCELED) {
                    tvStatus.setText("Press a button to start a scan.");
                    tvResult.setText("Scan cancelled.");
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}