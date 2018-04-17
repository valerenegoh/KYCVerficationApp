package com.example.liyang.remotekyc.FacialRecognition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liyang.remotekyc.R;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facial_name);

        final EditText name = (EditText) findViewById(R.id.name);
        Button nextButton = (Button) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().equals("")) {
                    Intent intent = new Intent(NameActivity.this, Training.class);
                    intent.putExtra("name", name.getText().toString().trim());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(NameActivity.this, "Please enter the name", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}