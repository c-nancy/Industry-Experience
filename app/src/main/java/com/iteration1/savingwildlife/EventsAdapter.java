package com.iteration1.savingwildlife;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iteration1.savingwildlife.entities.Event;

import java.util.ArrayList;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private ArrayList<Event> filterList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type,location,time;

        public MyViewHolder(View view) {
            super(view);
            type = (TextView) view.findViewById(R.id.event_type);
            location = (TextView) view.findViewById(R.id.event_location);
            time = (TextView) view.findViewById(R.id.event_time);

        }
    }

    public EventsAdapter(ArrayList<Event> filterList) {
        this.filterList = filterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = filterList.get(position);
        String type = event.getEvent_type();
        String location = event.getEvent_location();
        String date = event.getEvent_date().replace("-","/") + " " + event.getEvent_start() + "-" + event.getEvent_end();
        holder.type.setText(type);
        holder.location.setText(location);
        holder.time.setText(date);
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }
}