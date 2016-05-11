package com.nooraalhassen.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Exercise;
import com.nooraalhassen.myapplication.model.Illness;
import com.nooraalhassen.myapplication.model.Meal;
import com.nooraalhassen.myapplication.model.Mood;
import com.nooraalhassen.myapplication.model.Physical;
import com.nooraalhassen.myapplication.model.Profile;
import com.nooraalhassen.myapplication.model.Sleeping;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JsonTestActivity extends AppCompatActivity {

    EditText fromD, toD;
    Date date1, date2;

    CheckBox chkProf;
    CheckBox chkPhys;
    CheckBox chkMeals;
    CheckBox chkExer;
    CheckBox chkSleep;
    CheckBox chkMood;
    CheckBox chkIllness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fromD = (EditText) findViewById(R.id.fromD);
        toD = (EditText) findViewById(R.id.toD);
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);


        // from date calendar dialog
        ImageView fromDialog = (ImageView) findViewById(R.id.fromDia);
        fromDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(JsonTestActivity.this, new fromDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        // to date calendar dialog
        ImageView toDialog = (ImageView) findViewById(R.id.toDia);
        toDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(JsonTestActivity.this, new toDateDailogListener(), year, month, day);
                datePick.show();
            }
        });


        chkProf = (CheckBox) findViewById(R.id.downProf);
        chkPhys = (CheckBox) findViewById(R.id.downPhys);
        chkMeals = (CheckBox) findViewById(R.id.downMeals);
        chkExer = (CheckBox) findViewById(R.id.downExer);
        chkSleep = (CheckBox) findViewById(R.id.downSleep);
        chkMood = (CheckBox) findViewById(R.id.downMood);
        chkIllness = (CheckBox) findViewById(R.id.downIllness);



        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fromD == null || toD == null) Toast.makeText(JsonTestActivity.this, "You must choose your date range", Toast.LENGTH_LONG).show();
                else if (!chkProf.isChecked() && !chkPhys.isChecked() && !chkMeals.isChecked() && !chkMood.isChecked()
                        && !chkSleep.isChecked() && !chkIllness.isChecked() && chkExer.isChecked()) {
                    Toast.makeText(JsonTestActivity.this, "You must choose at least one category", Toast.LENGTH_LONG).show();
                }
                else new Task().execute();
            }
        });

    }


    private class Task extends AsyncTask<Void,Void,Uri> {

        boolean prof = false;
        boolean phys = false;
        boolean ill = false;
        boolean meal = false;
        boolean exe = false;
        boolean sleep = false;
        boolean mood = false;


        @Override
        protected void onPreExecute() {
            if (chkProf.isChecked()) {
                prof = true;
            }

            if (chkPhys.isChecked()) {
                phys = true;
            }

            if (chkIllness.isChecked()) {
                ill = true;
            }

            if (chkMeals.isChecked()) {
                meal = true;
            }

            if (chkMood.isChecked()) {
                mood = true;
            }

            if (chkExer.isChecked()) {
                exe = true;
            }

            if (chkSleep.isChecked()) {
                sleep = true;
            }
        }

        @Override
        protected Uri doInBackground(Void... params) {
            DBmanager db = new DBmanager(JsonTestActivity.this);

            SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
            long user_id = preferences.getLong(Constants.userId, -1);

            Profile p = db.getProfile(user_id);

            List<Physical> ph = db.getPhysicalInDateRange(date1, date2, user_id);
            List<Illness> i = db.getIllnessInDateRange(date1, date2, user_id);
            List<Meal> meals = db.getMealsInDateRange(date1, date2, user_id);
            List<Mood> m = db.getMoodInDateRange(date1, date2, user_id);
            List<Exercise> exer = db.getExerciseInDateRange(date1, date2, user_id);
            List<Sleeping> slp = db.getSleepingInDateRange(date1, date2, user_id);


            Gson gson = new Gson();
            String json = "{";
            if (prof) json += "\"Profile\":"+gson.toJson(p);
            if (phys) json += json.equals("") ? "" :","+ " \"Physical\":"+gson.toJson(ph);
            if (ill) json += json.equals("") ? "" :"," + " \"Illness\":"+gson.toJson(i);
            if (meal) json += json.equals("") ? "" :","+ " \"Meals\":"+gson.toJson(meals);
            if (mood) json += json.equals("") ? "" :","+" \"Mood Status\":"+gson.toJson(m);
            if (exe) json += json.equals("") ? "" :","+ " \"Exercise\":"+gson.toJson(exer);
            if (sleep) json += json.equals("") ? "" :","+ " \"Sleeping Hours\":"+gson.toJson(slp);
            json += "}";


            Uri mDirectoryPathname = Uri
                    .parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"
                            + "Self-Health-Manager" + "/");

           return  createDirectoryAndSaveFile(JsonTestActivity.this, json, "Model.txt", mDirectoryPathname);

        }

        @Override
        protected void onPostExecute(Uri aUri) {
            Toast.makeText(JsonTestActivity.this, "Your user model is successfully downloaded! to "
                    + aUri.toString(), Toast.LENGTH_LONG).show();

        }
    }





    private static Uri createDirectoryAndSaveFile(Context context,
                                                  String json,
                                                  String fileName,
                                                  Uri directoryPathname) {
        try {
            // Bail out of we get an invalid bitmap.
            if (json == null)
                return null;

            // Bail if the fileName is null as well.
            if (fileName == null) {
                return null;
            }

            // Create a directory path.
            File directoryPath =
                    new File(directoryPathname.toString());

            // If the directory doesn't exist already then create it.
            if (!directoryPath.exists())
                directoryPath.mkdirs();

            // Create a filePath within the directoryPath.
            File filePath =
                    new File(directoryPath,
                            fileName);

            // Delete the file if it already exists.
            if (filePath.exists())
                filePath.delete();


            // Get the content of the resource at the url and save it
            // to an output file.
            try (InputStream is = new ByteArrayInputStream(json.getBytes());
                 OutputStream os = new FileOutputStream(filePath)) {
                copyFile(is, os);
            } catch (Exception e) {
                return null; // Indicate a failure.
            }

            // Get the absolute path of the image.
            String absolutePathToFile = filePath.getAbsolutePath();

            Log.d("Noora",
                    "absolute path to image file is "
                            + absolutePathToFile);

            // Return the absolute path to the image file.
            return Uri.parse(absolutePathToFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copyFile(InputStream inputStream,
                                 OutputStream outputStream)
            throws IOException {
        byte[] buffer = new byte[1024];

        for (int n; (n = inputStream.read(buffer)) >= 0; )
            outputStream.write(buffer, 0, n);

        outputStream.flush();
    }

    // creating a calander dialog from date
    private class fromDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fromD.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            date1 = c.getTime();
        }
    }

    // creating a calander dialog illness end date
    private class toDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            toD.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            date2 = c.getTime();
        }
    }
}


