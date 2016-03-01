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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profile_tv = (TextView) findViewById(R.id.profile_tv);
        physical_tv = (TextView) findViewById(R.id.physical_tv);

        // action
        profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, Profile.class);
                startActivity(intent);
            }
        });

        // action
        physical_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, Physical.class);
                startActivity(intent);
            }
        });
    }

}
