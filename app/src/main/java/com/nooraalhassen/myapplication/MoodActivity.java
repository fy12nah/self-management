package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MoodActivity extends AppCompatActivity {

    long user_id;
    EditText mood_date;
    EditText mood_Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);

        // getting current time
        mood_date = (EditText) findViewById(R.id.moodDate);
        mood_Time = (EditText) findViewById(R.id.mdTime);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText mood = (EditText) findViewById(R.id.moodName);
        final EditText mood_reason = (EditText) findViewById(R.id.mdReason);
        /*final EditText mood_date = (EditText) findViewById(R.id.moodDate);
        final EditText mood_Time = (EditText) findViewById(R.id.mdTime);*/
        final Button save_mood = (Button) findViewById(R.id.saveMood);


        ImageView dateDialog = (ImageView) findViewById(R.id.moodDD);
        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(MoodActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // time calander dialog
        final ImageView time_dialog = (ImageView) findViewById(R.id.mood_timeD);
        time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(MoodActivity.this, new TimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });



        save_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(MoodActivity.this);

                // getting edittext values
                String mood_name = mood.getText().toString();
                String moodReason = mood_reason.getText().toString();
                String moodDate = mood_date.getText().toString();
                String moodTime = mood_Time.getText().toString();

                SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date DateMood = null;


                try {
                    DateMood = simpleDateFormatD.parse(moodDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean saved = mgr.insert_mood(user_id, mood_name, moodReason, DateMood, moodTime);
                if (saved == true){
                    Toast.makeText(MoodActivity.this, "Mood entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Landing view
                    Intent intent = new Intent(MoodActivity.this, LandingView.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MoodActivity.this, "Failed to save Mood entries", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mood_date.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    }



    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            mood_Time.setText(hourOfDay + ":" + minute);
        }
    }

}
