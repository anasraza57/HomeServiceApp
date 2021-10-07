package com.example.myapplication;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class HelperRecyclerAdapter extends RecyclerView.Adapter<HelperRecyclerAdapter.ViewHolder> {

    protected Context context;
    private ArrayList<ServicesRowData> servicesData;
    FirebaseFirestore db;
    String userName;
    String userPhone;
    String TAG="MyActivity";


    HelperRecyclerAdapter(Context applicationContext, ArrayList<ServicesRowData> serviceData) {
        this.context = applicationContext;
        this.servicesData=serviceData;
        db=FirebaseFirestore.getInstance();
        userPhone=SessionManager.getUserPhone();
        getUserName();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.helper_row_data, parent, false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.title.setText(servicesData.get(position).title);

        holder.startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.startTimeButton.setText(hourOfDay + ":"+minute);
                        holder.startTime.setHours(hourOfDay);
                        holder.startTime.setMinutes(minute);

                    }
                },holder.hour,holder.minute,android.text.format.DateFormat.is24HourFormat(context));
                holder.timePickerDialog.show();
            }
        });

        holder.endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        holder.endTimeButton.setText(hourOfDay + ":"+minute);
                        holder.endTime.setHours(hourOfDay);
                        holder.endTime.setMinutes(minute);

                    }
                },holder.hour,holder.minute,android.text.format.DateFormat.is24HourFormat(context));
                holder.timePickerDialog.show();

            }
        });
        holder.serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.selectedItem=holder.gender.getSelectedItem().toString();

                if(holder.selectedItem.isEmpty())
                {
                    Toast.makeText(context, "Please Fill the required fields",Toast.LENGTH_LONG).show();
                    return;

                }
                if((holder.startTime.getHours()!=0)&&(holder.endTime.getHours()!=0))
                {
                    autoAssignIfHelperAvailable(servicesData.get(position).getTitle(),holder,position);


                }
                else Toast.makeText(context,"Please Enter time first",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void autoAssignIfHelperAvailable(final String title, final ViewHolder holder, final int position) {
        db.collection("JobRequests").whereEqualTo("jobTitle",title).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().isEmpty())
                {
                    addUserRequestToDatabase(holder,position);
                }
                else
                {
                    final String helperPhone= queryDocumentSnapshots.getDocuments().get(0).getString("helperPhone");
                    final String reqId= queryDocumentSnapshots.getDocuments().get(0).getString("id");
                    final String helperName= queryDocumentSnapshots.getDocuments().get(0).getString("helperName");

                    db.collection("Helpers").document(helperPhone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            HashMap<String,Object> userNotification=new HashMap<>();
                            HashMap<String,Object> helperNotification=new HashMap<>();

                            String gender=documentSnapshot.getString("gender");
                            String cnic=documentSnapshot.getString("cnic");

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

                    deleteJobRequest(reqId);
                    Intent i=new Intent(context,MainActivity.class);
                    context.startActivity(i);

                }
            }
        });
    }

    private void deleteJobRequest(String reqId) {
        db.collection("JobRequests").document(reqId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void addUserRequestToDatabase(ViewHolder holder,int position)
    {
        Log.w(TAG,holder.startTimeButton.getText().toString());
        Map<String,Object> helperRequests=new HashMap<>();
        helperRequests.put("userName",userName);
        helperRequests.put("userPhone",userPhone);
        helperRequests.put("title",servicesData.get(position).title);
        helperRequests.put("startTime",holder.startTime.hours+":"+holder.startTime.minutes);
        helperRequests.put("endTime",holder.endTime.hours+":"+holder.endTime.minutes);
        helperRequests.put("helperGender",holder.selectedItem);
        db.collection("HelperRequests").add(helperRequests).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Map<String, Object> map=new HashMap<>();
                String id=documentReference.getId();
                map.put("id",id);
                documentReference.set(map, SetOptions.merge());

                Toast.makeText(context,"Your Request is submitted, You will be assigned a helper real soon",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Add helperRequests Failure");
            }
        });
        Intent i=new Intent(context,MainActivity.class);
        context.startActivity(i);
    }

    void getUserName()
    {
        db.collection("Users").document(userPhone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userName=documentSnapshot.getString("name");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"getUserName on Failure Listener");
            }
        });
    }


    @Override
    public int getItemCount() {
        return servicesData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button startTimeButton, endTimeButton, genderButton, serviceButton;
        final int hour;
        final int minute;
        Time startTime,endTime;
        TimePickerDialog timePickerDialog;
        Context context;
        String startTimeString,endTimeString;
        Spinner gender;
        String[] items=new String[]{"Male","Female"};
        String selectedItem="";

        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);

            title = itemView.findViewById(R.id.titleText);
            startTimeButton = itemView.findViewById(R.id.startTime);
            endTimeButton = itemView.findViewById(R.id.endTime);

            this.context=context;
            gender=itemView.findViewById(R.id.gender);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item,items);
            gender.setAdapter(adapter);
            startTimeString="";
            endTimeString="";
            serviceButton = itemView.findViewById(R.id.getService);
            startTime=new Time();
            endTime=new Time();



            Calendar calendar=Calendar.getInstance();


           hour=calendar.get(Calendar.HOUR_OF_DAY);
           minute=calendar.get(Calendar.MINUTE);

        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId())
//            {
//
//                case R.id.endTime:
//
//                    timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                            endTimeButton.setText(hourOfDay + ":"+minute);
//                            endTime.setHours(hourOfDay);
//                            endTime.setMinutes(minute);
//                        }
//                    },hour,minute,android.text.format.DateFormat.is24HourFormat(context));
//                    timePickerDialog.show();
//                    break;
//            }
//        }
    }
}
