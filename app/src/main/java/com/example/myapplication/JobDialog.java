package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class JobDialog extends AppCompatDialogFragment  {

    private TextView startTimeText1, startTimeText2, startTimeText3, endTimeText1, endTimeText2, endTimeText3;
    TimePickerDialog timePickerDialog;
    Button done;
    Context context;
    ArrayList<Time> times=new ArrayList<>();
    public static String TAG="MyActivity";
    ArrayList<JobRowData> jobData;
    CommonArray comm;
    int pos;
    Button cancelButton;
    TextView errorMsg;
    final  Time startTime1=new Time();
    final  Time startTime2=new Time();
    final  Time startTime3=new Time();
    final  Time endTime1=new Time();
    final  Time endTime2=new Time();
    final  Time endTime3=new Time();

    public  boolean isTimeSelected()
    {

        if((startTime1.getHours()!=0 && endTime1.getHours()!=0) || (startTime2.getHours()!=0 && endTime2.getHours()!=0) || (startTime3.getHours()!=0 && endTime3.getHours()!=0))
        {  return true;}
        else return false;

    }

    JobDialog(ArrayList<JobRowData> arr,int pos,JobRecyclerAdapter obj)
    {
        comm=obj;
        jobData=arr;
        this.pos=pos;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view);
        errorMsg=view.findViewById(R.id.errorMsg);
        done=view.findViewById(R.id.doneButton);
        cancelButton=view.findViewById(R.id.cancelButton);
        startTimeText1 = view.findViewById(R.id.startTime1);
        startTimeText2 = view.findViewById(R.id.startTime2);
        startTimeText3 = view.findViewById(R.id.startTime3);
        endTimeText1 = view.findViewById(R.id.endTime1);
        endTimeText2 = view.findViewById(R.id.endTime2);
        endTimeText3 = view.findViewById(R.id.endTime3);


        //,endTime1,startTime2,endTime2,startTime3,endTime3;



        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        Calendar calendar=Calendar.getInstance();

        final int hour=calendar.get(Calendar.HOUR_OF_DAY);
        final int minute=calendar.get(Calendar.MINUTE);

        // initializeComponent();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isTimeSelected()) {
                    jobData.get(pos).setSelected(true);

                    addDataToArrayList();

                    comm.setArray(jobData, pos);
                    dismiss();
                }
                else{
                    errorMsg.setText("Please Select Atleast one time");
                }
            }
        });


        startTimeText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"cLICKED");
                timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTimeText1.setText(hourOfDay + ":"+minute);
                        startTime1.setHours(hourOfDay);
                        startTime1.setMinutes(minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
        startTimeText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTimeText2.setText(hourOfDay + ":"+minute);
                        startTime2.setHours(hourOfDay);
                        startTime2.setMinutes(minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        startTimeText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTimeText3.setText(hourOfDay + ":"+minute);
                        startTime3.setHours(hourOfDay);
                        startTime3.setMinutes(minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
        endTimeText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTimeText1.setText(hourOfDay + ":"+minute);
                        endTime1.setHours(hourOfDay);
                        endTime1.setMinutes(minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
        endTimeText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTimeText2.setText(hourOfDay + ":"+minute);
                        endTime2.setHours(hourOfDay);
                        endTime2.setMinutes(minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
        endTimeText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTimeText3.setText(hourOfDay + ":"+minute);
                        endTime3.setHours(hourOfDay);
                        endTime3.setMinutes(minute);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
    }

    private void initializeComponent() {

        if(startTime1.getHours()!=0)
        {

        }
        if(startTime2.getHours()!=0)
        {

        }
        if(startTime3.getHours()!=0)
        {

        }

    }

    public void addDataToArrayList()
    {
        if(startTime1.getHours()!=0 && endTime1.getHours()!=0)
        {
            jobData.get(pos).time.add(startTime1);
            jobData.get(pos).time.add(endTime1);
        }
        if(startTime2.getHours()!=0 && endTime2.getHours()!=0)
        {
            jobData.get(pos).time.add(startTime2);
            jobData.get(pos).time.add(endTime2);
        }
        if(startTime3.getHours()!=0 && endTime3.getHours()!=0)
        {
            jobData.get(pos).time.add(startTime3);
            jobData.get(pos).time.add(endTime3);
        }
    }


}
