package com.nooraalhassen.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nooraalhassen.myapplication.model.DBmanager;

public class MainActivity extends AppCompatActivity {

    //DBmanager helper = new DBmanager(this);
    Button btnlogin;
    Button btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        long user_id = preferences.getLong(Constants.userId, -1);


        if (user_id == -1) {

            // buttons declaration
            btnlogin = (Button) findViewById(R.id.buttonLog);
            btnSignup = (Button) findViewById(R.id.buttonSign);

            // login button action
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText username = (EditText) findViewById(R.id.signName);
                    EditText pass = (EditText) findViewById(R.id.signPass);

                    String checkUser = username.getText().toString();
                    String checkPass = pass.getText().toString();

                    DBmanager manager = new DBmanager(MainActivity.this);

                    if (checkUser.equals("") || checkPass.equals("")) {
                        Toast.makeText(MainActivity.this, "Please Complete all fields", Toast.LENGTH_LONG).show();
                    }
                    else {

                        // if username and password do match in database, then user is a member
                        long id = manager.authenticate(checkUser, checkPass);
                        if (id != -1) {

                            // save user id in shared preferences for multi user environment
                            SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putLong(Constants.userId, id);
                            editor.commit();

                            // go to another view - landing view
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, LandingView.class);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();

                    }
                }
            });

            // signup button action
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go to another view - signup
                    Intent intent = new Intent(MainActivity.this, SignUp.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(MainActivity.this, LandingView.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


