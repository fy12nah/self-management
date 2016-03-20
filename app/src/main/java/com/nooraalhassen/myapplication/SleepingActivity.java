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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepingActivity extends AppCompatActivity {

    long user_id;
    TextView sleep_dur;
    EditText sleep_date;
    EditText sleep_start;
    EditText sleep_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);

        sleep_date = (EditText) findViewById(R.id.slpDate);
        sleep_start = (EditText) findViewById(R.id.sleepStart);
        sleep_end = (EditText) findViewById(R.id.sleepEnd);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText slpDate = (EditText) findViewById(R.id.slpDate);
        final EditText sTime = (EditText) findViewById(R.id.sleepStart);
        final EditText eTime = (EditText) findViewById(R.id.sleepEnd);
        final Button save_sleep = (Button) findViewById(R.id.sleepSave);
        sleep_dur = (TextView) findViewById(R.id.sleepDur);


        ImageView date_dialog = (ImageView) findViewById(R.id.date_Dialog);
        date_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(SleepingActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // time picker dialog
        final ImageView Stime_dialog = (ImageView) findViewById(R.id.startSleep_dialog);
        Stime_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(SleepingActivity.this, new startTimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });


        // time picker dialog
        final ImageView Etime_dialog = (ImageView) findViewById(R.id.endSleep_dialog);
        Etime_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(SleepingActivity.this, new endTimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });



        save_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(SleepingActivity.this);

                // getting edittext values
                String sleepDate = slpDate.getText().toString();
                String sleepStart = sTime.getText().toString();
                String sleepEnd = eTime.getText().toString();
                String durSlp = sleep_dur.getText().toString();

                SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date DateSlp = null;
                Date EndSlp = null;
                Date StartSlp = null;

                try {
                    DateSlp = simpleDateFormatD.parse(sleepDate);
                    StartSlp = simpleDateFormat.parse(sleepStart);
                    EndSlp = simpleDateFormat.parse(sleepEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean saved = mgr.insert_sleep(user_id, DateSlp, StartSlp, EndSlp, durSlp);
                if (saved == true){
                    Toast.makeText(SleepingActivity.this, "Sleeping Hours entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Landing view
                    Intent intent = new Intent(SleepingActivity.this, LandingView.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SleepingActivity.this, "Failed to save Sleeping Hours entries", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void CalculateDur(){

        // calculate duration of sleep
        if (!sleep_start.getText().toString().equals("")  && !sleep_end.getText().toString().equals("")  ) {

            String st = sleep_start.getText().toString();
            String [] parts = st.split(":");
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int time1 = h*60+m;

            String ed = sleep_end.getText().toString();
            parts = ed.split(":");
            h = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]);
            int time2 = h*60+m;

            int minutes = time2-time1;
            int Hours = (int) (minutes / 60);
            int Mins = (int) (minutes % 60);

            String diff = Hours + ":" + Mins; // updated value every1 second
            sleep_dur.setText(diff);
        }
    }


    // creating a calander dialog for physical date
    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sleep_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        }
    }


    private class startTimeDialogList implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            sleep_start.setText(hourOfDay + ":" + minute);
            CalculateDur();
        }
    }

    private class endTimeDialogList implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            sleep_end.setText(hourOfDay + ":" + minute);
            CalculateDur();
        }
    }
}
