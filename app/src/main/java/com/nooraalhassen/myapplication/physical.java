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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

public class physical extends AppCompatActivity {

    private EditText phys_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        phys_date = (EditText) findViewById(R.id.phys_date);

        // date calander dialog
        ImageView physd_dialog = (ImageView) findViewById(R.id.physicald_Dialog);
        physd_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(physical.this, new PhysdDailogListener(), 0, 0, 0);
            }
        });

        // defining widgets for use
        final EditText weight_edittext= (EditText) findViewById(R.id.weight);
        final EditText height_edittext= (EditText) findViewById(R.id.height);
        final EditText date_edittext= (EditText) findViewById(R.id.phys_date);

        // button action
        Button btn = (Button) findViewById(R.id.physical_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBmanager mgr = new DBmanager(physical.this);

                // getting edittext values
                String weight = weight_edittext.getText().toString();
                String height = height_edittext.getText().toString();
                String phys_date = date_edittext.getText().toString();

                // save data in db
                // gives Toast msg

            }
        });
    }


    // creating a calander dialog for physical date
    private class PhysdDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            phys_date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

}
