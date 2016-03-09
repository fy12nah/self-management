package com.nooraalhassen.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class DinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // defining widgets for use
        final EditText dinner = (EditText) findViewById(R.id.dinnerName);
        final EditText dinner_item = (EditText) findViewById(R.id.itemDinner);
        final EditText dinner_time = (EditText) findViewById(R.id.dinnerTime);
        final ImageView add_dinner = (ImageView) findViewById(R.id.addDinner);
        final Button save_dinner = (Button) findViewById(R.id.dinnerSave);

        add_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        save_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

}
