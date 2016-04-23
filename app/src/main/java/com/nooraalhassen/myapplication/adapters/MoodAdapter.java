package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.MoodActivity;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.SleepingActivity;
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

        final Mood m = list.get(position);

        TextView moodName = (TextView) convertView.findViewById(R.id.mood_Name);
        TextView moodReason = (TextView) convertView.findViewById(R.id.moodR);
        TextView moodTime = (TextView) convertView.findViewById(R.id.mood_Time);

        Button editbtn = (Button) convertView.findViewById(R.id.moodEdit);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MoodActivity.createIntentForEdit(getContext(), m.getId());
                getContext().startActivity(intent);
            }
        });

        moodName.setText(String.valueOf(m.getMoodName()));
        moodReason.setText(String.valueOf(m.getMoodReason()));
        moodTime.setText(m.getMoodTime());

        return convertView;
    }

}
