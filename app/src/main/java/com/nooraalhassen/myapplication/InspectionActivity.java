package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InspectionActivity extends AppCompatActivity {

    EditText fromD, toD;
    long user_id;
    Date fromDate, toDate;
    ArrayList<String> contentList = new ArrayList<>();

    CheckBox chkPhys;
    CheckBox chkMeals;
    CheckBox chkExer;
    CheckBox chkSleep;
    CheckBox chkMood;
    CheckBox chkIllness;

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

        chkPhys = (CheckBox) findViewById(R.id.inspPhys);
        chkMeals = (CheckBox) findViewById(R.id.inspMeals);
        chkExer = (CheckBox) findViewById(R.id.inspExer);
        chkSleep = (CheckBox) findViewById(R.id.inspSleep);
        chkMood = (CheckBox) findViewById(R.id.inspMood);
        chkIllness = (CheckBox) findViewById(R.id.inspIllness);


        chkPhys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                contentList.add(Constants.phys);
                else contentList.remove(Constants.phys);
            }
        });

        chkIllness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    contentList.add(Constants.ill);
                else contentList.remove(Constants.ill);            }
        });

        chkMeals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    contentList.add(Constants.meal);
                else contentList.remove(Constants.meal);            }
        });

        chkMood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    contentList.add(Constants.mood_s);
                else contentList.remove(Constants.mood_s);            }
        });

        chkExer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    contentList.add(Constants.exer);
                else contentList.remove(Constants.exer);            }
        });

        chkSleep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    contentList.add(Constants.sleep);
                else contentList.remove(Constants.sleep);            }
        });


        // from date calendar dialog
        ImageView fromDialog = (ImageView) findViewById(R.id.fromDialog);
        fromDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(InspectionActivity.this, new fromDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // to date calendar dialog
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);

                ArrayList<String> dateList = new ArrayList<String>();
                if (fromDate != null && toDate != null){
                    Calendar c = Calendar.getInstance();
                    c.setTime(fromDate);

                    for(Date d = fromDate; d.before(toDate); ){
                        //Log.d("Noora", d.toString());
                        dateList.add(simpleDateFormat.format(d));
                        c.add(Calendar.DAY_OF_MONTH , 1);
                        d = c.getTime();

                    }
                    dateList.add(simpleDateFormat.format(toDate));

                }

                if (contentList.isEmpty()) Toast.makeText(InspectionActivity.this, "You should choose at least one option",Toast.LENGTH_LONG).show();
                else {
                    Intent intent = DailyDisplayActivity.createIntent(InspectionActivity.this, dateList, contentList);
                    startActivity(intent);
                }

            }
        });


    }


    // creating a calander dialog from date
    private class fromDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fromD.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            fromDate = c.getTime();
        }
    }

    // creating a calander dialog illness end date
    private class toDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            toD.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            toDate = c.getTime();
        }
    }
}
