package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUp extends AppCompatActivity {

    EditText birthdate_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // defining widgets for use
        final EditText username_edittext = (EditText) findViewById(R.id.signUsername);
        final EditText name_edittext = (EditText) findViewById(R.id.signName);
        final EditText password_edittext = (EditText) findViewById(R.id.signPass);
        birthdate_edittext = (EditText) findViewById(R.id.signBirth);
        final RadioGroup gender_RG = (RadioGroup) findViewById(R.id.genderGroup);

        // calendar dialog for birthdate
        ImageView bddialog = (ImageView) findViewById(R.id.bdprof_dialog);
        bddialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(SignUp.this, new BirthDateDailogListener(), 0, 0, 0);
                datePick.show();
            }
        });


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

                // date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date birthdialog = null;

                try {
                    birthdialog = simpleDateFormat.parse(birthdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // way to save selected gender radio button
                char gender = 'M';
                int selectedRadioButton = gender_RG.getCheckedRadioButtonId();
                if (selectedRadioButton == R.id.signMale){
                    gender = 'M';
                }
                else if (selectedRadioButton == R.id.signFemale){
                    gender = 'F';
                }

                if (username_edittext == null || name_edittext == null || password_edittext == null || birthdate_edittext == null) {
                    Toast.makeText(SignUp.this, "Please Complete all fields", Toast.LENGTH_LONG).show();
                }

                // action when signup data are entered
                long id;
                id =  mgr.signup(username, name, password, birthdialog, gender);

                if (id != -1){

                    // save user id in shared preferences for multi user environment
                    SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong(Constants.userId, id);
                    editor.commit();

                    Toast.makeText(SignUp.this, "Signup is successful; please complete your profile", Toast.LENGTH_LONG).show();

                    // goes to profile view to complete profile entries
                    Intent intent = new Intent(SignUp.this, ProfileActivity.class);
                    startActivity(intent);
                }
                // gives user a failure msg
                else Toast.makeText(SignUp.this, "Failed to signup", Toast.LENGTH_LONG).show();

            }
        });
    }

    // creating a calander dialog for physical date
    private class BirthDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            birthdate_edittext.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    }
}

