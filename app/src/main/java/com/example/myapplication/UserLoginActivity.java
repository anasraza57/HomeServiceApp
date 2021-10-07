package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView registerButton;
    Button loginButton;
    EditText phoneText,passText;
    TextView errorMsg;
    FirebaseFirestore db;
    final static String TAG="MyActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = findViewById(R.id.register);
        loginButton = findViewById(R.id.loginButton);
        phoneText=findViewById(R.id.phoneText);
        passText=findViewById(R.id.passText);
        errorMsg=findViewById(R.id.errorMessage);
        db= FirebaseFirestore.getInstance();




        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){

            case R.id.loginButton:
                Log.w(TAG,"Login clicked");
                final String phone=phoneText.getText().toString();
                final String password=passText.getText().toString();
                if(phone == "" || password=="")
                {
                    errorMsg.setText("Please fill all fields");
                    return;
                }
                else{
                    db.collection("Users").whereEqualTo("phone",phone).whereEqualTo("password",password).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.getDocuments().isEmpty())
                            {
                                errorMsg.setText("Invalid Phone or password");
                                return;
                            }
                            else {
                                SessionManager.createLoginSessionForUser(phone);
                                Intent i=new Intent(getApplicationContext(),HelperActivity.class);
                                startActivity(i);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG,"Failure listener of User login activity");
                        }
                    });
                }

                break;
            case R.id.register:
                intent= new Intent(getApplicationContext(), UserRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
