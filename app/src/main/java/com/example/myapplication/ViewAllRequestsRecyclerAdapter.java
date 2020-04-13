package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ViewAllRequestsRecyclerAdapter extends RecyclerView.Adapter<ViewAllRequestsRecyclerAdapter.ViewHolder> {
    private Context context;
    ArrayList<ServiceRequestsData> serviceRequestsData;

    ViewAllRequestsRecyclerAdapter(Context applicationContext,ArrayList<ServiceRequestsData> serviceRequestsData ) {
        this.context = applicationContext;
        this.serviceRequestsData=serviceRequestsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.allrequests_row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(serviceRequestsData.get(position).getTitle());
        holder.viewRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
            }
        });

    }

    private void openDialog(int pos) {
        ViewAllRequestsDialog dialog = new ViewAllRequestsDialog(pos,serviceRequestsData);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "User Request");

    }

    @Override
    public int getItemCount() {
        return serviceRequestsData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button viewRequestButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.request);
            viewRequestButton = itemView.findViewById(R.id.view_request);

        }


    }
}
