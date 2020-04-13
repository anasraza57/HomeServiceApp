package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class AvailableHelpersDialogue  extends AppCompatDialogFragment implements View.OnClickListener {


    private TextView startTimeText1, startTimeText2, startTimeText3, endTimeText1, endTimeText2, endTimeText3,name,title;
    int position;
    ArrayList<JobRequestsData> jobRequestsData;
    Button assign;

    public AvailableHelpersDialogue(int pos, ArrayList<JobRequestsData> jobRequestsData) {
        this.position = pos;
        this.jobRequestsData = jobRequestsData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.available_helper_dialogue, null);

        builder.setView(view);

        startTimeText1 = view.findViewById(R.id.startTime1);
        startTimeText2 = view.findViewById(R.id.startTime2);
        startTimeText3 = view.findViewById(R.id.startTime3);
        endTimeText1 = view.findViewById(R.id.endTime1);
        endTimeText2 = view.findViewById(R.id.endTime2);
        endTimeText3 = view.findViewById(R.id.endTime3);
        name=view.findViewById(R.id.name);
        title=view.findViewById(R.id.service);
        assign=view.findViewById(R.id.assignButton);





        name.setText(jobRequestsData.get(position).getHelperName());
        title.setText(jobRequestsData.get(position).getJobTitle());

        for (int i=0;i< jobRequestsData.get(position).times.size();i++) {
            if(i==0) {
                startTimeText1.setText(jobRequestsData.get(position).times.get(i));
                i++;
                endTimeText1.setText(jobRequestsData.get(position).times.get(i));
            }
            if(i==2)
            {
                startTimeText2.setText(jobRequestsData.get(position).times.get(i));
                i++;
                endTimeText2.setText(jobRequestsData.get(position).times.get(i));
            }
            if(i==4)
            {
                startTimeText3.setText(jobRequestsData.get(position).times.get(i));
                i++;
                endTimeText3.setText(jobRequestsData.get(position).times.get(i));
            }

        }

        assign.setOnClickListener(this);


        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.assignButton:


                break;
        }
    }
}
