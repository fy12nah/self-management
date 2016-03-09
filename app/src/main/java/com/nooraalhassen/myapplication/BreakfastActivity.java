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

public class BreakfastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // defining widgets for use
        final EditText breakfast = (EditText) findViewById(R.id.BFName);
        final EditText bf_item = (EditText) findViewById(R.id.itemBF);
        final EditText bf_time = (EditText) findViewById(R.id.BFTime);
        final ImageView add_bf = (ImageView) findViewById(R.id.addBF);
        final Button save_bf = (Button) findViewById(R.id.BFSave);

        add_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        save_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}
