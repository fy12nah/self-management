package com.nooraalhassen.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nooraalhassen.myapplication.BreakfastActivity;
import com.nooraalhassen.myapplication.Constants;
import com.nooraalhassen.myapplication.DinnerActivity;
import com.nooraalhassen.myapplication.LunchActivity;
import com.nooraalhassen.myapplication.MoodActivity;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.SnacksActivity;
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
        final Meal m = list.get(position);

        TextView type = (TextView) convertView.findViewById(R.id.tvMealType);
        TextView tv = (TextView) convertView.findViewById(R.id.tvMealItem);

        TextView mealName = (TextView) convertView.findViewById(R.id.mealName);
        ListView mealItemList = (ListView) convertView.findViewById(R.id.mealItem);
        TextView mealtime = (TextView) convertView.findViewById(R.id.mealTime);

        Button editbtn = (Button) convertView.findViewById(R.id.mealEdit);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.getMealType().equals(Constants.snack)){
                    Intent intent = SnacksActivity.createIntentForEdit(getContext(), m.getId());
                    getContext().startActivity(intent);
                }
                else if (m.getMealType().equals(Constants.BF)){
                    Intent intent = BreakfastActivity.createIntentForEdit(getContext(), m.getId());
                    getContext().startActivity(intent);
                }
                else if (m.getMealType().equals(Constants.Lunch)){
                    Intent intent = LunchActivity.createIntentForEdit(getContext(), m.getId());
                    getContext().startActivity(intent);
                }
                else if (m.getMealType().equals(Constants.Dinner)){
                    Intent intent = DinnerActivity.createIntentForEdit(getContext(), m.getId());
                    getContext().startActivity(intent);
                }
            }
        });


            type.setText(String.valueOf(m.getMealType()));
            mealName.setText(String.valueOf(m.getMealName()));

            mealtime.setText(m.getMealTime());

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