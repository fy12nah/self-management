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
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LunchActivity extends AppCompatActivity {

    EditText lnch_time;
    RelativeLayout layout;
    EditText lunch_item;
    ArrayList<EditText> edittexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // to get current time
        lnch_time = (EditText) findViewById(R.id.lunchTime);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText lunch = (EditText) findViewById(R.id.lunchName);
        final EditText lnch_time = (EditText) findViewById(R.id.lunchTime);

        final ImageView add_lnch = (ImageView) findViewById(R.id.addLunch);
        final Button save_lnch = (Button) findViewById(R.id.lunchSave);

        lunch_item = (EditText) findViewById(R.id.itemLunch);
        edittexts.add(lunch_item);
        layout = (RelativeLayout) findViewById(R.id.lunchLayout);


        // date calander dialog
        final ImageView time_dialog = (ImageView) findViewById(R.id.lnch_TimeDialog);
        time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(LunchActivity.this, new TimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });

        add_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText item1 = new EditText(LunchActivity.this);
                item1.setWidth(100);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_LEFT, R.id.itemLunch);
                params.addRule(RelativeLayout.BELOW, R.id.itemLunch);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                layout.addView(item1, params);
                edittexts.add(item1);
            }
        });

        save_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(LunchActivity.this);

                // getting edittext values
                String lunch_name = lunch.getText().toString();
                String lunch_Time = lnch_time.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date TimeLunch = null;

                try {
                    TimeLunch = simpleDateFormat.parse(lunch_Time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<String> items = new ArrayList<String>();
                for (EditText et: edittexts){
                    items.add(et.getText().toString());
                }


                boolean saved = mgr.insert_lunch(lunch_name, TimeLunch, items);
                if (saved == true) {
                    Toast.makeText(LunchActivity.this, "Lunch entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Meals view
                    Intent intent = new Intent(LunchActivity.this, Meals.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LunchActivity.this, "Failed to save Lunch entries", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            lnch_time.setText(hourOfDay + ":" + minute);
        }
    }
}
