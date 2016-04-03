package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameText;

    private EditText uniText;
    private EditText birthDText ;
    private EditText startDText;
    private EditText gradDText;
    ImageView profile_save;
    Profile p = null;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    CheckBox chkProfile;
    CheckBox chkPhys;
    CheckBox chkMeals;
    CheckBox chkExer;
    CheckBox chkSleep;
    CheckBox chkMood;
    CheckBox chkIllness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // declaring widgets
        nameText = (EditText) findViewById(R.id.name);
        uniText = (EditText) findViewById(R.id.uni_name);
        birthDText = (EditText) findViewById(R.id.birthD);
        startDText = (EditText) findViewById(R.id.start_study);
        gradDText = (EditText) findViewById(R.id.grad_study);
        profile_save = (ImageView) findViewById(R.id.profile_save);
        maleRadio = (RadioButton) findViewById(R.id.radioMale);
        femaleRadio = (RadioButton) findViewById(R.id.radioFemale);

        chkProfile = (CheckBox) findViewById(R.id.radioProf);
        chkPhys = (CheckBox) findViewById(R.id.radioPhys);
        chkMeals = (CheckBox) findViewById(R.id.radioMeals);
        chkExer = (CheckBox) findViewById(R.id.radioExer);
        chkSleep = (CheckBox) findViewById(R.id.radioSleep);
        chkMood = (CheckBox) findViewById(R.id.radioMood);
        chkIllness = (CheckBox) findViewById(R.id.radioIllness);

        chkProfile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    p.getCategories().put("Profile", isChecked);
            }
        });

        chkPhys.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p.getCategories().put("Physical", isChecked);
            }
        });

        chkIllness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p.getCategories().put("Illness", isChecked);
            }
        });

        chkMeals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p.getCategories().put("Meals", isChecked);
            }
        });

        chkMood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p.getCategories().put("Mood Status", isChecked);
            }
        });

        chkExer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p.getCategories().put("Exercises", isChecked);
            }
        });

        chkSleep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                p.getCategories().put("Sleeping Hours", isChecked);
            }
        });

        // autofill data entry
        DBmanager manager = new DBmanager(this);
        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        long user_id = preferences.getLong(Constants.userId, -1);


        if (user_id != -1){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);

            // getting user's profile information
            // or autofill data after signing up
            p = manager.getProfile(user_id);

            nameText.setText(p.getName());
            uniText.setText(p.getUniname());

            if (p.getBirthdate() != null)
            birthDText.setText(simpleDateFormat.format(p.getBirthdate()));

            if (p.getStart_Study() != null)
            startDText.setText(simpleDateFormat.format(p.getStart_Study()));

            if (p.getGrad_Study() != null)
            gradDText.setText(simpleDateFormat.format(p.getGrad_Study()));


            if (p.getGender() == 'M'){
                maleRadio.setChecked(true);
            }
            else femaleRadio.setChecked(true);

            for (HashMap.Entry<String, Boolean> s: p.getCategories().entrySet()){
                switch (s.getKey()){
                    case Constants.pro:
                        if (s.getValue())
                        chkProfile.setChecked(true);
                        break;

                    case Constants.phys:
                        if (s.getValue())
                        chkPhys.setChecked(true);
                        break;

                    case Constants.ill:
                        if (s.getValue())
                        chkIllness.setChecked(true);
                        break;

                    case Constants.meal:
                        if (s.getValue())
                        chkMeals.setChecked(true);
                        break;

                    case Constants.mood_s:
                        if (s.getValue())
                        chkMood.setChecked(true);
                        break;

                    case Constants.exer:
                        if (s.getValue())
                        chkExer.setChecked(true);
                        break;

                    case Constants.sleep:
                        if (s.getValue())
                        chkSleep.setChecked(true);
                        break;
                }
            }

        }
        else Toast.makeText(this, "Try to login", Toast.LENGTH_LONG).show();


        // calendar dialog for birthdate when image is clicked
        ImageView bddialog = (ImageView) findViewById(R.id.bdDialog);
        bddialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(ProfileActivity.this, new BirthDateDailogListener(), 0, 0, 0);
                datePick.show();
            }
        });

        // calendar dialog for start date when image is clicked
        ImageView sddialog = (ImageView) findViewById(R.id.sdDialog);
        sddialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(ProfileActivity.this, new sDateDailogListener(),0,0,0);
                datePick.show();
            }
        });

        // calendar dialog for grad date
        ImageView gddialog = (ImageView) findViewById(R.id.gdDialog);
        gddialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(ProfileActivity.this, new gDateDailogListener(),0,0,0);
                datePick.show();
            }
        });



        // action when save button is clicked
        profile_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save and update profile
                saveprofile();

                // move to landing view
                Intent intent = new Intent(ProfileActivity.this, LandingView.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveprofile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // method of saving profile information into database
    private void saveprofile() {

        // declaring widgets
        EditText nameText = (EditText) findViewById(R.id.name);

        // getting values of widgets
        String name = nameText.getText().toString();
        String birthD = birthDText.getText().toString();
        String startD = startDText.getText().toString();
        String gradD = gradDText.getText().toString();

        // getting values of Gender radio group
        RadioGroup radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        char gender = 'M';
        int selectedRadioButton = radioGroupGender.getCheckedRadioButtonId();
        if (selectedRadioButton == R.id.radioMale){
            gender = 'M';
        }
        else if (selectedRadioButton == R.id.radioFemale){
            gender = 'F';
        }


        // date format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
        Date bd = null;
        Date sd = null;
        Date gd = null;

        try {
            bd = simpleDateFormat.parse(birthD);
            sd = simpleDateFormat.parse(startD);
            gd = simpleDateFormat.parse(gradD);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        p.setName(name);
        p.setBirthdate(bd);
        p.setGender(gender);
        p.setStart_Study(sd);
        p.setGrad_Study(gd);

        DBmanager manager = new DBmanager(this);

        // if profile is updated and saved
        boolean update = manager.updateProfile(p);
        if (update == true){
            Toast.makeText(this, "Profile updated", Toast.LENGTH_LONG).show();
        }
        else {
           Toast.makeText(this, "Profile failed to update", Toast.LENGTH_LONG).show();
        }

    }

    // creating a calander dialog for birthdate
    private class BirthDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            birthDText.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    }

    // creating a calander dialog for start study date
    private class sDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDText.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    }

    // creating a calander dialog for grad study date
    private class gDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            gradDText.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    }

}
