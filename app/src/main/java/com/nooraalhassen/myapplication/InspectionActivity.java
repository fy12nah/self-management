package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

public class InspectionActivity extends AppCompatActivity {

    EditText fromD, toD;
    long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);

        // to get current date
        fromD = (EditText) findViewById(R.id.fromD);
        toD = (EditText) findViewById(R.id.toD);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);


        // start date calendar dialog
        ImageView fromDialog = (ImageView) findViewById(R.id.fromDialog);
        fromDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(InspectionActivity.this, new fromDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // end date calendar dialog
        ImageView toDialog = (ImageView) findViewById(R.id.toDialog);
        toDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(InspectionActivity.this, new toDateDailogListener(), year, month, day);
                datePick.show();
            }
        });

        Button showbtn = (Button) findViewById(R.id.showB);
        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InspectionActivity.this, displayInspActivity.class);
                startActivity(intent);
            }
        });


    }


    // creating a calander dialog illness start date
    private class fromDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fromD.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

    // creating a calander dialog illness end date
    private class toDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            toD.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }
}
