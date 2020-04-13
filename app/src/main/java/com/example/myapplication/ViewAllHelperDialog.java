package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ViewAllHelperDialog extends AppCompatDialogFragment {

    int position;
    ArrayList<HelpersData> helpersData;
    TextView name,phone,gender,cnic;

    public ViewAllHelperDialog(int position, ArrayList<HelpersData> helpersData) {
        this.position = position;
        this.helpersData = helpersData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.allhelpers_dialog, null);

        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
        gender=view.findViewById(R.id.gender);
        cnic=view.findViewById(R.id.cnic);

        name.setText(helpersData.get(position).getName());
        phone.setText(helpersData.get(position).getPhone());
        gender.setText(helpersData.get(position).getGender());
        cnic.setText(helpersData.get(position).getCnic());

        builder.setView(view);



        return builder.create();
    }
}
