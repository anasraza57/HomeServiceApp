package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AvailableHelpersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AvailableHelpersRecyclerAdapter recyclerAdapter;
    String TAG="MyActivity";
    FirebaseFirestore db;
    ArrayList<JobRequestsData> jobRequestsData=new ArrayList<>();
    Context context;
    String title;

    String reqID;
    String userName;
    String userPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_helpers);
        context=this;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=getIntent();
       title= intent.getStringExtra("title");
       reqID=intent.getStringExtra("id");
       userName=intent.getStringExtra("userName");
       userPhone=intent.getStringExtra("userPhone");

        db=FirebaseFirestore.getInstance();
        db.collection("JobRequests").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot request :queryDocumentSnapshots.getDocuments()) {

                    if(request.getString("jobTitle").equals(title))
                    {
                        JobRequestsData data = new JobRequestsData(request.getString("id"), request.getString("helperName"), request.getString("helperPhone"), request.getString("jobTitle"));
                        ArrayList<String> times = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            if (!request.contains("StartTime" + i)) {
                                times.add(request.getString("startTime"+ i));
                                times.add(request.getString("endTime"+ i));

                            } else break;
                        }
                        data.setTimes(times);
                        jobRequestsData.add(data);
                    }
                }
                recyclerAdapter = new AvailableHelpersRecyclerAdapter(context , jobRequestsData,reqID,userName,userPhone);
                recyclerView.setAdapter(recyclerAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"ALL JOB REQUESTS FAILURE LISTENER"+e.getMessage());
            }
        });
    }
}
