package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class JobRequestsData {

    String id;
    String helperName;
    String helperPhone;
    String jobTitle;
    ArrayList<String> times;
    String helperCNIC;



    public String getId() {
        return id;
    }

    public String getHelperName() {
        return helperName;
    }

    public String getHelperPhone() {
        return helperPhone;
    }

    public String getJobTitle() {
        return jobTitle;
    }


    public JobRequestsData(String id, String helperName, String helperPhone, String jobTitle) {
        this.id = id;
        this.helperName = helperName;
        this.helperPhone = helperPhone;
        this.jobTitle = jobTitle;
    }

    public JobRequestsData(String id, String helperName, String helperPhone, String jobTitle, ArrayList<String> times) {
        this.id = id;
        this.helperName = helperName;
        this.helperPhone = helperPhone;
        this.jobTitle = jobTitle;
        this.times = times;

        FirebaseFirestore.getInstance().collection("Helpers").document(helperPhone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                helperCNIC=documentSnapshot.getString("cnic");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
        Log.w("MyActivity","Job Request Data Faillure");
            }
        });
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public void setHelperPhone(String helperPhone) {
        this.helperPhone = helperPhone;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }
}
