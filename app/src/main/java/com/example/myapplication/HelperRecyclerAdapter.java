package com.example.myapplication;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Calendar;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.startTimeButton.getText().toString()!="-:-"&&holder.endTimeButton.getText().toString()!="-:-")
                {
                    Map<String,Object> helperRequests=new HashMap<>();
                    helperRequests.put("userName",userName);
                    helperRequests.put("userPhone",userPhone);

                    helperRequests.put("startTime",holder.startTime);
                    helperRequests.put("endTime",holder.endTime);
                    db.collection("HelperRequests").add(helperRequests).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
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
                else Toast.makeText(context,"Please Enter time first",Toast.LENGTH_LONG).show();
            }
        });

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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        Button startTimeButton, endTimeButton, genderButton, serviceButton;
        final int hour;
        final int minute;
        Time startTime,endTime;
        TimePickerDialog timePickerDialog;
        Context context;

        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);

            title = itemView.findViewById(R.id.titleText);
            startTimeButton = itemView.findViewById(R.id.startTime);
            endTimeButton = itemView.findViewById(R.id.endTime);
            genderButton = itemView.findViewById(R.id.gender);
            serviceButton = itemView.findViewById(R.id.getService);
            startTime=new Time();
            endTime=new Time();
            this.context=context;
            startTimeButton.setOnClickListener(this);
            endTimeButton.setOnClickListener(this);
            Calendar calendar=Calendar.getInstance();


           hour=calendar.get(Calendar.HOUR_OF_DAY);
           minute=calendar.get(Calendar.MINUTE);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {

                case R.id.startTime:

                    timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            startTimeButton.setText(hourOfDay + ":"+minute);
                            startTime.setHours(hourOfDay);
                            startTime.setMinutes(minute);
                        }
                    },hour,minute,android.text.format.DateFormat.is24HourFormat(context));
                    timePickerDialog.show();
                    break;
                case R.id.endTime:

                    timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            endTimeButton.setText(hourOfDay + ":"+minute);
                            endTime.setHours(hourOfDay);
                            endTime.setMinutes(minute);
                        }
                    },hour,minute,android.text.format.DateFormat.is24HourFormat(context));
                    timePickerDialog.show();
                    break;
            }
        }
    }
}
