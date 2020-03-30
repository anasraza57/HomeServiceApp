package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewAllHelpersActivity extends AppCompatActivity implements View.OnClickListener{

    Button usersButton, HelpersButton, viewRequestButton;
    RecyclerView recyclerView;
    ViewAllHelperRecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_helpers);

        usersButton = findViewById(R.id.users);
        HelpersButton = findViewById(R.id.helpers);
        viewRequestButton = findViewById(R.id.view_requests);

        usersButton.setOnClickListener(this);
        HelpersButton.setOnClickListener(this);
        viewRequestButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new ViewAllHelperRecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.users:
                intent = new Intent(this, ViewAllUsersActivity.class);
                startActivity(intent);
                break;
            case R.id.helpers:
                intent = new Intent(this, ViewAllHelpersActivity.class);
                startActivity(intent);
                break;
            case R.id.view_requests:
                intent = new Intent(this, ViewAllRequestsActivity.class);
                startActivity(intent);
                break;


        }
    }
}
