package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegisterActivity extends AppCompatActivity {

    boolean flag=true;
    TextView signButton;
    Button registerButton;

    TextInputLayout nameText;
    TextInputLayout addressText;
    TextInputLayout phoneText;
    TextInputLayout passText;



    FirebaseFirestore db ;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);



        signButton = findViewById(R.id.signin);
        nameText=findViewById(R.id.nameText);
        addressText=findViewById(R.id.addressText);
        phoneText=findViewById(R.id.phoneText);
        passText=findViewById(R.id.passText);
        registerButton=findViewById(R.id.registerButton);
        db= FirebaseFirestore.getInstance();


        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserLoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=nameText.getEditText().getText().toString();
                final String phone=phoneText.getEditText().getText().toString();
                final String address=addressText.getEditText().getText().toString();
                final String password=passText.getEditText().getText().toString();

                if(name==null || phone == null || address==null || password==null)
                {
                    Toast.makeText(getApplicationContext(), "Please Fill the required fields",Toast.LENGTH_LONG).show();
                    return;
                }
                db.collection("Users").document(phone).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(!documentSnapshot.exists())
                        {
                            if(!isValid(phone))
                            {Toast.makeText(getApplicationContext(), "Invalid phone",Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(registerUser(name,phone,address,password)==true)
                            {
                                SessionManager.createLoginSessionForUser(phone);
                                Intent intent=new Intent(getApplicationContext(),HelperActivity.class);
                                startActivity(intent);
                            }
                            else Log.i(TAG,"registerUser rETURNED false");
                        }
                        else
                        {
                            Log.w(TAG,"aLREADY EXIST");

                            Toast.makeText(getApplicationContext(),"Account Already exist, Sign in to continue",Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"On Failure called of register User");
                    }
                });



            }
        });

    }

    public static boolean isValid(String s)
    {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        // (m.find() && m.group().equals(s));
        return true;
    }

    public boolean registerUser(String name, String phone, String address, String pass)
    {



        Map<String,String> users=new HashMap<>();
        users.put("name",name);
        users.put("phone",phone);
        users.put("address",address);
        users.put("password",pass);
        // users.put("cnicImage",cnicImage);
        Log.w(TAG,name+phone);

        db.collection("Users").document(phoneText.getEditText().getText().toString()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"SUCKSEXFUL");
                flag=true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"NotSUCKSEXFUL",e);
                flag=false;
            }
        });


        return flag;
    }


}
