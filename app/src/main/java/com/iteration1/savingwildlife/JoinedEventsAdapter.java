package com.iteration1.savingwildlife;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteration1.savingwildlife.entities.Event;

import java.util.ArrayList;

public class JoinedEventsAdapter extends RecyclerView.Adapter<JoinedEventsAdapter.MyViewHolder> {

    private ArrayList<Event> filterList;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type,location,time;
        public ImageView image;
        public ImageView image2;

        public MyViewHolder(View view) {
            super(view);
            type = (TextView) view.findViewById(R.id.event_type1);
            location = (TextView) view.findViewById(R.id.event_location1);
            time = (TextView) view.findViewById(R.id.event_time1);
            image = (ImageView) view.findViewById(R.id.EventImage);
            image2 = view.findViewById(R.id.EventImage2);
        }

        public void bind(Event event, OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(event);
                }
            });
        }
    }

    public JoinedEventsAdapter(ArrayList<Event> filterList, OnItemClickListener listener) {
        this.filterList = filterList;
        this.listener = listener;
    }

    @Override
    public JoinedEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joined_event_list_row, parent, false);

        return new JoinedEventsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JoinedEventsAdapter.MyViewHolder holder, int position) {
        holder.bind(filterList.get(position), listener);
        Event event = filterList.get(position);
        StringBuilder type = new StringBuilder(event.getEvent_type());
        String location = event.getEvent_location();
        String date = event.getEvent_date().replace("-","/") + " " + event.getEvent_start() + "-" + event.getEvent_end();

        holder.location.setText(location);
        holder.time.setText(date);
        if (event.getImei().equals(" ")){
            holder.image.setVisibility(View.GONE);
            holder.image2.setVisibility(View.VISIBLE);
            type.append(" (Cancelled)");
        }
        holder.type.setText(type);
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }
}
