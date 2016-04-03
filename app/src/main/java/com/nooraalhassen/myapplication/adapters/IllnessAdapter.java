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
import com.nooraalhassen.myapplication.model.Illness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nooraalhassen
 */

public class IllnessAdapter extends ArrayAdapter<Illness>{

    ArrayList<Illness> list;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.display_DatePattern);

    public IllnessAdapter(Context context, ArrayList<Illness> items) {

        super(context, R.layout.meds_items_list_single_item, items);
        list = (ArrayList<Illness>) items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.meds_list_signle_item, parent, false);
        }

        TextView type = (TextView) convertView.findViewById(R.id.tvIllType);

        TextView illName = (TextView) convertView.findViewById(R.id.illName);
        ListView medsList = (ListView) convertView.findViewById(R.id.meds);
        TextView sDateText = (TextView) convertView.findViewById(R.id.illSDate);
        TextView eDateText = (TextView) convertView.findViewById(R.id.illEDate);


        Illness m = list.get(position);

        type.setText(String.valueOf(m.getIllnessType()));
        illName.setText(String.valueOf(m.getIllnessName()));
        sDateText.setText(simpleDateFormat.format(m.getsIllnessDate()));
        eDateText.setText(simpleDateFormat.format(m.geteIllnessDate()));

        IllnessMedsAdapter adapter = new IllnessMedsAdapter(getContext(), m.getMealItems());
        medsList.setAdapter(adapter);


        return convertView;
    }

}
