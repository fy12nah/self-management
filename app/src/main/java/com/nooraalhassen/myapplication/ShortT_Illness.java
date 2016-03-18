package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShortT_Illness extends AppCompatActivity {

    EditText ST_sdate, ST_edate;
    RelativeLayout layout;
    ArrayList<EditText> edittexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_t__illness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // to get current date
        ST_sdate = (EditText) findViewById(R.id.STI_sdate);
        ST_edate = (EditText) findViewById(R.id.STI_edate);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);


        // start date calendar dialog
        ImageView sdateDialog = (ImageView) findViewById(R.id.sDate_Dialog);
        sdateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(ShortT_Illness.this, new StartDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // end date calendar dialog
        ImageView edateDialog = (ImageView) findViewById(R.id.eDate_Dialog);
        edateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(ShortT_Illness.this, new EndDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        final EditText ST_name = (EditText) findViewById(R.id.shrtIll_name);
        final EditText sdate_edt = (EditText) findViewById(R.id.STI_sdate);
        final EditText edate_edt = (EditText) findViewById(R.id.STI_edate);
        final EditText ST_med = (EditText) findViewById(R.id.STI_med);

        edittexts.add(ST_med);
        layout = (RelativeLayout) findViewById(R.id.STLayout);

        ImageView add_med = (ImageView) findViewById(R.id.addMed);
        add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText item1 = new EditText(ShortT_Illness.this);
                item1.setWidth(100);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_LEFT, R.id.STI_med);
                params.addRule(RelativeLayout.BELOW, R.id.STI_med);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                layout.addView(item1, params);
                edittexts.add(item1);
            }
        });


        Button saveB = (Button) findViewById(R.id.STI_save);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(ShortT_Illness.this);

                // getting edittext values
                String SillnessName = ST_name.getText().toString();
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

                ArrayList<String> STmeds = new ArrayList<String>();
                for (EditText et: edittexts){
                    STmeds.add(et.getText().toString());
                }


                boolean saved = mgr.insert_STIllness(SillnessName, startDate, endDate, STmeds);
                if (saved == true){
                    Toast.makeText(ShortT_Illness.this, "Short-term Illness entries saved", Toast.LENGTH_LONG).show();

                    // go to another view - landing view
                    Intent intent = new Intent(ShortT_Illness.this, LandingView.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ShortT_Illness.this, "Failed to save short-term Illness entries", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    // creating a calander dialog illness start date
    private class StartDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ST_sdate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

    // creating a calander dialog illness end date
    private class EndDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ST_edate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

}
