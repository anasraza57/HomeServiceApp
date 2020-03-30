package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ViewAllRequestsDialog extends AppCompatDialogFragment {
    Button assignHelperButton;
    static int REQUEST_CODE = 100;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.allrequests_dialog, null);

        assignHelperButton = view.findViewById(R.id.assignButton);
        assignHelperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AvailableHelpersActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
