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
import android.util.Log;
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

public class ExercisesActivity extends AppCompatActivity {

    long user_id;
    EditText exer_date;
    EditText exer_end;
    EditText exer_start;
    TextView exer_dur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exer_date = (EditText) findViewById(R.id.exer_date);
        exer_start = (EditText) findViewById(R.id.exer_start);
        exer_end = (EditText) findViewById(R.id.exer_end);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);

        ImageView dateDialog = (ImageView) findViewById(R.id.exdateDialog);
        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(ExercisesActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });

        // time picker dialog
        final ImageView Stime_dialog = (ImageView) findViewById(R.id.sExer_Dialog);
        Stime_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(ExercisesActivity.this, new startTimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });


        // time picker dialog
        final ImageView Etime_dialog = (ImageView) findViewById(R.id.eExer_Dialog);
        Etime_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(ExercisesActivity.this, new endTimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });


        // defining widgets for use
        final EditText exer_type = (EditText) findViewById(R.id.exerType);
        final EditText exer_date = (EditText) findViewById(R.id.exer_date);
        final EditText exer_start = (EditText) findViewById(R.id.exer_start);
        final EditText exer_end = (EditText) findViewById(R.id.exer_end);
        final Button save_exer = (Button) findViewById(R.id.exerSave);
        exer_dur = (TextView) findViewById(R.id.exerDur);



        save_exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(ExercisesActivity.this);

                // getting edittext values
                String exerType = exer_type.getText().toString();
                String exerDate = exer_date.getText().toString();
                String start_Exer = exer_start.getText().toString();
                String end_Exer = exer_end.getText().toString();
                String durExer = exer_dur.getText().toString();

                SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date DateExer = null;
                Date EndExer = null;
                Date StartExer = null;

                try {
                    DateExer = simpleDateFormatD.parse(exerDate);
                    StartExer = simpleDateFormat.parse(start_Exer);
                    EndExer = simpleDateFormat.parse(end_Exer);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean saved = mgr.insert_exer(user_id, exerType, DateExer, StartExer, EndExer, durExer);
                if (saved == true) {
                    Toast.makeText(ExercisesActivity.this, "Exercises entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Landing view
                    Intent intent = new Intent(ExercisesActivity.this, LandingView.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ExercisesActivity.this, "Failed to save Exercises entries", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void CalculateDur(){

        // calculate duration of an exercise
        if (!exer_start.getText().toString().equals("")  && !exer_end.getText().toString().equals("")  ) {

            String st = exer_start.getText().toString();
            String [] parts = st.split(":");
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);

            int time1 = h*60+m;



            String ed = exer_end.getText().toString();
            parts = ed.split(":");
            h = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]);

            int time2 = h*60+m;

            int minutes = time2-time1;

            int Hours = (int) (minutes / 60);
            int Mins = (int) (minutes % 60);

            String diff = Hours + ":" + Mins; // updated value every1 second
            exer_dur.setText(diff);
        }
    }



    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            exer_date.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    }


    private class startTimeDialogList implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            exer_start.setText(hourOfDay + ":" + minute);
            CalculateDur();
        }
    }

    private class endTimeDialogList implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            exer_end.setText(hourOfDay + ":" + minute);
            CalculateDur();
        }
    }

}
