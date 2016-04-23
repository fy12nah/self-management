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
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Meal;
import com.nooraalhassen.myapplication.model.Sleeping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BreakfastActivity extends AppCompatActivity {

    String activityMode;
    long user_id;
    EditText bf_Date;
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

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);
        Intent intent = getIntent();


        bf_Date = (EditText) findViewById(R.id.bf_date);
        bf_Time = (EditText) findViewById(R.id.BFTime);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText breakfast = (EditText) findViewById(R.id.BFName);

        final Button save_bf = (Button) findViewById(R.id.BFSave);
        final ImageView add_bf = (ImageView) findViewById(R.id.addBF);

        layout = (RelativeLayout) findViewById(R.id.breakfastLayout);
        bf_item = (EditText) findViewById(R.id.itemBF);
        edittexts.add(bf_item);


        ImageView dateDialog = (ImageView) findViewById(R.id.bfDD);
        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(BreakfastActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });


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

        final DBmanager mgr = new DBmanager(BreakfastActivity.this);
        final long id = intent.getLongExtra(Constants.BF_Id, -1);
        final SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);


        // if save button is clicked, entries must be saved into database
        save_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // getting edittext values
                String bf_name = breakfast.getText().toString();
                String bfDate = bf_Date.getText().toString();
                String TimeBF = bf_Time.getText().toString();

                Date DateBF = null;

                try {
                    DateBF = simpleDateFormatD.parse(bfDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                ArrayList<String> items = new ArrayList<String>();
                for (EditText et: edittexts){
                    items.add(et.getText().toString());
                }

                if (activityMode.equals(Constants.addMode)) {
                    boolean saved = mgr.insert_meal(user_id, Constants.BF, bf_name, DateBF, TimeBF, items);
                    if (saved == true) {
                        Toast.makeText(BreakfastActivity.this, "Breakfast entries are saved", Toast.LENGTH_LONG).show();

                        // go to another view - Meals view
                        Intent intent = new Intent(BreakfastActivity.this, LandingView.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BreakfastActivity.this, "Failed to save Breakfast entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updateMeal(user_id, Constants.BF, bf_name, DateBF, TimeBF, items);
                    if (updated == true) {
                        Toast.makeText(BreakfastActivity.this, "Breakfast entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(BreakfastActivity.this, "Failed to update Breakfast entries", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Meal s = mgr.getMealByID(id, user_id);

            if (s != null){
                bf_Date.setText(simpleDateFormatD.format(s.getMealDate()));
                bf_Time.setText(s.getMealTime());
                //bf_item.setText((CharSequence) s.getMealItems());
                breakfast.setText(s.getMealName());
            }
            else {
                Toast.makeText(BreakfastActivity.this, "Invalid Breakfast ID", Toast.LENGTH_LONG).show();
            }
        }
        else activityMode = Constants.addMode;
    }


    public static Intent createIntent(Context context) {
        return new Intent(context, BreakfastActivity.class);
    }

    public static Intent createIntentForEdit(Context context, long id) {
        Intent intent = new Intent(context, BreakfastActivity.class);
        intent.putExtra(Constants.BF_Id, id);
        return intent;
    }


    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            bf_Date.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    }


    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            bf_Time.setText(hourOfDay + ":" + minute);
        }
    }

}
