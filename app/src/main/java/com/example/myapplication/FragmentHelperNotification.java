package com.example.myapplication;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragmentHelperNotification extends Fragment {
    View view;

    RecyclerView recyclerView;
    FragmentHelperRecyclerAdapter recyclerAdapter;
    String TAG="MyActivity";
    FirebaseFirestore db;
    ArrayList<HelperNotificationData> helperNotificationData =new ArrayList<>();
    String helperPhone;

    FragmentHelperNotification(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.helper_notification_fragment,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        helperPhone=SessionManager.getHelperPhone();
        Log.w(TAG,"This is helper phone : " + helperPhone);
        db=FirebaseFirestore.getInstance();

        db.collection("HelperNotifications").whereEqualTo("helperPhone",helperPhone).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Failure helper notification "+ e.getMessage());

            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot notification:queryDocumentSnapshots.getDocuments()) {
                    helperNotificationData.add(new HelperNotificationData(notification.getString("userName"),notification.getString("userPhone"),notification.getString("title"),notification.getDate("notificationDate").toString()));

                }
                Log.d(TAG, "hELPER nOTFICATIONS SIZE "+helperNotificationData.size() +"");
                recyclerAdapter = new FragmentHelperRecyclerAdapter(getContext(),helperNotificationData);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });

        return view;
    }
}
