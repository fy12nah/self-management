package com.nooraalhassen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Lifestyle extends AppCompatActivity {

    Button meals;
    Button exer;
    Button sleep;
    Button mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        meals = (Button) findViewById(R.id.meals_ls);
        mood = (Button) findViewById(R.id.mood_ls);
        exer = (Button) findViewById(R.id.exer_ls);
        sleep = (Button) findViewById(R.id.sleep_ls);

        // action - goes to Meals view when text is clicked
        meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Lifestyle.this, Meals.class);
                startActivity(intent);
            }
        });

        // action - goes to Mood view when text is clicked
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Lifestyle.this, MoodActivity.class);
                startActivity(intent);
            }
        });

        // action - goes to exercises view when text is clicked
        exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Lifestyle.this, ExercisesActivity.class);
                startActivity(intent);
            }
        });

        // action - goes to sleeping hours view when text is clicked
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Lifestyle.this, SleepingActivity.class);
                startActivity(intent);
            }
        });

    }

}
