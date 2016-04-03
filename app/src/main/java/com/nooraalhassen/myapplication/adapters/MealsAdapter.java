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
import com.nooraalhassen.myapplication.model.MealItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen
 */
public class MealsAdapter extends ArrayAdapter<Meal> {

    ArrayList<Meal> list;
    SimpleDateFormat simpleDateFormatT = new SimpleDateFormat(Constants.display_TimePattern);

    public MealsAdapter(Context context, List<Meal> items) {

        super(context, R.layout.meals_items_list_single_item, items);
        list = (ArrayList<Meal>) items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.meals_list_signle_item, parent, false);
        }

        TextView type = (TextView) convertView.findViewById(R.id.tvMealType);
        TextView tv = (TextView) convertView.findViewById(R.id.tvMealItem);

        TextView mealName = (TextView) convertView.findViewById(R.id.mealName);
        ListView mealItemList = (ListView) convertView.findViewById(R.id.mealItem);
        TextView mealtime = (TextView) convertView.findViewById(R.id.mealTime);


        Meal m = list.get(position);
            type.setText(String.valueOf(m.getMealType()));
            mealName.setText(String.valueOf(m.getMealName()));

            mealtime.setText(simpleDateFormatT.format(m.getMealTime()));

        if (!m.getMealType().equals(Constants.snack)) {
            MealItemsAdapter adapter = new MealItemsAdapter(getContext(),
                    m.getMealItems());
            mealItemList.setAdapter(adapter);
            tv.setVisibility(View.VISIBLE);
        }
        else {
            tv.setVisibility(View.GONE);
            mealItemList.setVisibility(View.GONE);
        }

        return convertView;
    }
}