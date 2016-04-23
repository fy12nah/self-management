package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.SignUp;
import com.nooraalhassen.myapplication.SleepingActivity;
import com.nooraalhassen.myapplication.model.Meal;
import com.nooraalhassen.myapplication.model.Sleeping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen
 */
public class SleepAdapter extends ArrayAdapter<Sleeping> {

    ArrayList<Sleeping> list;
    SimpleDateFormat simpleDateFormatT = new SimpleDateFormat(Constants.display_TimePattern);

    public SleepAdapter(Context context, List<Sleeping> items) {

        super(context, R.layout.sleeping_list_signle_item, items);
        list = (ArrayList<Sleeping>) items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.sleeping_list_signle_item, parent, false);
        }

        final Sleeping m = list.get(position);

        TextView sTimeText = (TextView) convertView.findViewById(R.id.sleepStrtTime);
        TextView eTimeText = (TextView) convertView.findViewById(R.id.slpEndTime);
        TextView slpMsg = (TextView) convertView.findViewById(R.id.msgSleep);

        Button editbtn = (Button) convertView.findViewById(R.id.slpEdit);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SleepingActivity.createIntentForEdit(getContext(), m.getId());
                getContext().startActivity(intent);
            }
        });


        sTimeText.setText(m.getsSleepTime());
        eTimeText.setText(m.geteSleepTime());
        slpMsg.setText("You Slept for " + m.getSleepDur());

        return convertView;
    }

}
