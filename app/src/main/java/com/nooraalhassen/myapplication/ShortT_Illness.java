package com.nooraalhassen.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nooraalhassen.myapplication.adapters.IllnessNameAdapter;
import com.nooraalhassen.myapplication.adapters.MoodNameAdapter;
import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Exercise;
import com.nooraalhassen.myapplication.model.Illness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShortT_Illness extends AppCompatActivity {

    String activityMode;
    long user_id;
    EditText ST_sdate, ST_edate;
    Spinner ST_name;
    RelativeLayout layout;
    int newId = -1;
    ArrayList<EditText> edittexts = new ArrayList<>();
    final DBmanager mgr = new DBmanager(ShortT_Illness.this);
    AlertDialog dialog;
    AlertDialog.Builder builder = null;
    LayoutInflater inflater = null;
    View view = null;

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


        // end date calendar dialog
        ImageView addIllness = (ImageView) findViewById(R.id.addSTName);
        addIllness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);
        Intent intent = getIntent();

        builder = new AlertDialog.Builder(this);
        inflater = ShortT_Illness.this.getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_layout, null);


        ST_name = (Spinner) findViewById(R.id.shrtIll_name);
        populateIllnessName();

        final EditText sdate_edt = (EditText) findViewById(R.id.STI_sdate);
        final EditText edate_edt = (EditText) findViewById(R.id.STI_edate);
        final EditText ST_med = (EditText) findViewById(R.id.STI_med);

        edittexts.add(ST_med);
        layout = (RelativeLayout) findViewById(R.id.STLayout);

        ImageView add_med = (ImageView) findViewById(R.id.addMed);
        add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addField();
            }
        });

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
        final long id = intent.getLongExtra(Constants.shortIllness_Id, -1);
        final String dateString = intent.getStringExtra(Constants.exerciseDate);
        if (dateString != null){
            sdate_edt.setText(dateString);
        }


        Button saveB = (Button) findViewById(R.id.STI_save);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting edittext values
                long STIName = ST_name.getSelectedItemId();;
                String sIlldate = sdate_edt.getText().toString();
                String eIlldate = edate_edt.getText().toString();

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


                if (activityMode.equals(Constants.addMode)) {
                    boolean saved = mgr.insert_illness(user_id, Constants.ShortT, STIName, startDate, endDate, STmeds);
                    if (saved == true) {
                        Toast.makeText(ShortT_Illness.this, "Short-term Illness entries saved", Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Toast.makeText(ShortT_Illness.this, "Failed to save short-term Illness entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updateIllness(user_id, Constants.ShortT, STIName, startDate, endDate, STmeds);
                    if (updated == true) {
                        Toast.makeText(ShortT_Illness.this, "Short-term Illness entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ShortT_Illness.this, "Failed to update Short-term Illness entries", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Illness s = mgr.getIllnessByID(id, user_id);

            if (s != null) {
                ST_sdate.setText(simpleDateFormat.format(s.getsIllnessDate()));
                ST_edate.setText(simpleDateFormat.format(s.geteIllnessDate()));
                int count = ST_name.getCount();
                for (int i = 0; i < count; i++){
                    if (ST_name.getItemIdAtPosition(i) == s.getIllnessName().getIllnessNameID()){
                        ST_name.setSelection(i);
                        break;
                    }
                }
                ST_med.setText(s.getMedsList().get(0).getIllnessMed());
                for (int i = 1; i < s.getMedsList().size(); i++) {
                    EditText newText = addField();
                    newText.setText(s.getMedsList().get(i).getIllnessMed());
                }
            }

            else {
                Toast.makeText(ShortT_Illness.this, "Invalid Short-Term Illness ID", Toast.LENGTH_LONG).show();
            }
        }
        else activityMode = Constants.addMode;


        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText) view.findViewById(R.id.editText);
                        mgr.insert_STI(user_id, editText.getText().toString());
                        populateIllnessName();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        dialog = builder.create();

    }


    public void populateIllnessName(){

        Cursor c = mgr.getSTINameCursor(user_id);
        IllnessNameAdapter adapter = new IllnessNameAdapter(this, c, false, Constants.ShortT);
        ST_name.setAdapter(adapter);
    }


    public EditText addField(){

        EditText item1 = new EditText(ShortT_Illness.this);
        item1.setWidth(100);

        if (newId == -1){
            newId = R.id.STI_med;
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT, R.id.STI_med);
        params.addRule(RelativeLayout.BELOW, newId);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        while (findViewById(newId) != null){
            newId++;
        }
        item1.setId(newId);

        layout.addView(item1, params);
        edittexts.add(item1);

        return item1;
    }



    public static Intent createIntentForEdit(Context context, long id) {
        Intent intent = new Intent(context, ShortT_Illness.class);
        intent.putExtra(Constants.shortIllness_Id, id);
        return intent;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, ShortT_Illness.class);
    }



    // creating a calander dialog illness start date
    private class StartDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ST_sdate.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
        }
    }

    // creating a calander dialog illness end date
    private class EndDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ST_edate.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
        }
    }

}
