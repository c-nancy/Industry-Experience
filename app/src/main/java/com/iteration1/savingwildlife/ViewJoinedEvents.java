package com.iteration1.savingwildlife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteration1.savingwildlife.entities.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewJoinedEvents extends Fragment {
    View eventView;
    ArrayList<Event> eventList;
    ArrayList<Event> filterList;
    String imei;
    Event e;
    RecyclerView myView;
    JoinedEventsAdapter mAdapter;
    private TextView holder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
//        e = new Event();
//        e.setEvent_date("No Data");
//        e.setEvent_start("No Data");
//        e.setEvent_end("No Data");
//        e.setEvent_type("You have not joined any events");
//        e.setEvent_location("No Data");
//        e.setImei(" ");
//        e.setName(" ");
//        e.setRegistered_user(" ");
        eventList = new ArrayList<>();
        filterList = new ArrayList<>();
        eventView = inflater.inflate(R.layout.view_joined_events, container, false);
        myView = (RecyclerView) eventView.findViewById(R.id.my_recycler_view1);
        holder = eventView.findViewById(R.id.empty_view);
        new LoadTask1().execute();
//        getData();
        return eventView;
    }

    private void getRelatedEvents() {
        imei = getUniqueIMEIId(getContext());
        filterList = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i++) {
            if (!imei.equals("not_found")) {
                if (eventList.get(i).getRegistered_user().contains(imei) && eventList.get(i).getImei() != null) {
                    filterList.add(eventList.get(i));
                }
            } else {
                filterList = new ArrayList<>();
            }
        }
        if (filterList.size() == 0) {
            myView.setVisibility(View.GONE);
            holder.setVisibility(View.VISIBLE);
//            filterList.add(e);
        }
        mAdapter = new JoinedEventsAdapter(filterList, new JoinedEventsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                showEventDialog(event);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        myView.setLayoutManager(mLayoutManager);
        myView.setItemAnimator(new DefaultItemAnimator());
        //myView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this.getActivity()), LinearLayoutManager.VERTICAL));
        myView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            assert telephonyManager != null;
            String imei = telephonyManager.getDeviceId();
            Log.e("imei", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }




    private void showEventDialog(Event event){
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.drawable.ic_event_note_black_24dp);
        normalDialog.setTitle(event.getEvent_type());
        StringBuilder sb = new StringBuilder();
        sb.append(event.getEvent_date().substring(0,5).replace("-","/"));
        sb.append("/");
        sb.append(event.getEvent_date().substring(event.getEvent_date().length() - 2,event.getEvent_date().length()));
        sb.append(" · ");
        sb.append(event.getEvent_start() + " - " + event.getEvent_end());
        sb.append(" · ");
        sb.append(event.getEvent_location() + "\n\n");
        if (event.getDescription() != null && !event.getDescription().equals("")){
            sb.append(event.getDescription()).append("\n");
        }
        normalDialog.setMessage(sb);
        normalDialog.setPositiveButton("Unregister", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (event.getImei().trim().equals(getUniqueIMEIId(getContext()))) {
                    Toast.makeText(getContext(), "You cannot unregister to the event you create!", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> t = new ArrayList<>(Arrays.asList(event.getRegistered_user().split(",")));
                    for (String s : t) {
                        if (s.trim().equals(imei)) {
                            t.remove(s);
                        }
                    }
                    StringBuilder nt = new StringBuilder();

                    if (t.size() == 0) {
                        nt.append(" ");
                    } else {
                        for (String n : t) {
                            nt.append(n);
                            nt.append(",");
                        }
                    }
                    event.setRegistered_user(nt.toString());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("event");
                    reference.child(event.getId()).child("registered_user").setValue(nt.toString());
                    Toast.makeText(getContext(), "You have unregistered to this event!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().detach(ViewJoinedEvents.this).attach(ViewJoinedEvents.this).commit();
                }
            }
        });
        normalDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = normalDialog.create();
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(13);
    }




    private class LoadTask1 extends AsyncTask<String, Integer, String> {

        @MainThread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("pre", "onPreExecute() called");
        }

        @WorkerThread
        @Override
        protected String doInBackground(String... params) {

            // Get the reference of firebase instance

            DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("event");

            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Event> events = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Event e = child.getValue(Event.class);
                        e.setId(child.getKey());
                        events.add(e);
                    }
                    eventList = events;
                    getRelatedEvents();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getDetails());
                }
            });

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
            Log.d("mprogress", "onProgressUpdate(Progress... progresses) called");
        }

        @MainThread
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute("finish");
            Log.d("post", "onPostExecute(Result result) called");
        }

        @Override
        protected void onCancelled() {
            Log.d("cancelled", "onCancelled() called");
        }

    }
}