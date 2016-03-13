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
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SnacksActivity extends AppCompatActivity {

    EditText snacks_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // to get current time
        snacks_time = (EditText) findViewById(R.id.snackTime);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText snack = (EditText) findViewById(R.id.snackName);
        final EditText snacks_time = (EditText) findViewById(R.id.snackTime);
        final ImageView add_snacks = (ImageView) findViewById(R.id.addSnack);
        final Button save_snacks = (Button) findViewById(R.id.snacksSave);


        // date calander dialog
        final ImageView time_dialog = (ImageView) findViewById(R.id.snack_TimeD);
        time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(SnacksActivity.this, new TimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });


        add_snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        save_snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(SnacksActivity.this);

                // getting edittext values
                String snack_name = snack.getText().toString();
                String snack_Time = snacks_time.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date TimeSnack = null;

                try {
                    TimeSnack = simpleDateFormat.parse(snack_Time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean saved = mgr.insert_snack(snack_name, TimeSnack);
                if (saved == true) {
                    Toast.makeText(SnacksActivity.this, "Snacks entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Meals view
                    Intent intent = new Intent(SnacksActivity.this, Meals.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SnacksActivity.this, "Failed to save Snacks entries", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            snacks_time.setText(hourOfDay + ":" + minute);
        }
    }

}
