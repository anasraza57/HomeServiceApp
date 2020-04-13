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

class FragmentHelperRecyclerAdapter extends RecyclerView.Adapter<FragmentHelperRecyclerAdapter.ViewHolder> {
    private Context context;
    ArrayList<HelperNotificationData> helperNotificationData;
    String helperPhone;

    public FragmentHelperRecyclerAdapter(Context context, ArrayList<HelperNotificationData> helperNotificationData) {
        this.context = context;
        this.helperNotificationData = helperNotificationData;
    }

    public FragmentHelperRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.helper_notification_row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
            }
        });
    }

    private void openDialog(int pos) {
        FragmentHelperDialog dialog = new FragmentHelperDialog(pos, helperNotificationData);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "User Details");

    }

    @Override
    public int getItemCount() {
        return helperNotificationData.size();
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
