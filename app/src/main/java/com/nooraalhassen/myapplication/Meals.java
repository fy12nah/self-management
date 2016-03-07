package com.nooraalhassen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Meals extends AppCompatActivity {

    Button breakfast;
    Button lunch;
    Button dinner;
    Button snacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        breakfast = (Button) findViewById(R.id.breakfast_meals);
        lunch = (Button) findViewById(R.id.lunch_meals);
        dinner = (Button) findViewById(R.id.dinner_meals);
        snacks = (Button) findViewById(R.id.snacks_meals);

        // action - goes to Meals view when text is clicked
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Meals.this, BreakfastActivity.class);
                startActivity(intent);
            }
        });

        // action - goes to Mood view when text is clicked
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Meals.this, LunchActivity.class);
                startActivity(intent);
            }
        });

        // action - goes to exercises view when text is clicked
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Meals.this, DinnerActivity.class);
                startActivity(intent);
            }
        });

        // action - goes to sleeping hours view when text is clicked
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Meals.this, SnacksActivity.class);
                startActivity(intent);
            }
        });


    }

}
