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

public class LunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // defining widgets for use
        final EditText lunch = (EditText) findViewById(R.id.lunchName);
        final EditText lnch_item = (EditText) findViewById(R.id.itemLunch);
        final EditText lnch_time = (EditText) findViewById(R.id.lunchTime);
        final ImageView add_lnch = (ImageView) findViewById(R.id.addLunch);
        final Button save_lnch = (Button) findViewById(R.id.lunchSave);

        add_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        save_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}
