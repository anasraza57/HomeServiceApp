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

class ViewAllRequestsRecyclerAdapter extends RecyclerView.Adapter<ViewAllRequestsRecyclerAdapter.ViewHolder> {
    private Context context;

    ViewAllRequestsRecyclerAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.allrequests_row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView requestMessageTextView;
        Button viewRequestButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestMessageTextView = itemView.findViewById(R.id.request);
            viewRequestButton = itemView.findViewById(R.id.view_request);
            viewRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog();
                }
            });
        }

        private void openDialog() {
            ViewAllRequestsDialog dialog = new ViewAllRequestsDialog();
            dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "User Request");

        }
    }
}
