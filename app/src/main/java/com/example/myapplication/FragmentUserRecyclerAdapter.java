package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class FragmentUserRecyclerAdapter extends RecyclerView.Adapter<FragmentUserRecyclerAdapter.ViewHolder> {
    private Context context;
    ArrayList<UserNotificationData> userNotificationData;
    String userPhone;

    public FragmentUserRecyclerAdapter(Context context, ArrayList<UserNotificationData> userNotificationData) {
        this.context = context;
        this.userNotificationData = userNotificationData;
    }

    public FragmentUserRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_notification_row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
            }
        });

    }

    private void openDialog(int pos) {
        FragmentUserDialog dialog = new FragmentUserDialog(pos, userNotificationData);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "Helper Details");

    }

    @Override
    public int getItemCount() {
        return userNotificationData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView notify;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notify = itemView.findViewById(R.id.notify);
            layout = itemView.findViewById(R.id.card);


        }

    }
}
