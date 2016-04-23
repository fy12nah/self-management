package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.ExercisesActivity;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.SleepingActivity;
import com.nooraalhassen.myapplication.model.Exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen
 */
public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    ArrayList<Exercise> list;

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

        final Exercise m = list.get(position);

        TextView type = (TextView) convertView.findViewById(R.id.exer_Type);
        TextView exerSTime = (TextView) convertView.findViewById(R.id.exerSTime);
        TextView exerETime = (TextView) convertView.findViewById(R.id.exerETime);
        TextView durMsg = (TextView) convertView.findViewById(R.id.exerMsg);

        Button editbtn = (Button) convertView.findViewById(R.id.exerEdit);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ExercisesActivity.createIntentForEdit(getContext(), m.getId());
                getContext().startActivity(intent);
            }
        });

        type.setText(String.valueOf(m.getExerType()));
        Log.d("Noora", String.valueOf(m.getExerType())+m.getExerDate().toString());
        exerSTime.setText(m.getsExerTime());
        exerETime.setText(m.geteExerTime());
        durMsg.setText("This Exercise Last for "+ m.getExerDur());


        return convertView;
    }
}
