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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Meal;
import com.nooraalhassen.myapplication.model.MealItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LunchActivity extends AppCompatActivity {

    String activityMode;
    long user_id;
    EditText lunch_date;
    EditText lnch_time;
    RelativeLayout layout;
    EditText lunch_item;
    ArrayList<EditText> edittexts = new ArrayList<>();
    int newId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);
        Intent intent = getIntent();


        // to get current time
        lunch_date = (EditText) findViewById(R.id.lunch_date);
        lnch_time = (EditText) findViewById(R.id.lunchTime);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        // defining widgets for use
        final EditText lunch = (EditText) findViewById(R.id.lunchName);
        final EditText lunch_date = (EditText) findViewById(R.id.lunch_date);
        final EditText lnch_time = (EditText) findViewById(R.id.lunchTime);

        final ImageView add_lnch = (ImageView) findViewById(R.id.addLunch);
        final Button save_lnch = (Button) findViewById(R.id.lunchSave);

        lunch_item = (EditText) findViewById(R.id.itemLunch);
        edittexts.add(lunch_item);
        layout = (RelativeLayout) findViewById(R.id.lunchLayout);


        ImageView dateDialog = (ImageView) findViewById(R.id.lunchDD);
        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(LunchActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // time calander dialog
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

                addField();
            }
        });

        final DBmanager mgr = new DBmanager(LunchActivity.this);
        final long id = intent.getLongExtra(Constants.lunch_Id, -1);
        final SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);

        save_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting edittext values
                String lunch_name = lunch.getText().toString();
                String lunch_Date = lunch_date.getText().toString();
                String TimeLunch = lnch_time.getText().toString();

                Date DateLunch = null;
                try {
                    DateLunch = simpleDateFormatD.parse(lunch_Date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<String> items = new ArrayList<String>();
                for (EditText et: edittexts){
                    items.add(et.getText().toString());
                }


                if (activityMode.equals(Constants.addMode)) {
                    boolean saved = mgr.insert_meal(user_id, Constants.Lunch, lunch_name, DateLunch, TimeLunch, items);
                    if (saved == true) {
                        Toast.makeText(LunchActivity.this, "Lunch entries are saved", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(LunchActivity.this, "Failed to save Lunch entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updateMeal(user_id, Constants.Lunch, lunch_name, DateLunch, TimeLunch, items);
                    if (updated == true) {
                        Toast.makeText(LunchActivity.this, "Lunch entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(LunchActivity.this, "Lunch to update Dinner entries", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Meal s = mgr.getMealByID(id, user_id);

            if (s != null){
                lunch_date.setText(simpleDateFormatD.format(s.getMealDate()));
                lnch_time.setText(s.getMealTime());
                lunch.setText(s.getMealName());
                lunch_item.setText(s.getMealItems().get(0).getMealItem());
                for (int i = 1; i < s.getMealItems().size(); i++) {
                    EditText newText = addField();
                    newText.setText(s.getMealItems().get(i).getMealItem());
                }

            }
            else {
                Toast.makeText(LunchActivity.this, "Invalid Lunch ID", Toast.LENGTH_LONG).show();
            }
        }
        else activityMode = Constants.addMode;
    }

    public EditText addField(){

        EditText item1 = new EditText(LunchActivity.this);
        item1.setWidth(100);

        if (newId == -1){
            newId = R.id.itemLunch;
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT, R.id.itemLunch);
        params.addRule(RelativeLayout.BELOW, newId);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        while (findViewById(newId) != null){
            newId++;
        }
        item1.setId(newId);

        layout.addView(item1, params);
        edittexts.add(item1);

        return item1;
    }

    public static Intent createIntentForEdit(Context context, long id) {
        Intent intent = new Intent(context, LunchActivity.class);
        intent.putExtra(Constants.lunch_Id, id);
        return intent;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, LunchActivity.class);
    }


    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            lunch_date.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    }


    private class TimeDialogList implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            lnch_time.setText(hourOfDay + ":" + minute);
        }
    }
}
