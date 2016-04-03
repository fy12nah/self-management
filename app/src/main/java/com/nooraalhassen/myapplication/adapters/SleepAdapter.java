package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.model.Meal;
import com.nooraalhassen.myapplication.model.Sleeping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen on 4/3/16.
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


        TextView sTimeText = (TextView) convertView.findViewById(R.id.sleepStrtTime);
        TextView eTimeText = (TextView) convertView.findViewById(R.id.slpEndTime);
        TextView slpMsg = (TextView) convertView.findViewById(R.id.sleepMsg);

        Sleeping m = list.get(position);
        sTimeText.setText(simpleDateFormatT.format(m.getsSleepTime()));
        eTimeText.setText(simpleDateFormatT.format(m.geteSleepTime()));

        slpMsg.setText("You Slept for " + m.getSleepDur());


        return convertView;
    }

}
