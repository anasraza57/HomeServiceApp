package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewAllRequestsActivity extends AppCompatActivity implements View.OnClickListener{

    Button usersButton, HelpersButton, viewRequestButton;
    RecyclerView recyclerView;
    ViewAllRequestsRecyclerAdapter recyclerAdapter;
    FirebaseFirestore db;
    String TAG="MyActivity";
    Context context;
    ArrayList<ServiceRequestsData> serviceRequestsData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_requests);

        usersButton = findViewById(R.id.users);
        HelpersButton = findViewById(R.id.helpers);
        viewRequestButton = findViewById(R.id.view_requests);

        usersButton.setOnClickListener(this);
        HelpersButton.setOnClickListener(this);
        viewRequestButton.setOnClickListener(this);
        context=this;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        db.collection("HelperRequests").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot request :queryDocumentSnapshots.getDocuments()) {
                    serviceRequestsData.add(new ServiceRequestsData(request.getString("endTime"),request.getString("startTime"),request.getString("title"),request.getString("userName"),request.getString("userPhone"),request.getString("id")));
                }
                recyclerAdapter = new ViewAllRequestsRecyclerAdapter(context,serviceRequestsData);
                recyclerView.setAdapter(recyclerAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"ALL REQUESTS FAILURE LISTENER"+e.getMessage());
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
