package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import com.nooraalhassen.myapplication.model.Exercise;
import com.nooraalhassen.myapplication.model.Meal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SnacksActivity extends AppCompatActivity {

    String activityMode;
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
        Intent intent = getIntent();


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

        final DBmanager mgr = new DBmanager(SnacksActivity.this);
        final long id = intent.getLongExtra(Constants.snackId, -1);
        final SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);


        save_snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting edittext values
                String snack_name = snack.getText().toString();
                String snack_Date = snacks_date.getText().toString();
                String TimeSnack = snacks_time.getText().toString();

                Date DateSnack = null;

                try {
                    DateSnack = simpleDateFormatD.parse(snack_Date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (activityMode.equals(Constants.addMode)) {
                    boolean saved = mgr.insert_meal(user_id, Constants.snack, snack_name, DateSnack, TimeSnack, null);
                    if (saved == true) {
                        Toast.makeText(SnacksActivity.this, "Snacks entries are saved", Toast.LENGTH_LONG).show();

                        // go to another view - Meals view
                        Intent intent = new Intent(SnacksActivity.this, LandingView.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SnacksActivity.this, "Failed to save Snacks entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updateMeal(user_id, Constants.snack, snack_name, DateSnack, TimeSnack, null);
                    if (updated == true) {
                        Toast.makeText(SnacksActivity.this, "Meals entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SnacksActivity.this, "Failed to update Meals entries", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Meal s = mgr.getMealByID(id, user_id);

            if (s != null){
                snack.setText(s.getMealName());
                snacks_time.setText(s.getMealTime());
                snacks_date.setText(simpleDateFormatD.format(s.getMealDate()));
            }
            else {
                Toast.makeText(SnacksActivity.this, "Invalid Meal ID", Toast.LENGTH_LONG).show();
            }
        }
        else activityMode = Constants.addMode;
    }



    public static Intent createIntent(Context context) {
        return new Intent(context, SnacksActivity.class);
    }

    public static Intent createIntentForEdit(Context context, long id) {
        Intent intent = new Intent(context, SnacksActivity.class);
        intent.putExtra(Constants.snackId, id);
        return intent;
    }


    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            snacks_date.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    }


    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            snacks_time.setText(hourOfDay + ":" + minute);
        }
    }

}
