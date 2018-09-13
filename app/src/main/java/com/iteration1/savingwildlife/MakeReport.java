package com.iteration1.savingwildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iteration1.savingwildlife.entities.Event;

public class MakeReport extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner eventType;
    private DatePicker eventDate;
    private Button btnUpload;
    private Button btnCancel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);
        initUI();
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addArrayList();
            }
        });

    }

    private void addArrayList(){
        String type = eventType.getSelectedItem().toString();
        Integer year = eventDate.getYear();
        Integer month = eventDate.getMonth();
        Integer Day = eventDate.getDayOfMonth();
        String event_date = Day.toString() + "-" + month.toString() + "-" + year.toString();


//        DatabaseReference pushedPostRef = .push
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id = databaseReference.push().getKey();
//        Event event = new Event(type,event_date);
        databaseReference.child(id).child("event_type").setValue(type);
        databaseReference.child(id).child("event_date").setValue(event_date);
        Toast.makeText(this, "report has been saved", Toast.LENGTH_SHORT).show();
    }


    private void initUI(){
        eventType = (Spinner) findViewById(R.id.spinner1);
        eventDate = (DatePicker) findViewById(R.id.datePicker1);
        btnCancel = (Button) findViewById(R.id.cancelbtn);
        btnUpload = (Button) findViewById(R.id.uploadbtn);
        databaseReference = FirebaseDatabase.getInstance().getReference("report");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        // Back to former page
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
