package com.iteration1.savingwildlife;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.GeoDataClient;

import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.utils.SplashScreen;
import com.iteration1.savingwildlife.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private Button visualization;
    private Button explore;
    private TextView title;

    private ArrayList<Beach> beachList;
    private ArrayList<ImageView> ibList;

    private SplashScreen ss;


    // To see whether user has given the permission of using device location or not
    private boolean locationrefuse;


    private Location mlocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beachList = new ArrayList<>();
        ibList = new ArrayList<>();
        ss = new SplashScreen(this);
        locationrefuse = false;

        // Get user location using locationmanager
        LocationManager mLocMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("permission check","inside");
            locationrefuse = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            locationrefuse = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
            if(!locationrefuse){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        101);
            }
            UIUtils.showCenterToast(this,"Show beach randomly...");
        }else{
            UIUtils.showCenterToast(this,"Analyzing the closest beach...");
            assert mLocMan != null;
            mlocation = mLocMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }



        this.setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        initUI();
        new LoadTask().execute();
        hint();
        visul();
        statistic();
    }

    private void initUI() {
        ImageView ib1 = (ImageView) findViewById(R.id.image1);
        ImageView ib2 = (ImageView) findViewById(R.id.image2);
        ImageView ib3 = (ImageView) findViewById(R.id.image3);
        ImageView ib4 = (ImageView) findViewById(R.id.image4);
        ImageView ib5 = (ImageView) findViewById(R.id.image5);
        ImageView ib6 = (ImageView) findViewById(R.id.image6);
        ImageView ib7 = (ImageView) findViewById(R.id.image7);
        ImageView ib8 = (ImageView) findViewById(R.id.image8);
        ImageView ib9 = (ImageView) findViewById(R.id.image9);
        ImageView ib10 = (ImageView) findViewById(R.id.image10);
        title = (TextView) findViewById(R.id.textView1);
        visualization = (Button) findViewById(R.id.visualization);
        explore = (Button) findViewById(R.id.explore);
        ibList.add(ib1);
        ibList.add(ib2);
        ibList.add(ib3);
        ibList.add(ib4);
        ibList.add(ib5);
        ibList.add(ib6);
        ibList.add(ib7);
        ibList.add(ib8);
        ibList.add(ib9);
        ibList.add(ib10);
        glow();
    }

    private void visul() {
        visualization.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Toast.makeText(MainActivity.this, "map is loading!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Visualization.class);
                startActivity(intent);
            }
        });
    }

    private void statistic() {
        explore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent intent = new Intent(MainActivity.this, Statistics.class);
                startActivity(intent);
            }
        });
    }

    private void glow() {
        title.setShadowLayer(50, 0, 0, Color.CYAN);
    }

    private void hint() {
        Snackbar.make(visualization, "Click pictures to know more", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.theme_color)).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }









    // This is a new thread to load data from database
    private class LoadTask extends AsyncTask<String, Integer, String> {

        @MainThread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ss.show(2000);
            Log.d("pre", "onPreExecute() called");
        }

        @WorkerThread
        @Override
        protected String doInBackground(String... params) {

                    // Get the reference of firebase instance
                    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("beaches");

                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Beach b = child.getValue(Beach.class);
                                beachList.add(b);
                            }
                            applyRecommendSystem();
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
            ss.hide();
            Log.d("post", "onPostExecute(Result result) called");
        }

        @Override
        protected void onCancelled() {
            Log.d("cancelled", "onCancelled() called");
        }



        // Simulate the recomsys, but now just sort randomly instead of real algorithm
        private void applyRecommendSystem() {

            if(mlocation != null){
                // If has the user location, apply the recommend algorithm based on distance.(Bubble sort)
                for (int i = 0; i < beachList.size(); i++) {
                    for (int j = 0; j < beachList.size()-1; j++){
                        Location iLocation = new Location(LocationManager.GPS_PROVIDER);
                        iLocation.setLatitude(beachList.get(j).getLatitude());
                        iLocation.setLongitude(beachList.get(j).getLongitude());
                        Location nextLocation  = new Location(LocationManager.GPS_PROVIDER);
                        nextLocation.setLatitude(beachList.get(j+1).getLatitude());
                        nextLocation.setLongitude(beachList.get(j+1).getLongitude());
                        if (mlocation.distanceTo(nextLocation) < mlocation.distanceTo(iLocation)){
                            Beach tb = beachList.get(j+1);
                            beachList.set(j+1,beachList.get(j));
                            beachList.set(j,tb);
                        }
                    }
                }
            }else{
                // When user do not provide location, shuffle the beaches
                Collections.shuffle(beachList);
            }

            for (int i = 0; i < ibList.size(); i++) {
                ImageView iv = ibList.get(i);

                // This block is for taking picts from cloud
                StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(beachList.get(i).getBanner());
                GlideApp.with(getApplicationContext())
                        .load(imageRef)
                        .placeholder(R.drawable.common_full_open_on_phone)
                        .error(R.drawable.common_full_open_on_phone)
                        .into(iv);
                iv.setTag(beachList.get(i).getName());

                //@Override
                iv.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, InfoPage.class);
                    String ns = (String) v.getTag();
                    Beach selected;
                    for (Beach b : beachList) {
                        if (b.getName().toUpperCase().replaceAll(" ", "")
                                .contains(ns.toUpperCase().trim().replaceAll(" ", ""))) {
                            selected = b;
                            // intent cannot be used to parse integer, so use bundle to pack the params
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("beach", selected);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
            }
        }






    }

}


