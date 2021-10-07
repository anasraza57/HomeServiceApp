package com.example.myapplication;

import android.content.Intent;
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
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    String helperGender;
    String helperCNIC;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

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

       getHelperDetails();

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

    void getHelperDetails()
    {


        db.collection("Helpers").document(helperPhone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                helperName =documentSnapshot.getString("name");
                helperGender =documentSnapshot.getString("gender");
                helperCNIC =documentSnapshot.getString("cnic");
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

                        autoAssignUserIfAvailable(rowData.getTitle(),rowData);

                    }

                }
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });
    }

    private void autoAssignUserIfAvailable(final String title, final JobRowData rowData) {
        db.collection("HelperRequests").whereEqualTo("title",title).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().isEmpty())
                {
                    addJobRequest(rowData);
                }
                else
                {
                    final String userPhone= queryDocumentSnapshots.getDocuments().get(0).getString("userPhone");
                    final String reqId= queryDocumentSnapshots.getDocuments().get(0).getString("id");
                    final String userName= queryDocumentSnapshots.getDocuments().get(0).getString("userName");
                    db.collection("Users").document(userPhone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            HashMap<String,Object> userNotification=new HashMap<>();
                            HashMap<String,Object> helperNotification=new HashMap<>();


                            helperNotification.put("reqId",reqId);
                            helperNotification.put("userName",userName);
                            helperNotification.put("userPhone",userPhone);
                            helperNotification.put("title",title);
                            helperNotification.put("helperPhone",helperPhone);



                            userNotification.put("reqId",reqId);
                            userNotification.put("helperPhone",helperPhone);
                            userNotification.put("helperName",helperName);
                            // userNotification.put("helperCNIC",jobRequestsData.get(position).helperCNIC);
                            userNotification.put("title",title);
                            userNotification.put("userPhone",userPhone);
                            userNotification.put("helperGender",helperGender);
                            userNotification.put("helperCNIC",helperCNIC);


                            //  String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

                            Calendar c = Calendar.getInstance();
                            Date date=new Date();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = df.format(c.getTime());
                            try {
                                date=df.parse(formattedDate);
                            } catch (ParseException e) {
                                Log.w(TAG,e.getMessage());
                            }
                            userNotification.put("notificationDate",date);
                            helperNotification.put("notificationDate",date);


                            db.collection("HelperNotifications").add(helperNotification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Map<String, Object> map=new HashMap<>();
                                    String id=documentReference.getId();
                                    map.put("id",id);
                                    documentReference.set(map, SetOptions.merge());
                                    Log.d(TAG,"push Notification Success");
                                    Toast.makeText(getApplicationContext(),"Request Assigned Successfully, User and helper both will be notified",Toast.LENGTH_LONG).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"push Notification Failure");
                                }
                            });

                            db.collection("UserNotifications").add(userNotification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Map<String, Object> map=new HashMap<>();
                                    String id=documentReference.getId();
                                    map.put("id",id);
                                    documentReference.set(map, SetOptions.merge());
                                    Log.d(TAG,"push Notification Success");
                                    Toast.makeText(getApplicationContext(),"Request Assigned Successfully, User and helper both will be notified",Toast.LENGTH_LONG).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"push Notification Failure");
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    deleteHelperRequest(reqId);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void deleteHelperRequest(String reqId) {
        db.collection("HelperRequests").document(reqId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error deleting document", e);

            }
        });
    }

    private void addJobRequest(JobRowData rowData)
    {
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
                Map<String, Object> map=new HashMap<>();
                String id=documentReference.getId();
                map.put("id",id);
                documentReference.set(map, SetOptions.merge());

                Toast.makeText(getApplicationContext(),"Your Job request is submitted You will be Notified",Toast.LENGTH_LONG).show();
                autoAssignUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG,"Add helperRequest to firestore Failure Listener");
            }
        });


    }
    private void autoAssignUser() {

    }


}
