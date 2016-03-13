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

public class ExercisesActivity extends AppCompatActivity {

    EditText exer_end;
    EditText exer_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exer_start = (EditText) findViewById(R.id.exer_start);
        exer_end = (EditText) findViewById(R.id.exer_end);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


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
        final EditText exer_start = (EditText) findViewById(R.id.exer_start);
        final EditText exer_end = (EditText) findViewById(R.id.exer_end);
        final ImageView add_exer = (ImageView) findViewById(R.id.addExer);
        final Button save_exer = (Button) findViewById(R.id.exerSave);
        final TextView exer_dur = (TextView) findViewById(R.id.exerDur);

        add_exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        // calculate duration of an exercise
        if (exer_start != null && exer_end != null) {
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

            Date t1 = null;
            Date t2 = null;
            try {
                t1 = format.parse(String.valueOf(exer_start));
                t2 = format.parse(String.valueOf(exer_end));
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
            exer_dur.setText(diff);
        }


        save_exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(ExercisesActivity.this);

                // getting edittext values
                String exerType = exer_type.getText().toString();
                String start_Exer = exer_start.getText().toString();
                String end_Exer = exer_end.getText().toString();
                String durExer = exer_dur.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date EndExer = null;
                Date StartExer = null;

                try {
                    StartExer = simpleDateFormat.parse(start_Exer);
                    EndExer = simpleDateFormat.parse(end_Exer);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean saved = mgr.insert_exer(exerType, StartExer, EndExer, durExer);
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


    private class startTimeDialogList implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            exer_start.setText(hourOfDay + ":" + minute);
        }
    }

    private class endTimeDialogList implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            exer_end.setText(hourOfDay + ":" + minute);
        }
    }

}
