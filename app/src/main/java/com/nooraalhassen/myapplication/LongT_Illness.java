package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LongT_Illness extends AppCompatActivity {

    long user_id;
    EditText LT_sdate, LT_edate;
    RelativeLayout layout;
    ArrayList<EditText> edittexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_t__illness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);

        // to get current date
        LT_sdate = (EditText) findViewById(R.id.LTI_sdate);
        LT_edate = (EditText) findViewById(R.id.LTI_edate);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);


        // start date calendar dialog
        ImageView sdateDialog = (ImageView) findViewById(R.id.LTsd_Dialog);
        sdateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(LongT_Illness.this, new StartDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // end date calendar dialog
        ImageView edateDialog = (ImageView) findViewById(R.id.LTed_Dialog);
        edateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(LongT_Illness.this, new EndDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        final EditText LT_name = (EditText) findViewById(R.id.longIll_name);
        final EditText sdate_edt = (EditText) findViewById(R.id.LTI_sdate);
        final EditText edate_edt = (EditText) findViewById(R.id.LTI_edate);
        final EditText LT_med = (EditText) findViewById(R.id.LTI_med);

        edittexts.add(LT_med);
        layout = (RelativeLayout) findViewById(R.id.LTLayout);


        ImageView add_med = (ImageView) findViewById(R.id.LTaddMed);
        add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText item1 = new EditText(LongT_Illness.this);
                item1.setWidth(100);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_LEFT, R.id.LTI_med);
                params.addRule(RelativeLayout.BELOW, R.id.LTI_med);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                layout.addView(item1, params);
                edittexts.add(item1);
            }
        });


        Button saveB = (Button) findViewById(R.id.LT_save);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(LongT_Illness.this);

                // getting edittext values
                String SillnessName = LT_name.getText().toString();
                String sIlldate = sdate_edt.getText().toString();
                String eIlldate = edate_edt.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);

                Date startDate = null;
                Date endDate = null;

                try {
                    startDate = simpleDateFormat.parse(sIlldate);
                    endDate = simpleDateFormat.parse(eIlldate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<String> LTmeds = new ArrayList<String>();
                for (EditText et: edittexts){
                    LTmeds.add(et.getText().toString());
                }


                boolean saved = mgr.insert_illness(user_id, Constants.LongT, SillnessName, startDate, endDate, LTmeds);
                if (saved == true){
                    Toast.makeText(LongT_Illness.this, "Long-term Illness entries saved", Toast.LENGTH_LONG).show();

                    // go to another view - landing view
                    Intent intent = new Intent(LongT_Illness.this, LandingView.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LongT_Illness.this, "Failed to save Long-term Illness entries", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // creating a calander dialog illness start date
    private class StartDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            LT_sdate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

    // creating a calander dialog illness end date
    private class EndDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            LT_edate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

}
