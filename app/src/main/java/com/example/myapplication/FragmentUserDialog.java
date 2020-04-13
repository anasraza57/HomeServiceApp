package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class FragmentUserDialog extends AppCompatDialogFragment {

    int position;
    ArrayList<UserNotificationData> userNotificationData;
    TextView name,phone,gender,time;
    Button markAsRead,done;

    public FragmentUserDialog(int position, ArrayList<UserNotificationData> userNotificationData) {
        this.position = position;
        this.userNotificationData = userNotificationData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.user_notification_dialog, null);

        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
        gender=view.findViewById(R.id.gender);
        markAsRead=view.findViewById(R.id.mark);
        done=view.findViewById(R.id.doneButton);
        time=view.findViewById(R.id.timings);



        name.setText(userNotificationData.get(position).getHelperName());
        phone.setText(userNotificationData.get(position).getHelperPhone());
        gender.setText(userNotificationData.get(position).getHelperGender());
        time.setText(userNotificationData.get(position).getDateOfNotification());
       // name.setText(userNotificationData.get(position).getHelperName());

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);

        return builder.create();
    }
}
