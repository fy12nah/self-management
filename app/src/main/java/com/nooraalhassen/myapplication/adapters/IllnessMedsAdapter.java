package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.model.IllnessMed;
import com.nooraalhassen.myapplication.model.MealItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen
 */
public class IllnessMedsAdapter extends ArrayAdapter<IllnessMed> {

    ArrayList<IllnessMed> list;

    public IllnessMedsAdapter(Context context, List<IllnessMed> items) {

        super(context, R.layout.meds_items_list_single_item, items);
        list = (ArrayList<IllnessMed>) items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.meds_items_list_single_item, parent, false);
        }

        TextView medName = (TextView) convertView.findViewById(R.id.medname);
        medName.setText(list.get(position).getIllnessMed());
        return convertView;
    }

}
