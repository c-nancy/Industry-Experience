package com.iteration1.savingwildlife;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.utils.UIUtils;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends AppCompatActivity {

    private MapView mMapView;
    private MapboxMap mMapboxMap;
    private ArrayList<Beach> beaches;
    private ArrayList<LatLng> locations;
    private String provider;
    private Location location;

//    @Override
//    @Nullable
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
//            savedInstanceState) {
//        vViewmap = inflater.inflate(R.layout.visualization, container, false);
//        return vViewmap;
//    }

    @Override
    public void onCreate(Bundle
                                 savedInstanceState) {
//        vViewmap = inflater.inflate(R.layout.visualization, container, false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualization);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        beaches = new ArrayList<>();
        locations = new ArrayList<>();
        MapQuest.start(getApplicationContext());
        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        LocationManager mLocMan = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        assert mLocMan != null;
        List<String> list = mLocMan.getProviders(true);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            UIUtils.showCenterToast(this, "Please check internet connection or GPS permission!");
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = mLocMan.getLastKnownLocation(provider);
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(location), 9);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                connectDatabase();
                mMapView.setStreetMode();
                mMapboxMap.moveCamera(cu);
                mMapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location))
                        .title("You are here"));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


    private void connectDatabase(){
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("beaches");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Beach b = child.getValue(Beach.class);
                    beaches.add(b);
                    locations.add(new LatLng(b.getLatitude(),b.getLongitude()));
                    mMapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(b.getLatitude(),b.getLongitude()))
                            .title(b.getName())
                            .snippet(b.getLocation()));
                    Log.d("one beach",b.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });
    }

    // When user click the back button of their own phone
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

