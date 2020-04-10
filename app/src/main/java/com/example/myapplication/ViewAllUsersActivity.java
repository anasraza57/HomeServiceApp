package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDexApplication;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewAllUsersActivity extends AppCompatActivity implements View.OnClickListener {

    Button usersButton, HelpersButton, viewRequestButton;
    RecyclerView recyclerView;
    ViewAllUserRecyclerAdapter recyclerAdapter;
    FirebaseFirestore db;
    String TAG="MyActivity";
    ArrayList<UsersData> usersData=new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_users);

        usersButton = findViewById(R.id.users);
        HelpersButton = findViewById(R.id.helpers);
        viewRequestButton = findViewById(R.id.view_requests);

        usersButton.setOnClickListener(this);
        HelpersButton.setOnClickListener(this);

        context=this;


        viewRequestButton.setOnClickListener(this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot user :queryDocumentSnapshots.getDocuments()) {
                    usersData.add(new UsersData(user.getString("phone"),user.getString("password"),user.getString("name"),user.getString("address")));
                }
                recyclerAdapter = new ViewAllUserRecyclerAdapter(context,usersData);
                recyclerView.setAdapter(recyclerAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"GET ALL USERS FAILURE LISTENER"+e.getMessage());
            }
        });


    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.users:
                intent = new Intent(this, ViewAllUsersActivity.class);
                startActivity(intent);
                break;
            case R.id.helpers:
                intent = new Intent(this, ViewAllHelpersActivity.class);
                startActivity(intent);
                break;
            case R.id.view_requests:
                intent = new Intent(this, ViewAllRequestsActivity.class);
                startActivity(intent);
                break;


        }
    }
}
