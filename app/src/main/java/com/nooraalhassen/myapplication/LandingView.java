package com.nooraalhassen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class LandingView extends AppCompatActivity {

    TextView profile_tv;
    TextView physical_tv;
    TextView lifestyle_tv;
    TextView illness_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile_tv = (TextView) findViewById(R.id.profile_tv);
        physical_tv = (TextView) findViewById(R.id.physical_tv);
        lifestyle_tv = (TextView) findViewById(R.id.lifes_tv);
        illness_tv = (TextView) findViewById(R.id.illness_tv);

        // action - goes to Profile view when text is clicked
        profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, Profile.class);
                startActivity(intent);
            }
        });

        // action - goes to Physical view when text is clicked
        physical_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, physical.class);
                startActivity(intent);
            }
        });

        // action - goes to Lifestyle view when text is clicked
        lifestyle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(LandingView.this, Lifestyle.class);
               // startActivity(intent);
            }
        });

        // action - goes to Illness view when text is clicked
        illness_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(LandingView.this, Illness.class);
               // startActivity(intent);
            }
        });
    }

}
