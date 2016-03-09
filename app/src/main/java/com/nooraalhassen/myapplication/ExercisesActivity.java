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
import android.widget.TextView;

public class ExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // defining widgets for use
        // final EditText exer_type = (EditText) findViewById(R.id.exerType);
        final EditText exer_start = (EditText) findViewById(R.id.exer_start);
        final EditText exer_end = (EditText) findViewById(R.id.exer_end);
        final ImageView add_exer = (ImageView) findViewById(R.id.addExer);
        final Button save_exer = (Button) findViewById(R.id.exerSave);
        final TextView exer_msg = (TextView) findViewById(R.id.exerMsg);

        add_exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        save_exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}
