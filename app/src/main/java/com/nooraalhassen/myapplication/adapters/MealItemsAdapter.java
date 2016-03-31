package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.model.MealItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen on 3/31/16.
 */
public class MealItemsAdapter extends ArrayAdapter<MealItem>{

    ArrayList<MealItem> list;

    public MealItemsAdapter(Context context, List<MealItem> items) {

        super(context, R.layout.meals_items_list_single_item, items);
        list = (ArrayList<MealItem>) items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.meals_items_list_single_item, parent, false);
        }

        TextView itemName = (TextView) convertView.findViewById(R.id.itemname);
        itemName.setText(list.get(position).getMealItem());
        return convertView;
    }
}
