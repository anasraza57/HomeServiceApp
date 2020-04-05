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

public class ViewAllUserDialog extends AppCompatDialogFragment {

    ArrayList<UsersData> usersData;
    int position;
    TextView address;
    TextView name;
    TextView phone;

    public ViewAllUserDialog(ArrayList<UsersData> usersData,int position) {
        this.usersData = usersData;
        this.position=position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alluser_dialog, null);

        builder.setView(view);

        name=view.findViewById(R.id.name);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);

        name.setText(usersData.get(position).getName());
        address.setText(usersData.get(position).getAddress());
        phone.setText(usersData.get(position).getPhone());

        return builder.create();
    }
}
