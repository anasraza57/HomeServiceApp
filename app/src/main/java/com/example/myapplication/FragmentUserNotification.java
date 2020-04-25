package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class FragmentUserNotification extends Fragment {
    View view;
    RecyclerView recyclerView;
    FragmentUserRecyclerAdapter recyclerAdapter;
    String TAG="MyActivity";
    FirebaseFirestore db;
    ArrayList<UserNotificationData> userNotificationData =new ArrayList<>();
    String userPhone;
    Context context;


    FragmentUserNotification() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_notification_fragment, container, false);

        context=view.getContext();

        userPhone=SessionManager.getUserPhone();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db=FirebaseFirestore.getInstance();

        db.collection("UserNotifications").whereEqualTo("userPhone",userPhone).orderBy("notificationDate").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Usre Notification Failure " + e.getMessage() );

            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot notification:queryDocumentSnapshots.getDocuments()) {
                    userNotificationData.add(new UserNotificationData(notification.getString("helperName"),notification.getString("helperPhone"),notification.getString("helperGender"),notification.getString("helperCNIC"),notification.getString("title"),notification.getDate("notificationDate").toString(),notification.getString("id")));

                }
                recyclerAdapter = new FragmentUserRecyclerAdapter(getContext(),userNotificationData);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });


        return view;
    }
}
