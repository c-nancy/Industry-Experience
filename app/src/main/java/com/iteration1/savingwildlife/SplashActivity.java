package com.iteration1.savingwildlife;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {
    private ArrayList<Beach> beachList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
//
//        Boolean locationrefuse = false;
//        String provider = "";
//        Location location = null;
//        // Get user location using locationmanager
//        LocationManager mLocMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            locationrefuse = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
//            locationrefuse = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
//            if (!locationrefuse) {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
//                        101);
//                this.onResume();
//            }
//            UIUtils.showCenterToast(this.getApplicationContext(), "Show beach randomly...");
//        } else {
//            UIUtils.showCenterToast(this.getApplicationContext(), "Analyzing the closest beach...");
//
//            assert mLocMan != null;
//            List<String> list = mLocMan.getProviders(true);
//            if (list.contains(LocationManager.GPS_PROVIDER)) {
//                provider = LocationManager.GPS_PROVIDER;
//            } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
//                provider = LocationManager.NETWORK_PROVIDER;
//            } else {
//                UIUtils.showCenterToast(this, "Please check internet connection or GPS permission!");
//            }
//
//            location = mLocMan.getLastKnownLocation(provider);
//        }
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("location", location);
//        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }



}

