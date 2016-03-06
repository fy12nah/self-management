package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private EditText birthDText ;
    private EditText startDText;
    private EditText gradDText;
    ImageView profile_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // declaring widgets
        birthDText = (EditText) findViewById(R.id.birthD);
        startDText = (EditText) findViewById(R.id.start_study);
        gradDText = (EditText) findViewById(R.id.grad_study);
        profile_save = (ImageView) findViewById(R.id.profile_save);

        // autofill data entry
        DBmanager manager = new DBmanager(this);
        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        long id = preferences.getLong(Constants.userId, -1);
        if (id != -1){
            manager.getProfile(id);
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

        // declaring checkbox widgets
        CheckBox chkProfile = (CheckBox) findViewById(R.id.radioProf);
        CheckBox chkPhys = (CheckBox) findViewById(R.id.radioPhys);
        CheckBox chkMeals = (CheckBox) findViewById(R.id.radioMeals);
        CheckBox chkExer = (CheckBox) findViewById(R.id.radioExer);
        CheckBox chkSleep = (CheckBox) findViewById(R.id.radioSleep);
        CheckBox chkMood = (CheckBox) findViewById(R.id.radioMood);
        CheckBox chkIllness = (CheckBox) findViewById(R.id.radioIllness);

        // actions in checkbox are checked
        char profilecheck = 'Y';
        char physicalcheck = 'Y';
        char mealscheck = 'Y';
        char exercheck = 'Y';
        char moodcheck = 'Y';
        char sleepcheck = 'Y';
        char illnesscheck = 'Y';


        if (chkProfile.isChecked()){
            profilecheck = 'Y';
        } else profilecheck = 'N';

        if (chkPhys.isChecked()){
            physicalcheck = 'Y';
        } else physicalcheck = 'N';

        if (chkIllness.isChecked()){
            illnesscheck = 'Y';
        } else illnesscheck = 'N';

        if (chkMeals.isChecked()){
            mealscheck = 'Y';
        } else mealscheck = 'N';

        if (chkExer.isChecked()){
            exercheck = 'Y';
        } else exercheck = 'N';

        if (chkSleep.isChecked()){
            sleepcheck = 'Y';
        } else sleepcheck = 'N';

        if (chkMood.isChecked()){
            moodcheck = 'Y';
        } else moodcheck = 'N';

        // date format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
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


        // add var for checkbox
        DBmanager manager = new DBmanager(this);
        boolean update = manager.updateProfile(name, bd, gender, sd, gd, profilecheck, physicalcheck, illnesscheck,
                mealscheck, moodcheck, exercheck, sleepcheck);
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
            birthDText.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

    // creating a calander dialog for start study date
    private class sDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDText.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

    // creating a calander dialog for grad study date
    private class gDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            gradDText.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

}
