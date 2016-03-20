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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DinnerActivity extends AppCompatActivity {

    long user_id;
    EditText dinner_date;
    EditText dinner_time;
    RelativeLayout layout;
    EditText dinner_item;
    ArrayList<EditText> edittexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);

        // to get current time
        dinner_date = (EditText) findViewById(R.id.dinner_date);
        dinner_time = (EditText) findViewById(R.id.dinnerTime);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText dinner = (EditText) findViewById(R.id.dinnerName);
        final EditText dinner_date = (EditText) findViewById(R.id.dinner_date);
        final EditText dinner_time = (EditText) findViewById(R.id.dinnerTime);

        layout = (RelativeLayout) findViewById(R.id.dinnerLayout);
        dinner_item = (EditText) findViewById(R.id.itemDinner);
        edittexts.add(dinner_item);

        final ImageView add_dinner = (ImageView) findViewById(R.id.addDinner);
        final Button save_dinner = (Button) findViewById(R.id.dinnerSave);


        ImageView dateDialog = (ImageView) findViewById(R.id.dinnerDD);
        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(DinnerActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // time calander dialog
        final ImageView time_dialog = (ImageView) findViewById(R.id.dnr_TimeDialog);
        time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(DinnerActivity.this, new TimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });

        add_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText item1 = new EditText(DinnerActivity.this);
                item1.setWidth(100);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_LEFT, R.id.itemDinner);
                params.addRule(RelativeLayout.BELOW, R.id.itemDinner);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);


                layout.addView(item1, params);
                edittexts.add(item1);

            }
        });

        save_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(DinnerActivity.this);

                // getting edittext values
                String dinner_name = dinner.getText().toString();
                String dinner_Date = dinner_date.getText().toString();
                String dinner_Time = dinner_time.getText().toString();

                SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date DateDinner = null;
                Date TimeDinner = null;

                try {
                    DateDinner = simpleDateFormatD.parse(dinner_Date);
                    TimeDinner = simpleDateFormat.parse(dinner_Time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<String> items = new ArrayList<String>();
                for (EditText et: edittexts){
                    items.add(et.getText().toString());
                }

                boolean saved = mgr.insert_meal(user_id, Constants.Dinner, dinner_name, DateDinner, TimeDinner, items);
                if (saved == true) {
                    Toast.makeText(DinnerActivity.this, "Dinner entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Meals view
                    Intent intent = new Intent(DinnerActivity.this, Meals.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DinnerActivity.this, "Failed to save Dinner entries", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dinner_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
        }
    }


    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            dinner_time.setText(hourOfDay + ":" + minute);
        }
    }

}
