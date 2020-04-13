package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ViewAllRequestsDialog extends AppCompatDialogFragment {
    Button assignHelperButton;
    TextView name,phone,title,timings;
    int position;
    ArrayList<ServiceRequestsData> serviceRequestsData=new ArrayList<>();
    static int REQUEST_CODE = 100;

    public ViewAllRequestsDialog(int position, ArrayList<ServiceRequestsData> serviceRequestsData) {
        this.position = position;
        this.serviceRequestsData = serviceRequestsData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.allrequests_dialog, null);
        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
        title=view.findViewById(R.id.service);
        timings=view.findViewById(R.id.timings);

        name.setText(serviceRequestsData.get(position).getUserName());
        phone.setText(serviceRequestsData.get(position).getUserPhone());
        title.setText(serviceRequestsData.get(position).getTitle());
        timings.setText(serviceRequestsData.get(position).getStartTime() + " to "+ serviceRequestsData.get(position).getEndTime());

        assignHelperButton = view.findViewById(R.id.assignButton);
        assignHelperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AvailableHelpersActivity.class);
                intent.putExtra("id",serviceRequestsData.get(position).getId());
                intent.putExtra("title",serviceRequestsData.get(position).getTitle());
                intent.putExtra("userName",serviceRequestsData.get(position).getUserName());
                intent.putExtra("userPhone",serviceRequestsData.get(position).getUserPhone());

                startActivity(intent);
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
