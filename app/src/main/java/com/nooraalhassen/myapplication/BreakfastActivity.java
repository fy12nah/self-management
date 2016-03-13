package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BreakfastActivity extends AppCompatActivity {
    EditText bf_Time;
    RelativeLayout layout;
    EditText bf_item;
    ArrayList<EditText> edittexts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bf_Time = (EditText) findViewById(R.id.BFTime);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText breakfast = (EditText) findViewById(R.id.BFName);
        final EditText time_edittext = (EditText) findViewById(R.id.BFTime);

        final Button save_bf = (Button) findViewById(R.id.BFSave);
        final ImageView add_bf = (ImageView) findViewById(R.id.addBF);

        layout = (RelativeLayout) findViewById(R.id.breakfastLayout);
        bf_item = (EditText) findViewById(R.id.itemBF);
        edittexts.add(bf_item);


        // date calander dialog
        final ImageView time_dialog = (ImageView) findViewById(R.id.Bf_timeDialog);
        time_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(BreakfastActivity.this, new TimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });


        // if + image is clicked
        add_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText item1 = new EditText(BreakfastActivity.this);
                item1.setWidth(100);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_LEFT, R.id.itemBF);
                params.addRule(RelativeLayout.BELOW, R.id.itemBF);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);


                layout.addView(item1, params);
                edittexts.add(item1);

            }
        });

        // if save button is clicked, entries must be saved into database
        save_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(BreakfastActivity.this);

                // getting edittext values
                String bf_name = breakfast.getText().toString();
                String bfTime = time_edittext.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_TimePattern);

                Date TimeBF = null;

                try {
                    TimeBF = simpleDateFormat.parse(bfTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                ArrayList<String> items = new ArrayList<String>();
                for (EditText et: edittexts){
                    items.add(et.getText().toString());
                }

                boolean saved = mgr.insert_breakfast(bf_name, TimeBF, items);
                if (saved == true){
                    Toast.makeText(BreakfastActivity.this, "Breakfast entries are saved", Toast.LENGTH_LONG).show();

                    // go to another view - Meals view
                    Intent intent = new Intent(BreakfastActivity.this, Meals.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(BreakfastActivity.this, "Failed to save Breakfast entries", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            bf_Time.setText(hourOfDay + ":" + minute);
        }
    }

}
