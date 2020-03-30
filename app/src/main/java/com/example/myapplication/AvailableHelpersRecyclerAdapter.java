package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

class AvailableHelpersRecyclerAdapter extends RecyclerView.Adapter<AvailableHelpersRecyclerAdapter.ViewHolder> {
    private Context context;

    AvailableHelpersRecyclerAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.available_helpers_row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button viewDetailsButton, assignButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assignButton = itemView.findViewById(R.id.assign);
            viewDetailsButton = itemView.findViewById(R.id.view_details);
            assignButton.setOnClickListener(this);
            viewDetailsButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_details:
                    openDialog();
                    break;

                case R.id.assign:
                    Intent i = new Intent(context, MainActivity.class);
                    context.startActivity(i); // calling intent to start new activity

                    ((Activity)context).finish(); // calling finish to destroy activity
                    break;
            }
        }

        private void openDialog() {
            ViewAllHelperDialog dialog = new ViewAllHelperDialog();
            dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "Helper Details");

        }
    }
}
