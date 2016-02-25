package com.nooraalhassen.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.nooraalhassen.myapplication.model.DBmanager;

public class SignUp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // defining widgets for use
        final EditText username_edittext= (EditText) findViewById(R.id.signUsername);
        final EditText name_edittext= (EditText) findViewById(R.id.signName);
        final EditText password_edittext= (EditText) findViewById(R.id.signPass);
        final EditText birthdate_edittext= (EditText) findViewById(R.id.signBirth);
        final RadioGroup gender_RG = (RadioGroup) findViewById(R.id.genderGroup);

        // button action
        Button btn = (Button) findViewById(R.id.buttonSign);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBmanager mgr = new DBmanager(SignUp.this);

                // getting edittext values
                String username = username_edittext.getText().toString();
                String name = name_edittext.getText().toString();
                String password = password_edittext.getText().toString();
                String birthdate = birthdate_edittext.getText().toString();

                char gender = 'M';
                int selectedRadioButton = gender_RG.getCheckedRadioButtonId();
                if (selectedRadioButton == R.id.signMale){
                    gender = 'M';
                }
                else if (selectedRadioButton == R.id.signFemale){
                    gender = 'F';
                }

                mgr.signup(username, name, password, birthdate, gender);

            }
        });
    }

}
