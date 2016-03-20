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

public class SnacksActivity extends AppCompatActivity {


    long user_id;
    EditText snacks_date;
    EditText snacks_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);

        // to get current time
        snacks_date = (EditText) findViewById(R.id.snackDate);
        snacks_time = (EditText) findViewById(R.id.snackTime);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText snack = (EditText) findViewById(R.id.snackName);
        final EditText snacks_date = (EditText) findViewById(R.id.snackDate);
        final EditText snacks_time = (EditText) findViewById(R.id.snackTime);
        final Button save_snacks = (Button) findViewById(R.id.snacksSave);


        ImageView dateDialog = (ImageView) findViewById(R.id.snackDD);
        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(SnacksActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        final ImageView time_dialog = (ImageView) findViewById(R.id.snack_TimeD);
        time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(SnacksActivity.this, new TimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });



        save_snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(SnacksActivity.this);

                // getting edittext values
                String snack_name = snack.getText().toString();
                String snack_Date = snacks_date.getText().toString();
                String snack_Time = snacks_time.getText().toString();

                SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date DateSnack = null;
                Date TimeSnack = null;

                try {
                    DateSnack = simpleDateFormatD.parse(snack_Date);
                    TimeSnack = simpleDateFormat.parse(snack_Time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean saved = mgr.insert_snack(user_id, snack_name, DateSnack, TimeSnack);
                if (saved == true) {
                    Toast.makeText(SnacksActivity.this, "Snacks entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Meals view
                    Intent intent = new Intent(SnacksActivity.this, LandingView.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SnacksActivity.this, "Failed to save Snacks entries", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            snacks_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        }
    }


    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            snacks_time.setText(hourOfDay + ":" + minute);
        }
    }

}
