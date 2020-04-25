package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HelperActivity extends AppCompatActivity {

    Button logout;
    ArrayList<ServicesRowData> serviceData;
    RecyclerView recyclerView;
    HelperRecyclerAdapter recyclerAdapter;
    String TAG="MyActivity";
    FirebaseFirestore db;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        serviceData=new ArrayList<>();


        if(!SessionManager.isUserLoggedIn())
        {
            Intent i =new Intent(getApplicationContext(),UserLoginActivity.class);
            startActivity(i);
        }

        db=FirebaseFirestore.getInstance();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        logout=findViewById(R.id.logout);
        addData();

        recyclerAdapter = new HelperRecyclerAdapter(this,serviceData);
        recyclerView.setAdapter(recyclerAdapter);



    }

    private void addData() {

        serviceData.add(new ServicesRowData("Cleaning"));
        serviceData.add(new ServicesRowData("Laundry"));
        serviceData.add(new ServicesRowData("Utensil Washing"));
        serviceData.add(new ServicesRowData("BabySitters"));
        serviceData.add(new ServicesRowData("CareTakers"));
        serviceData.add(new ServicesRowData("Electrician"));
        serviceData.add(new ServicesRowData("Plumber"));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.logoutUser();
                Intent i =new Intent(getApplicationContext(),UserLoginActivity.class);
                startActivity(i);
            }
        });
    }
}
