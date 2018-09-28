package com.iteration1.savingwildlife;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;


public class CreateEvent extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbar_title;
    private DatePicker eventDate;
    private Spinner spinnerForEventLocation;
    private TimePicker timePickerForStartTime;
    private TimePicker timePickerForEndTime;
    private Button btnSubmmit;
    private int startHours;
    private int startMins;
    private int endHours;
    private int endMins;
    private long startDate;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        covertToLongType();
        initUI();
        btnSubmmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submmitInfo();
                spinnerForEventLocation.setSelection(0);
            }
        });
    }



    private void initUI(){
        btnSubmmit = (Button) findViewById(R.id.submmitbtn);
        spinnerForEventLocation = (Spinner) findViewById(R.id.spinnerForEventLocation);
        timePickerForStartTime = (TimePicker)findViewById(R.id.timePickerForStartTime);
        timePickerForEndTime = (TimePicker)findViewById(R.id.timePickerForEndTime);
        timePickerForStartTime.setIs24HourView(true);
        timePickerForEndTime.setIs24HourView(true);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("event");
        toolbar_title = (TextView) findViewById(R.id.toolbar_title1);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        eventDate = (DatePicker) findViewById(R.id.datePickerForEvent);
        eventDate.setMinDate(new Date().getTime());
        eventDate.setMaxDate(startDate);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void submmitInfo() {
        if (!spinnerForEventLocation.getSelectedItem().toString().equals("Select one")) {
            if (Build.VERSION.SDK_INT > 23) {
                startHours = timePickerForStartTime.getHour();
                startMins = timePickerForStartTime.getMinute();
                endHours = timePickerForEndTime.getHour();
                endMins = timePickerForEndTime.getMinute();
            } else {
                startHours = timePickerForStartTime.getCurrentHour();
                startMins = timePickerForStartTime.getCurrentMinute();
                endHours = timePickerForEndTime.getCurrentHour();
                endMins = timePickerForEndTime.getCurrentMinute();
            }
            String event_type = "Beach Cleaning";
            String eventLocation = spinnerForEventLocation.getSelectedItem().toString();
            Integer year = eventDate.getYear();
            Integer month = eventDate.getMonth() + 1;
            Integer Day = eventDate.getDayOfMonth();
            String event_date = Day.toString() + "-" + month.toString() + "-" + year.toString();
            String start_time = String.valueOf(startHours) + ":" + String.valueOf(startMins);
            String end_time = String.valueOf(endHours) + ":" + String.valueOf(endMins);
            String id = databaseReference.push().getKey();
            databaseReference.child(id).child("event_date").setValue(event_date);
            databaseReference.child(id).child("event_start").setValue(start_time);
            databaseReference.child(id).child("event_end").setValue(end_time);
            databaseReference.child(id).child("event_location").setValue(eventLocation);
            databaseReference.child(id).child("event_type").setValue(event_type);
            Toast.makeText(CreateEvent.this, "Your event has been created!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CreateEvent.this, "Miss the event location!", Toast.LENGTH_SHORT).show();
        }
    }


    private void covertToLongType(){
        try {
            String dateString = "01/01/2020";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}



