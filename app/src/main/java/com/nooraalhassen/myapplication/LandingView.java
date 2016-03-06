package com.nooraalhassen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LandingView extends AppCompatActivity {

    Button profile_tv;
    Button physical_tv;
    Button illness_tv;
    Button meals_tv;
    Button exer_tv;
    Button sleep_tv;
    Button mood_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile_tv = (Button) findViewById(R.id.landing_profile);
        physical_tv = (Button) findViewById(R.id.landing_physical);
        illness_tv = (Button) findViewById(R.id.landing_illness);
        meals_tv = (Button) findViewById(R.id.landing_meals);
        mood_tv = (Button) findViewById(R.id.landing_mood);
        exer_tv = (Button) findViewById(R.id.landing_exer);
        sleep_tv = (Button) findViewById(R.id.landing_sleep);


        // action - goes to ProfileActivity view when text is clicked
        profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, ProfileActivity.class);
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


        // action - goes to Illness view when text is clicked
        illness_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(LandingView.this, Illness.class);
               // startActivity(intent);
            }
        });


        // action - goes to Meals view when text is clicked
        meals_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(LandingView.this, Meals.class);
                // startActivity(intent);
            }
        });

        // action - goes to Mood view when text is clicked
        mood_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(LandingView.this, Mood.class);
                // startActivity(intent);
            }
        });

        // action - goes to exercises view when text is clicked
        exer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(LandingView.this, Exercises.class);
                // startActivity(intent);
            }
        });

        // action - goes to sleeping hours view when text is clicked
        sleep_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(LandingView.this, Sleeping.class);
                // startActivity(intent);
            }
        });
    }

}
