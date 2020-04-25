package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewAllUserRecyclerAdapter extends RecyclerView.Adapter<ViewAllUserRecyclerAdapter.ViewHolder> {

    private Context context;
    FirebaseFirestore db;
    boolean flag;
    final String TAG="MyActivity";

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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you want to delete ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flag=true;
                        dialog.dismiss();

                         // stop chronometer here

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flag=false;
                        dialog.dismiss();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                if(flag==false)
                {
                    return;
                }
                db=FirebaseFirestore.getInstance();

                db.collection("Users").document(usersData.get(position).getPhone()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"User Deleted",Toast.LENGTH_LONG).show();
                        usersData.remove(position);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"dELETE USER FAILURE");
                    }
                });

                notifyDataSetChanged();
            }
        });


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

        RelativeLayout layout;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);

            layout = itemView.findViewById(R.id.card);
            delete=itemView.findViewById(R.id.delete);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete:
                    break;
            }
        }


    }
}
