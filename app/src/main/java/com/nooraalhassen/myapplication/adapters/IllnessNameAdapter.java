package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.model.DBmanager;

/**
 * Created by nooraalhassen
 */
public class IllnessNameAdapter extends CursorAdapter {

    String illnesType;
    LayoutInflater inflator;
    public IllnessNameAdapter(Context context, Cursor c, boolean autoRequery, String illnessType) {
        super(context, c, autoRequery);
        this.inflator =  (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.illnesType = illnessType;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflator.inflate(android.R.layout.simple_spinner_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int index = -1;
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        if (illnesType.equals(Constants.ShortT)){
            index = cursor.getColumnIndex(DBmanager.STINameTable.Col_illnessName);
        }
        else {
            index = cursor.getColumnIndex(DBmanager.LTINameTable.Col_illnessName);
        }
        tv.setText(cursor.getString(index));
    }
}
