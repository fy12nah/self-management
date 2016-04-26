package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.adapters.MoodNameAdapter;
import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Mood;
import com.nooraalhassen.myapplication.model.Sleeping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MoodActivity extends AppCompatActivity {

    String activityMode;
    long user_id;
    Spinner mood;
    EditText mood_date;
    EditText mood_Time;
    final DBmanager mgr = new DBmanager(MoodActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);
        Intent intent = getIntent();


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
        mood = (Spinner) findViewById(R.id.moodName);
        populateMoodNames();

        final EditText mood_reason = (EditText) findViewById(R.id.mdReason);
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


        final long id = intent.getLongExtra(Constants.moodID, -1);
        final String dateString = intent.getStringExtra(Constants.moodDate);
        if (dateString != null){
            mood_date.setText(dateString);
        }

        final SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);

        save_mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting edittext values
                long mood_nameID = mood.getSelectedItemId();
                String moodReason = mood_reason.getText().toString();
                String moodDate = mood_date.getText().toString();
                String moodTime = mood_Time.getText().toString();


                Date DateMood = null;
                try {
                    DateMood = simpleDateFormatD.parse(moodDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (activityMode.equals(Constants.addMode)) {
                    boolean saved = mgr.insert_mood(user_id, mood_nameID, moodReason, DateMood, moodTime);
                    if (saved == true) {
                        Toast.makeText(MoodActivity.this, "Mood entries are saved", Toast.LENGTH_LONG).show();

                        // go to another view - Landing view
                        Intent intent = new Intent(MoodActivity.this, LandingView.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MoodActivity.this, "Failed to save Mood entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updateMood(id, mood_nameID, moodReason, DateMood, moodTime);
                    if (updated == true) {
                        Toast.makeText(MoodActivity.this, "Mood Status entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(MoodActivity.this, "Failed to update Mood Status entries", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Mood s = mgr.getMoodByID(id, user_id);

            if (s != null){
                mood_date.setText(simpleDateFormatD.format(s.getMoodDate()));
                mood_reason.setText(s.getMoodReason());
                int count = mood.getCount();
                for (int i = 0; i < count; i++){
                    if (mood.getItemIdAtPosition(i) == s.getMoodName().getMoodNameID()){
                        mood.setSelection(i);
                        break;
                    }
                }
                mood_Time.setText(s.getMoodTime());
            }
            else {
                Toast.makeText(MoodActivity.this, "Invalid Mood ID", Toast.LENGTH_LONG).show();
            }
        }
        else activityMode = Constants.addMode;

    }


    public void populateMoodNames(){

        Cursor c = mgr.getMoodNameCursor(user_id);

        MoodNameAdapter adapter = new MoodNameAdapter(this, c, false);
        mood.setAdapter(adapter);

    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MoodActivity.class);
    }


    public static Intent createIntentForEdit(Context context, long id) {
        Intent intent = new Intent(context, MoodActivity.class);
        intent.putExtra(Constants.moodID, id);
        return intent;
    }

    public static Intent createIntent(Context context, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
        Intent intent = new Intent(context, MoodActivity.class);
        intent.putExtra(Constants.moodDate, simpleDateFormat.format(date));
        return intent;
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
