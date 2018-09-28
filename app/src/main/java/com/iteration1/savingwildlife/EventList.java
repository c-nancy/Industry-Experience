package com.iteration1.savingwildlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.entities.Event;
import com.iteration1.savingwildlife.entities.Report;
import com.iteration1.savingwildlife.utils.EventAdapter;
import com.iteration1.savingwildlife.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;

public class EventList extends Fragment {
    View thisView;
    private ListView listView;
    private ArrayList<Beach> beachList;
    private ArrayList<Event> events;
    private ArrayList<Report> reports;
    private ArrayList<String> locations;
    private ArrayList<String> dates;
    private Spinner order;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.event_list, container, false);
        beachList = new ArrayList<>();
        events = new ArrayList<>();
        reports = new ArrayList<>();
        locations = new ArrayList<>();
        listView = thisView.findViewById(R.id.beach_event);
        order = thisView.findViewById(R.id.select_order);
        getEvents();
        order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                switch (position){
//                    case 1:
//                        Collections.sort();
//                }
                if (position!=0){
                    UIUtils.showCenterToast(getContext(),"Order by " + order.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        return thisView;
    }



    private void getEvents(){
        // Get the reference of firebase instance
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("beaches");

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Beach b = child.getValue(Beach.class);
                    beachList.add(b);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("event");

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Event e = child.getValue(Event.class);
                    events.add(e);
                    locations.add(e.getEvent_location());
                }
                listView.setAdapter(new EventAdapter(getContext(), events));
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                params.height = metrics.heightPixels;
                listView.setLayoutParams(params);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        Event e = events.get(arg2);
                        showMultiBtnDialog(e);;

                    }

                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("report");
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Report e = child.getValue(Report.class);
                    reports.add(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void showMultiBtnDialog(Event event){
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getContext());
        normalDialog.setIcon(R.drawable.ic_event_note_black_24dp);
        normalDialog.setTitle(event.getEvent_type());
        StringBuilder sb = new StringBuilder();
        sb.append(event.getEvent_date().substring(0,4).replace("-","/"));
        sb.append(" · ");
        sb.append(event.getEvent_start() + " - " + event.getEvent_end());
        sb.append(" · ");
        sb.append(event.getEvent_location() + "\n\n");
        sb.append("0 People registered");
        normalDialog.setMessage(sb.toString());
        normalDialog.setPositiveButton("Register",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UIUtils.showCenterToast(getContext(), "Register successful!");
                    }
                });
        normalDialog.setNeutralButton("See beach",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Beach b:beachList) {
                            if (b.getName().toUpperCase().replaceAll(" ", "")
                                    .contains(event.getEvent_location().toUpperCase().trim().replaceAll(" ", ""))){
                                ArrayList<String> relatedReport = new ArrayList<>();
                                if (reports != null) {
                                    for (Report e : reports) {
                                        if (e.getBeach_name().toUpperCase().replaceAll(" ", "")
                                                .equals(b.getName().toUpperCase().replaceAll(" ", ""))) {
                                            relatedReport.add(e.getEvent_type());
                                        }
                                    }
                                }
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), InfoPage.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("beach", b);
                                bundle.putStringArrayList("reports",relatedReport);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }
                });
        normalDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        normalDialog.show();
    }

}
