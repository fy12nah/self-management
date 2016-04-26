package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Physical;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PhysicalActivity extends AppCompatActivity {

    String activityMode;
    EditText phys_date;
    long user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);
        Intent intent = getIntent();


        phys_date = (EditText) findViewById(R.id.phys_date);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        // date calander dialog
        ImageView physd_dialog = (ImageView) findViewById(R.id.physicald_Dialog);
        physd_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(PhysicalActivity.this, new PhysdDailogListener(), year, month, day);
                datePick.show();
            }
        });

        // defining widgets for use
        final EditText weight_edittext= (EditText) findViewById(R.id.weight);
        final EditText height_edittext= (EditText) findViewById(R.id.height);
        //final EditText date_edittext= (EditText) findViewById(R.id.phys_date);


        final long id = intent.getLongExtra(Constants.sleepingID, -1);
        final DBmanager mgr = new DBmanager(PhysicalActivity.this);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
        final String dateString = intent.getStringExtra(Constants.physicalDate);
        if (dateString != null){
            phys_date.setText(dateString);
        }

        // button action
        Button btn = (Button) findViewById(R.id.physical_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting edittext values
                String weight = weight_edittext.getText().toString();
                String height = height_edittext.getText().toString();
                String date = phys_date.getText().toString();


                Date physdate = null;

                try {
                    physdate = simpleDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (activityMode.equals(Constants.addMode)) {
                    boolean saved = mgr.insert_physical(user_id, weight, height, physdate);
                    if (saved == true) {
                        Toast.makeText(PhysicalActivity.this, "physical entries saved", Toast.LENGTH_LONG).show();

                        // go to another view - landing view
                        Intent intent = new Intent(PhysicalActivity.this, LandingView.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PhysicalActivity.this, "Failed to save physical entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updatePhysical(id, weight, height, physdate);
                    if (updated == true) {
                        Toast.makeText(PhysicalActivity.this, "Physical entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(PhysicalActivity.this, "Failed to update Physical entries", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Physical s = mgr.getPhysicalByID(id, user_id);

            if (s != null){
                phys_date.setText(simpleDateFormat.format(s.getDate()));
                weight_edittext.setText((int) s.getWeight());
                height_edittext.setText((int) s.getHeight());
            }
            else {
                Toast.makeText(PhysicalActivity.this, "Invalid Physical ID", Toast.LENGTH_LONG).show();
            }
        }
        else activityMode = Constants.addMode;
    }


    public static Intent createIntent(Context context) {
        return new Intent(context, PhysicalActivity.class);
    }

    public static Intent createIntentForEdit(Context context, long id) {
        Intent intent = new Intent(context, PhysicalActivity.class);
        intent.putExtra(Constants.physicalId, id);
        return intent;
    }

    public static Intent createIntent(Context context, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
        Intent intent = new Intent(context, PhysicalActivity.class);
        intent.putExtra(Constants.physicalDate, simpleDateFormat.format(date));
        return intent;
    }


    // creating a calander dialog for physical date
    private class PhysdDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            phys_date.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
        }
    }

}
