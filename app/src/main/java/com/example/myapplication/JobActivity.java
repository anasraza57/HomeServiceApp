package com.example.myapplication;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    JobRecyclerAdapter recyclerAdapter;
    Button logout;
    ArrayList<JobRowData> jobData;
    Button startServices;
    String TAG="MyActivity";
    FirebaseFirestore db;
    String helperName;
    String helperPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        jobData=new ArrayList<>();


        if(!SessionManager.isHelperLoggedIn())
        {
            Intent i =new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }

        db=FirebaseFirestore.getInstance();
        startServices=findViewById(R.id.startServices);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        logout=findViewById(R.id.logout);
        addData();
        recyclerAdapter = new JobRecyclerAdapter(this,jobData);
        recyclerView.setAdapter(recyclerAdapter);
        helperPhone=SessionManager.getHelperPhone();

       getHelperName();

    }

    private void addData() {

        jobData.add(new JobRowData("Cleaning"));
        jobData.add(new JobRowData("Laundry"));
        jobData.add(new JobRowData("Utensil Washing"));
        jobData.add(new JobRowData("BabySitters"));
        jobData.add(new JobRowData("CareTakers"));
        jobData.add(new JobRowData("Electrician"));
        jobData.add(new JobRowData("Plumber"));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void getHelperName()
    {


        db.collection("Helpers").document(helperPhone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                helperName =documentSnapshot.getString("name");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"getHelperName on Failure Listener");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.logoutHelper();
                Intent i =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        startServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (JobRowData rowData: jobData) {

                    if(rowData.isSelected()) {
                        Map<String, Object> helperRequests = new HashMap<>();
                        helperRequests.put("helperName", helperName);
                        helperRequests.put("helperPhone", helperPhone);
                        helperRequests.put("jobTitle", rowData.getTitle());
                        int j = 1;

                        ArrayList<Time> times = rowData.getTime();
                        for (int i = 0; i < times.size(); i++) {

                            helperRequests.put("startTime" + j, times.get(i).hours + ":" + times.get(i).minutes);
                            i++;
                            helperRequests.put("endTime" + j, times.get(i).hours + ":" + times.get(i).minutes);
                            j++;

                        }


                        db.collection("JobRequests").add(helperRequests).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Your Job request is submitted You will be Notified",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(TAG,"Add helperRequest to firestore Failure Listener");
                            }
                        });
                    }

                }

            }
        });
    }


}
