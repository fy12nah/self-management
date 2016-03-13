package com.nooraalhassen.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepingActivity extends AppCompatActivity {

    EditText sleep_start;
    EditText sleep_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sleep_start = (EditText) findViewById(R.id.sleepStart);
        sleep_end = (EditText) findViewById(R.id.sleepEnd);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText sTime = (EditText) findViewById(R.id.sleepStart);
        final EditText eTime = (EditText) findViewById(R.id.sleepEnd);
        final ImageView add_sleep = (ImageView) findViewById(R.id.addSleep);
        final Button save_sleep = (Button) findViewById(R.id.sleepSave);
        final TextView sleep_dur = (TextView) findViewById(R.id.sleepDur);


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


        add_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        if (sleep_start != null && sleep_end != null){
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

            Date t1 = null;
            Date t2 = null;
            try {
                t1 = format.parse(String.valueOf(sleep_start));
                t2 = format.parse(String.valueOf(sleep_end));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            long mills = 0;
            if (t1 != null) {
                if (t2 != null) {
                    mills = t1.getTime() - t2.getTime();
                }
            }

            int Hours = (int) (mills / (1000 * 60 * 60));
            int Mins = (int) (mills % (1000 * 60 * 60));

            String diff = Hours + ":" + Mins; // updated value every1 second
            sleep_dur.setText(diff);
        }

        save_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(SleepingActivity.this);

                // getting edittext values
                String sleepStart = sTime.getText().toString();
                String sleepEnd = eTime.getText().toString();
                String durSlp = sleep_dur.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date EndSlp = null;
                Date StartSlp = null;

                try {
                    StartSlp = simpleDateFormat.parse(sleepStart);
                    EndSlp = simpleDateFormat.parse(sleepEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean saved = mgr.insert_sleep(StartSlp, EndSlp, durSlp);
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


    private class startTimeDialogList implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            sleep_start.setText(hourOfDay + ":" + minute);
        }
    }

    private class endTimeDialogList implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            sleep_end.setText(hourOfDay + ":" + minute);
        }
    }
}
