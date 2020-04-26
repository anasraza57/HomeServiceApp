package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CarouselView carouselView;
    TextView textView, textViewHeader, signinTextView;
    Button bookHelperButton, getJobButton, ourChargesButton, servicesButton;
    ImageView notifyButton;


    int[] sampleImages = {R.drawable.clean, R.drawable.launday, R.drawable.utensil,R.drawable.babysitter,R.drawable.patient,R.drawable.plmbr,R.drawable.Ali_Cooling_Electrical_Home_Services_citybook_1};
    String[] textImagesHeading = {"Cleaning", "Laundary", "UtensilWashing", "Baby Sitter", "Patient Care", "Plumber", "Electrician"};

    String[] textImages = {"Info about home service providers", "Info about home service providers", "Book our Cleaners now", "Book our Cleaners now", "Book our Cleaners now", "Book our Cleaners now", "Book our Cleaners now"};
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carouselView = findViewById(R.id.carouselView);
        textView = findViewById(R.id.imageText);
        textViewHeader = findViewById(R.id.imageTextHeading);
        bookHelperButton = findViewById(R.id.bookHelper);
        getJobButton = findViewById(R.id.getJob);
        ourChargesButton = findViewById(R.id.ourCharges);
        servicesButton = findViewById(R.id.services);
        signinTextView = findViewById(R.id.signin);
        notifyButton = findViewById(R.id.notify);

        carouselView.addOnPageChangeListener(onPageChangeListener);
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.length);
        bookHelperButton.setOnClickListener(this);
        getJobButton.setOnClickListener(this);
        ourChargesButton.setOnClickListener(this);
        servicesButton.setOnClickListener(this);
        signinTextView.setOnClickListener(this);
        notifyButton.setOnClickListener(this);

        sessionManager=new SessionManager(getApplicationContext());
        if(!SessionManager.isUserLoggedIn() && !SessionManager.isHelperLoggedIn())
        {
            notifyButton.setVisibility(View.GONE);
        }





    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            textView.setText(textImages[position]);
            textViewHeader.setText(textImagesHeading[position]);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            imageView.setImageResource(sampleImages[position]);


        }
    };

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.bookHelper:
                if(sessionManager.isUserLoggedIn())
                {
                    intent=new Intent(getApplicationContext(),HelperActivity.class);
                }
                else intent=new Intent(getApplicationContext(),UserLoginActivity.class);

                startActivity(intent);
                break;

            case R.id.getJob:
                if(sessionManager.isHelperLoggedIn())
                {
                    intent=new Intent(getApplicationContext(),JobActivity.class);
                }
                else intent=new Intent(getApplicationContext(),LoginActivity.class);

                startActivity(intent);

            break;


            case R.id.ourCharges:
            intent= new Intent(getApplicationContext(), HelperActivity.class);
                startActivity(intent);
            case R.id.services:
                intent= new Intent(getApplicationContext(), HelperActivity.class);
                startActivity(intent);
                break;

            case R.id.signin:
                intent= new Intent(getApplicationContext(), ViewAllUsersActivity.class);
                startActivity(intent);
                break;

            case R.id.notify:

                    intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);

                break;

        }
    }
}
