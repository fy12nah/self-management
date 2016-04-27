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
import com.nooraalhassen.myapplication.LongT_Illness;
import com.nooraalhassen.myapplication.LunchActivity;
import com.nooraalhassen.myapplication.R;
import com.nooraalhassen.myapplication.ShortT_Illness;
import com.nooraalhassen.myapplication.SnacksActivity;
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


        final Illness m = list.get(position);

        TextView type = (TextView) convertView.findViewById(R.id.tvIllType);

        TextView illName = (TextView) convertView.findViewById(R.id.illName);
        ListView medsList = (ListView) convertView.findViewById(R.id.meds);
        TextView sDateText = (TextView) convertView.findViewById(R.id.illSDate);
        TextView eDateText = (TextView) convertView.findViewById(R.id.illEDate);


        Button editbtn = (Button) convertView.findViewById(R.id.illnessEdit);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.getIllnessType().equals(Constants.LongT)) {
                    Intent intent = LongT_Illness.createIntentForEdit(getContext(), m.getId());
                    getContext().startActivity(intent);
                } else if (m.getIllnessType().equals(Constants.ShortT)) {
                    Intent intent = ShortT_Illness.createIntentForEdit(getContext(), m.getId());
                    getContext().startActivity(intent);
                }
            }

        });


        type.setText(String.valueOf(m.getIllnessType()));
        illName.setText(String.valueOf(m.getIllnessName()));
        sDateText.setText(simpleDateFormat.format(m.getsIllnessDate()));
        if (m.geteIllnessDate() != null){
            eDateText.setText(simpleDateFormat.format(m.geteIllnessDate()));
        }

        IllnessMedsAdapter adapter = new IllnessMedsAdapter(getContext(), m.getMedsList());
        medsList.setAdapter(adapter);


        return convertView;
    }

}
