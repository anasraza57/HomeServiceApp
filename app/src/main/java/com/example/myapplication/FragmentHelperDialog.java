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

public class FragmentHelperDialog extends AppCompatDialogFragment {

    int position;
    ArrayList<HelperNotificationData> helperNotificationData;
    TextView name,phone,title,time;
    Button markAsRead,done;

    public FragmentHelperDialog(int position, ArrayList<HelperNotificationData> helperNotificationData) {
        this.position = position;
        this.helperNotificationData = helperNotificationData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.helper_notification_dialog, null);
        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
        title=view.findViewById(R.id.title);
        markAsRead=view.findViewById(R.id.mark);
        done=view.findViewById(R.id.doneButton);
        time=view.findViewById(R.id.timings);

        name.setText(helperNotificationData.get(position).getUserName());
        phone.setText(helperNotificationData.get(position).getUserPhone());
        title.setText(helperNotificationData.get(position).getTitle());
        time.setText(helperNotificationData.get(position).getDateOfNotification());

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
