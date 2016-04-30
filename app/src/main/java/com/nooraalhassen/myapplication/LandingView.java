package com.nooraalhassen.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Profile;

import java.util.HashMap;

public class LandingView extends AppCompatActivity {

    ImageButton profile_tv;
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

        profile_tv = (ImageButton) findViewById(R.id.LVProfile);
        physical_tv = (Button) findViewById(R.id.landing_physical);
        illness_tv = (Button) findViewById(R.id.landing_illness);
        meals_tv = (Button) findViewById(R.id.landing_meals);
        mood_tv = (Button) findViewById(R.id.landing_mood);
        exer_tv = (Button) findViewById(R.id.landing_exer);
        sleep_tv = (Button) findViewById(R.id.landing_sleep);

        DBmanager manager = new DBmanager(this);
        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        long user_id = preferences.getLong(Constants.userId, -1);


        Profile p = manager.getProfile(user_id);

        if (p == null){
            logout();
            return;
        }


        for (HashMap.Entry<String, Boolean> s: p.getCategories().entrySet()){
            switch (s.getKey()){
                case "Physical":
                    if (s.getValue())
                        physical_tv.setVisibility(View.VISIBLE);
                    else physical_tv.setVisibility(View.GONE);
                    break;
                case "Illness":
                    if (s.getValue())
                        illness_tv.setVisibility(View.VISIBLE);
                    else illness_tv.setVisibility(View.GONE);
                    break;
                case "Meals":
                    if (s.getValue())
                        meals_tv.setVisibility(View.VISIBLE);
                    else meals_tv.setVisibility(View.GONE);
                    break;
                case "Mood Status":
                    if (s.getValue())
                        mood_tv.setVisibility(View.VISIBLE);
                    else mood_tv.setVisibility(View.GONE);
                    break;
                case "Exercises":
                    if (s.getValue())
                        exer_tv.setVisibility(View.VISIBLE);
                    else exer_tv.setVisibility(View.GONE);
                    break;
                case "Sleeping Hours":
                    if (s.getValue())
                        sleep_tv.setVisibility(View.VISIBLE);
                    else sleep_tv.setVisibility(View.GONE);
                    break;
            }
        }


        // action - goes to ProfileActivity view when text is clicked
        profile_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // action - goes to physical view when text is clicked
        physical_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = PhysicalActivity.createIntent(LandingView.this);
                startActivity(intent);
            }
        });


        // action - goes to Illness view when text is clicked
        illness_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, IllnessActivity.class);
               startActivity(intent);
            }
        });


        // action - goes to Meals view when text is clicked
        meals_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, Meals.class);
                startActivity(intent);
            }
        });

        // action - goes to Mood view when text is clicked
        mood_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = MoodActivity.createIntent(LandingView.this);
                startActivity(intent);
            }
        });

        // action - goes to exercises view when text is clicked
        exer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = ExercisesActivity.createIntent(LandingView.this);
                startActivity(intent);
            }
        });

        // action - goes to sleeping hours view when text is clicked
        sleep_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = SleepingActivity.createIntent(LandingView.this);
                startActivity(intent);
            }
        });

        ImageView insp = (ImageView) findViewById(R.id.inspect);
        int color = Color.parseColor("#FF000000");
        insp.setColorFilter(color);
        insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, InspectionActivity.class);
                startActivity(intent);

            }
        });

        ImageView download = (ImageView) findViewById(R.id.LVdownload);
        download.setColorFilter(color);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LandingView.this, JsonTestActivity.class);
                startActivity(intent);

            }
        });


        ImageView logout = (ImageView) findViewById(R.id.btnLogout);
        logout.setColorFilter(color);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();

            }
        });


    }

    public void logout(){
        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Constants.userId);
        editor.commit();

        // go to another view - landing view
        Intent intent = new Intent(LandingView.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
