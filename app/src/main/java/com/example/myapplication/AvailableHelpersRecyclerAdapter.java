package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class AvailableHelpersRecyclerAdapter extends RecyclerView.Adapter<AvailableHelpersRecyclerAdapter.ViewHolder> {
    private Context context;
    ArrayList<JobRequestsData> jobRequestsData;
    FirebaseFirestore db;
    String reqID;
    String userName;
    String userPhone;
    String TAG="MyActivity";

    public AvailableHelpersRecyclerAdapter(Context context, ArrayList<JobRequestsData> jobRequestsData, String reqID, String userName, String userPhone) {
        this.context = context;
        this.jobRequestsData = jobRequestsData;
        this.reqID = reqID;
        this.userName = userName;
        this.userPhone = userPhone;
        db=FirebaseFirestore.getInstance();
    }

    AvailableHelpersRecyclerAdapter(Context applicationContext, ArrayList<JobRequestsData> jobRequestsData) {
        this.context = applicationContext;
        this.jobRequestsData=jobRequestsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.available_helpers_row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.phone.setText(jobRequestsData.get(position).getHelperPhone());
        holder.name.setText(jobRequestsData.get(position).getHelperName());



        holder.assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                db.collection("Helpers").document(jobRequestsData.get(position).getHelperPhone()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        HashMap<String,Object> userNotification=new HashMap<>();
                        HashMap<String,Object> helperNotification=new HashMap<>();

                        String gender=documentSnapshot.getString("gender");
                        String cnic=documentSnapshot.getString("cnic");

                        helperNotification.put("reqId",jobRequestsData.get(position).getId());
                        helperNotification.put("userName",userName);
                        helperNotification.put("userPhone",userPhone);
                        helperNotification.put("title",jobRequestsData.get(position).getJobTitle());
                        helperNotification.put("helperPhone",jobRequestsData.get(position).getHelperPhone());



                        userNotification.put("reqId",jobRequestsData.get(position).getId());
                        userNotification.put("helperPhone",jobRequestsData.get(position).getHelperPhone());
                        userNotification.put("helperName",jobRequestsData.get(position).getHelperName());
                        // userNotification.put("helperCNIC",jobRequestsData.get(position).helperCNIC);
                        userNotification.put("title",jobRequestsData.get(position).getJobTitle());
                        userNotification.put("userPhone",userPhone);
                        userNotification.put("helperGender",gender);
                        userNotification.put("helperCNIC",cnic);


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
                                Toast.makeText(context,"Request Assigned Successfully, User and helper both will be notified",Toast.LENGTH_LONG).show();


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
                                Toast.makeText(context,"Request Assigned Successfully, User and helper both will be notified",Toast.LENGTH_LONG).show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"push Notification Failure");
                            }
                        });

                    }
                });
                Intent i=new Intent(context,MainActivity.class);
                context.startActivity(i);

            }
        });

        holder.viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobRequestsData.size();
    }
    private void openDialog(int pos) {
        AvailableHelpersDialogue dialog = new AvailableHelpersDialogue(pos,jobRequestsData);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "Helper Details");

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button viewDetailsButton, assignButton;
        TextView name,phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            assignButton = itemView.findViewById(R.id.assign);
            viewDetailsButton = itemView.findViewById(R.id.view_details);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);


        }

        @Override
        public void onClick(View v) {
        }


    }
}
