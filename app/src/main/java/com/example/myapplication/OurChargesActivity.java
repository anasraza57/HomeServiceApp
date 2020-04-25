package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OurChargesActivity extends AppCompatActivity implements View.OnClickListener {

    Button getServiceButton1, getServiceButton2, getServiceButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_charges);

        getServiceButton1 = findViewById(R.id.getService);
        getServiceButton2 = findViewById(R.id.getService2);
        getServiceButton3 = findViewById(R.id.getService3);

        getServiceButton1.setOnClickListener(this);
        getServiceButton2.setOnClickListener(this);
        getServiceButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, HelperActivity.class);
        startActivity(intent);
    }
}
