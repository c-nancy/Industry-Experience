package com.iteration1.savingwildlife.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteration1.savingwildlife.R;
import com.iteration1.savingwildlife.entities.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EventAdapter extends BaseAdapter{
    private List<Event> events;
    private ArrayList<String> eventlocation;
    private LayoutInflater layoutInflater;
    private Context context;
    ImageView img;
    TextView tv1;
    TextView tv2;
    TextView tv3;

    public EventAdapter(Context context, ArrayList<Event> lists) {
        this.context = context;
        this.events = lists;
        layoutInflater = LayoutInflater.from(this.context);
        eventlocation = new ArrayList<>();
    }



    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.event_item, null);
        }
        img = (ImageView) convertView.findViewById(R.id.EventImage);
        tv1 = (TextView) convertView.findViewById(R.id.ItemTitle);
        tv2 = (TextView) convertView.findViewById(R.id.ItemText);
        tv3 = (TextView) convertView.findViewById(R.id.time);
//        img.setBackgroundResource(events.get(position).getEvent_location());
        img.setBackgroundResource(R.drawable.amu_bubble_mask);
        tv1.setText(events.get(position).getEvent_type());
        StringBuilder sb1 = new StringBuilder();
        sb1.append(events.get(position).getEvent_date().substring(0,4).replace("-","/"));
        sb1.append(" ");
        sb1.append(events.get(position).getEvent_start() + " - " + events.get(position).getEvent_end());
        tv2.setText(sb1.toString());
        StringBuilder sb2 = new StringBuilder("      " );
        sb2.append(events.get(position).getEvent_location());
        tv3.setText(sb2.toString());
        return convertView;
    }


}
