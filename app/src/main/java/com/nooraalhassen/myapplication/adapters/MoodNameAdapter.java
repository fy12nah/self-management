package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nooraalhassen.myapplication.model.DBmanager;


/**
 * Created by nooraalhassen
 */
public class MoodNameAdapter extends CursorAdapter{

    LayoutInflater inflator;

    public MoodNameAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.inflator =  (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return inflator.inflate(android.R.layout.simple_spinner_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        int index = cursor.getColumnIndex(DBmanager.MoodNameTable.Col_moodName);
        tv.setText(cursor.getString(index));
    }
}
