package com.iteration1.savingwildlife;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
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

import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.GeoDataClient;

import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.utils.LocationUtils;
import com.iteration1.savingwildlife.utils.SplashScreen;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private Button visualization;
    private Button explore;
    private TextView title;

    private ArrayList<Beach> beachList;
    private ArrayList<ImageView> ibList;

    private SplashScreen ss;

    private GeoDataClient mGeoDataClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private PlaceDetectionClient mPlaceDetectionClient;


    private Location mlocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beachList = new ArrayList<>();
        ibList = new ArrayList<>();
        ss = new SplashScreen(this);
        Toast newToast = Toast.makeText(MainActivity.this, "Analyzing the closest beach...", Toast.LENGTH_LONG);
        newToast.setGravity(Gravity.CENTER, 0, 0);
        newToast.show();
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);


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
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
            Log.d("pre", "onPreExecute() called");
        }

        @WorkerThread
        @Override
        protected String doInBackground(String... params) {
            new Thread(new Runnable() {
                @Override
                public void run() {
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
                }
            }).start();
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

//            // Shuffle the elements in drawable id list
//            Collections.shuffle(beachList);

            // Construct a FusedLocationProviderClient.
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mlocation = location;
                                Log.d("lat", Double.toString(mlocation.getLatitude()));
                                Log.d("lng", Double.toString(mlocation.getLongitude()));
                            } else {
                                Log.d("no", "No!");
                            }
                        }
                    });
            Log.d("beach list size", Integer.toString(beachList.size()));


            // The recomment algorithm. Bubble sort
            double mlat = mlocation.getLatitude();
            double mlng = mlocation.getLongitude();
            for (int i = 0; i < beachList.size(); i++) {
                for (int j = 0; j < beachList.size()-1; j++){
                    if (LocationUtils.getDistance(mlat,mlng,beachList.get(i+1).getLatitude(),beachList.get(i+1).getLongitude())
                            <LocationUtils.getDistance(mlat,mlng,beachList.get(i).getLatitude(),beachList.get(i).getLongitude())){
                        Beach tb = beachList.get(i+1);
                        beachList.set(i+1,beachList.get(i));
                        beachList.set(i,tb);
                    }
                }
            }

            for (int i = 0; i < ibList.size(); i++) {
                ImageView iv = ibList.get(i);

                //        This block is for taking picts from cloud
                StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(beachList.get(i).getBanner());
                GlideApp.with(getApplicationContext())
                        .load(imageRef)
                        .thumbnail(0.1f)
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
                            bundle.putParcelable("beach", selected);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
            }
        }



    }

}


