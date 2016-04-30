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
import com.nooraalhassen.myapplication.model.DBmanager;
import com.nooraalhassen.myapplication.model.Illness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LongT_Illness extends AppCompatActivity {

    String activityMode;
    long user_id;
    Spinner LT_name;
    EditText LT_sdate, LT_edate;
    RelativeLayout layout;
    int newId = -1;
    ArrayList<EditText> edittexts = new ArrayList<>();
    final DBmanager mgr = new DBmanager(LongT_Illness.this);
    AlertDialog dialog;
    AlertDialog.Builder builder = null;
    LayoutInflater inflater = null;
    View view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_t__illness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences(Constants.sharedpreferencesId, 0);
        user_id = preferences.getLong(Constants.userId, -1);
        Intent intent = getIntent();

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


        // end date calendar dialog
        ImageView addIllness = (ImageView) findViewById(R.id.addLTName);
        addIllness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });


        LT_name = (Spinner) findViewById(R.id.longIll_name);
        populateIllnessName();

        final EditText sdate_edt = (EditText) findViewById(R.id.LTI_sdate);
        final EditText edate_edt = (EditText) findViewById(R.id.LTI_edate);
        final EditText LT_med = (EditText) findViewById(R.id.LTI_med);

        edittexts.add(LT_med);
        layout = (RelativeLayout) findViewById(R.id.LTLayout);


        ImageView add_med = (ImageView) findViewById(R.id.LTaddMed);
        add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addField();
            }
        });


        final DBmanager mgr = new DBmanager(LongT_Illness.this);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);
        final long id = intent.getLongExtra(Constants.longIllness_Id, -1);

        builder = new AlertDialog.Builder(this);
        inflater = LongT_Illness.this.getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_layout, null);


        Button saveB = (Button) findViewById(R.id.LT_save);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting edittext values
                long illnessNameID = LT_name.getSelectedItemId();
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

                ArrayList<String> LTmeds = new ArrayList<String>();
                for (EditText et: edittexts){
                    LTmeds.add(et.getText().toString());
                }


                if (activityMode.equals(Constants.addMode)) {
                    boolean saved = mgr.insert_illness(user_id, Constants.LongT, illnessNameID, startDate, endDate, LTmeds);
                    if (saved == true) {
                        Toast.makeText(LongT_Illness.this, "Long-term Illness entries saved", Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Toast.makeText(LongT_Illness.this, "Failed to save Long-term Illness entries", Toast.LENGTH_LONG).show();
                    }
                }
                else if (activityMode.equals(Constants.EditMode)){
                    boolean updated = mgr.updateIllness(user_id, Constants.LongT, illnessNameID, startDate, endDate, LTmeds);
                    if (updated == true) {
                        Toast.makeText(LongT_Illness.this, "Long-term Illness entries are updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(LongT_Illness.this, "Failed to update Long-term Illness entries", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (id != -1){
            activityMode = Constants.EditMode;
            Illness s = mgr.getIllnessByID(id, user_id);

            if (s != null){
                LT_sdate.setText(simpleDateFormat.format(s.getsIllnessDate()));
                LT_edate.setText(simpleDateFormat.format(s.geteIllnessDate()));
                int count = LT_name.getCount();
                for (int i = 0; i < count; i++){
                    if (LT_name.getItemIdAtPosition(i) == s.getIllnessName().getIllnessNameID()){
                        LT_name.setSelection(i);
                        break;
                    }
                }
                LT_med.setText(s.getMedsList().get(0).getIllnessMed());
                for (int i = 1; i < s.getMedsList().size(); i++) {
                    EditText newText = addField();
                    newText.setText(s.getMedsList().get(i).getIllnessMed());
                }            }
            else {
                Toast.makeText(LongT_Illness.this, "Invalid Long-Term Illness ID", Toast.LENGTH_LONG).show();
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

        Cursor c = mgr.getLTINameCursor(user_id);
        IllnessNameAdapter adapter = new IllnessNameAdapter(this, c, false, Constants.LongT);
        LT_name.setAdapter(adapter);

    }


    public EditText addField(){

        EditText item1 = new EditText(LongT_Illness.this);
        item1.setWidth(100);

        if (newId == -1){
            newId = R.id.LTI_med;
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT, R.id.LTI_med);
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
        Intent intent = new Intent(context, LongT_Illness.class);
        intent.putExtra(Constants.longIllness_Id, id);
        return intent;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, LongT_Illness.class);
    }


    // creating a calander dialog illness start date
    private class StartDateDailogListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            LT_sdate.setText(dayOfMonth+"/"+ (monthOfYear+1) +"/"+year);
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
