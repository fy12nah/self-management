package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.model.Exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen on 4/3/16.
 */
public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    ArrayList<Exercise> list;
    SimpleDateFormat simpleDateFormatT = new SimpleDateFormat(Constants.display_TimePattern);

    public ExerciseAdapter(Context context, List<Exercise> items) {

        super(context, R.layout.exercises_list_signle_item, items);
        list = (ArrayList<Exercise>) items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.exercises_list_signle_item, parent, false);
        }

        TextView type = (TextView) convertView.findViewById(R.id.exerType);
        TextView exerSTime = (TextView) convertView.findViewById(R.id.exerSTime);
        TextView exerETime = (TextView) convertView.findViewById(R.id.exerETime);
        TextView durMsg = (TextView) convertView.findViewById(R.id.exerMsg);


        Exercise m = list.get(position);
        type.setText(String.valueOf(m.getExerType()));

        exerSTime.setText(simpleDateFormatT.format(m.getsExerTime()));
        exerETime.setText(simpleDateFormatT.format(m.geteExerTime()));
        durMsg.setText("This Exercise Last for "+ m.getExerDur());

        return convertView;
    }
}
