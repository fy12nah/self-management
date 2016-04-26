package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nooraalhassen.myapplication.adapters.ExerciseTypeAdapter;
import com.nooraalhassen.myapplication.adapters.MoodNameAdapter;
import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Exercise;
import com.nooraalhassen.myapplication.model.ExerciseType;
import com.nooraalhassen.myapplication.model.Sleeping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExercisesActivity extends AppCompatActivity {

    String activityMode;
    long user_id;
    EditText exer_date;
    EditText exer_end;
    EditText exer_start;
    TextView exer_dur;
    Spinner exer_type;
    final DBmanager mgr = new DBmanager(ExercisesActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        exer_date = (EditText) findViewById(R.id.exer_date);
        exer_start = (EditText) findViewById(R.id.exer_start);
        exer_end = (EditText) findViewById(R.id.exer_end);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);
        Intent intent = getIntent();

        ImageView dateDialog = (ImageView) findViewById(R.id.exdateDialog);
        dateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(ExercisesActivity.this, new dateDailogListener(), year, month, day);
                datePick.show();
            }
        });

        // time picker dialog
        final ImageView Stime_dialog = (ImageView) findViewById(R.id.sExer_Dialog);
        Stime_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(ExercisesActivity.this, new startTimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });


        // time picker dialog
        final ImageView Etime_dialog = (ImageView) findViewById(R.id.eExer_Dialog);
        Etime_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(ExercisesActivity.this, new endTimeDialogList(), hour, minute, true);
                timePick.show();
            }
        });


        // defining widgets for use
        exer_type = (Spinner) findViewById(R.id.exerType);
        populateExerType();

        final Button save_exer = (Button) findViewById(R.id.exerSave);
        exer_dur = (TextView) findViewById(R.id.exerDur);

        //final DBmanager mgr = new DBmanager(ExercisesActivity.this);
        final SimpleDateFormat simpleDateFormatD = new SimpleDateFormat(Constants.display_DatePattern);
        final long id = intent.getLongExtra(Constants.exerciseId, -1);
        final String dateString = intent.getStringExtra(Constants.exerciseDate);
        if (dateString != null){
            exer_date.setText(dateString);
        }


        save_exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting edittext values
                long exerTypeID = exer_type.getSelectedItemId();
                String exerDate = exer_date.getText().toString();
                String StartExer = exer_start.getText().toString();
                String EndExer = exer_end.getText().toString();
                String durExer = exer_dur.getText().toString();


                Date DateExer = null;
                try {
                    DateExer = simpleDateFormatD.parse(exerDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (activityMode.equals(Constants.addMode)) {

                    boolean saved = mgr.insert_exer(user_id, exerTypeID, DateExer, StartExer, EndExer, durExer);
                    if (saved == true) {
                        Toast.makeText(ExercisesActivity.this, "Exercises entries are saved", Toast.LENGTH_LONG).show();

                        // go to another view - Landing view
                        Intent intent = new Intent(ExercisesActivity.this, LandingView.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ExercisesActivity.this, "Failed to save Exercises entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updateExercise(user_id, exerTypeID, DateExer, StartExer, EndExer, durExer);
                    if (updated == true) {
                        Toast.makeText(ExercisesActivity.this, "Exercise entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ExercisesActivity.this, "Failed to update Exercise entries", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Exercise s = mgr.getExerciseByID(id, user_id);

            if (s != null){
                exer_date.setText(simpleDateFormatD.format(s.getExerDate()));
                exer_dur.setText(s.getExerDur());
                exer_start.setText(s.getsExerTime());
                exer_end.setText(s.geteExerTime());
                int count = exer_type.getCount();
                for (int i = 0; i < count; i++){
                    if (exer_type.getItemIdAtPosition(i) == s.getExerType().getExerTypeID()){
                        exer_type.setSelection(i);
                        break;
                    }
                }
            }
            else {
                Toast.makeText(ExercisesActivity.this, "Invalid Exercise ID", Toast.LENGTH_LONG).show();
            }
        }
        else activityMode = Constants.addMode;
    }


    public void populateExerType(){

        Cursor c = mgr.getExerciseTypeCursor(user_id);

        ExerciseTypeAdapter adapter = new ExerciseTypeAdapter(this, c, false);
        exer_type.setAdapter(adapter);

    }


    private void CalculateDur(){

        // calculate duration of an exercise
        if (!exer_start.getText().toString().equals("")  && !exer_end.getText().toString().equals("")  ) {

            String st = exer_start.getText().toString();
            String [] parts = st.split(":");
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);

            int time1 = h * 60 + m;

            String ed = exer_end.getText().toString();
            parts = ed.split(":");
            h = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]);

            int time2 = h*60+m;

            int minutes = time2 - time1;

            int Hours = (int) (minutes / 60);
            int Mins = (int) (minutes % 60);

            String diff = Hours + ":" + Mins; // updated value every1 second
            exer_dur.setText(diff);
        }
    }



    public static Intent createIntent(Context context) {
        return new Intent(context, ExercisesActivity.class);
    }

    public static Intent createIntentForEdit(Context context, long id) {
        Intent intent = new Intent(context, ExercisesActivity.class);
        intent.putExtra(Constants.exerciseId, id);
        return intent;
    }


    public static Intent createIntent(Context context, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
        Intent intent = new Intent(context, ExercisesActivity.class);
        intent.putExtra(Constants.exerciseDate, simpleDateFormat.format(date));
        return intent;
    }


    private class dateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            exer_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    }

    private class startTimeDialogList implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            exer_start.setText(hourOfDay + ":" + minute);
            CalculateDur();
        }
    }

    private class endTimeDialogList implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            exer_end.setText(hourOfDay + ":" + minute);
            CalculateDur();
        }
    }
}
