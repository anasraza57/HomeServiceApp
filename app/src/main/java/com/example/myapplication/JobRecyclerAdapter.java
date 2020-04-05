package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobRecyclerAdapter extends RecyclerView.Adapter<JobRecyclerAdapter.ViewHolder> implements CommonArray{

    private Context context;
    ArrayList<JobRowData> jobData;
    final JobRecyclerAdapter ref=this;
    final static String TAG="MyActivity";

    JobRecyclerAdapter(Context applicationContext,ArrayList<JobRowData> jobData) {
        this.context = applicationContext;
        this.jobData=jobData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.job_row_data, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if(jobData.get(position).isSelected())
        {
            Log.w(TAG,"isSelected");
            holder.selectButton.setText("Selected");
        }
        else{
            holder.selectButton.setText("Select");
        }
        holder.title.setText(jobData.get(position).title);

        holder.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!jobData.get(position).isSelected()) {
                    openDialog();
                } else {
                    removeSelected(position);
                }
            }

                private void openDialog() {

                    JobDialog dialog = new JobDialog(jobData,position,ref);
                    dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"Select Timings");


            }
        });
    }

    @Override
    public int getItemCount() {
        return jobData.size();
    }

    public void removeSelected(int pos)
    {
        jobData.get(pos).setSelected(false);
        if(!jobData.get(pos).time.isEmpty())
        jobData.get(pos).time.clear();
        this.notifyItemChanged(pos);
    }
    @Override
    public void setArray(ArrayList<JobRowData> arr , int i) {
        Log.w(TAG,"setArrayCalled");
        this.jobData=arr;
        this.notifyItemChanged(i);
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button selectButton;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            selectButton = itemView.findViewById(R.id.selectButton);

        }
    }
}
