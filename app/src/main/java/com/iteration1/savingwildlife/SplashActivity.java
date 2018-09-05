package com.iteration1.savingwildlife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteration1.savingwildlife.entities.Beach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {
    private ArrayList<Beach> beachList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
//        beachList = new ArrayList<Beach>();
//        connectDatabase();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("beachlist",(Serializable) beachList);
//        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    // Start the connection with firebase realtime database
//    private void connectDatabase() {
//        // Get the reference of firebase instance
//        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("beaches");
//
//        mReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    Beach b = child.getValue(Beach.class);
//                    beachList.add(b);
//                    Log.d("Added a beach", b.getBanner());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getDetails());
//            }
//        });
//    }

    }

