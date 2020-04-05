package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class ViewAllHelpersActivity extends AppCompatActivity {


    Button usersButton, HelpersButton;
    RecyclerView recyclerView;
    ViewAllHelperRecyclerAdapter recyclerAdapter;
    FirebaseFirestore db;
    String TAG="MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_helpers);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new ViewAllHelperRecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
    }
}
