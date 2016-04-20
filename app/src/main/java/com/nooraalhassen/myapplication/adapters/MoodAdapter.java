package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.model.Mood;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen
 */
public class MoodAdapter extends ArrayAdapter<Mood> {

    ArrayList<Mood> list;
    SimpleDateFormat simpleDateFormatT = new SimpleDateFormat(Constants.display_TimePattern);

    public MoodAdapter(Context context, List<Mood> items) {

        super(context, R.layout.mood_list_signle_item, items);
        list = (ArrayList<Mood>) items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.mood_list_signle_item, parent, false);
        }

        TextView moodName = (TextView) convertView.findViewById(R.id.mood_Name);
        TextView moodReason = (TextView) convertView.findViewById(R.id.moodR);
        TextView moodTime = (TextView) convertView.findViewById(R.id.mood_Time);

        Mood m = list.get(position);
        moodName.setText(String.valueOf(m.getMoodName()));
        moodReason.setText(String.valueOf(m.getMoodReason()));
        moodTime.setText(m.getMoodTime());

        return convertView;
    }

}
