package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAllUserRecyclerAdapter extends RecyclerView.Adapter<ViewAllUserRecyclerAdapter.ViewHolder> {

    private Context context;

    ArrayList<UsersData> usersData;
    ViewAllUserRecyclerAdapter(Context applicationContext,ArrayList<UsersData> usersData) {
        this.context = applicationContext;
        this.usersData=usersData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alluser_row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText(usersData.get(position).getName());
        holder.phone.setText(usersData.get(position).getPhone());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
            }
            private void openDialog(int pos) {
                ViewAllUserDialog dialog = new ViewAllUserDialog(usersData,pos);
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "User Details");

            }
        });

    }


    @Override
    public int getItemCount() {
        return usersData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, phone;
        ImageView closeButton;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            closeButton = itemView.findViewById(R.id.close);
            layout = itemView.findViewById(R.id.card);

            closeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.close:
                    break;
            }
        }


    }
}
