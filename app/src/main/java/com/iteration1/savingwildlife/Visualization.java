package com.iteration1.savingwildlife;

import android.app.Activity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class Visualization extends Activity {

    private MapView mMapView;
    private MapboxMap mMapboxMap;
    private final LatLng ST_KILDA = new LatLng(-37.8679, 144.9740);
    private final LatLng Elwood_Beach = new LatLng(-37.8902, 144.9846);
    private final LatLng Half_Moon_Beach = new LatLng(-38.1730, 145.0804);
    private final LatLng Brighton_Beach = new LatLng(-37.9160, 144.9861);
    private final LatLng Mothers_Beach = new LatLng(-38.2149, 145.0350);
    private final LatLng Williamstown_Beach = new LatLng(-37.8641, 144.8944);
    private final LatLng Sorrento_Beach = new LatLng(-31.8283, 115.7439);
    private final LatLng Altona_Beach = new LatLng(-37.8710, 144.8300);
    private final LatLng Mordialloc_Beach = new LatLng(-38.00899, 145.086);
    private final LatLng Hampton_Beach = new LatLng(-37.936144, 144.996948);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.visualization);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ST_KILDA, 11));
                addMarker(mMapboxMap);
            }
        });
    }

    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }

    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }

    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState); }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        MarkerOptions markerOptions1 = new MarkerOptions();
        MarkerOptions markerOptions2 = new MarkerOptions();
        MarkerOptions markerOptions3 = new MarkerOptions();
        MarkerOptions markerOptions4 = new MarkerOptions();
        MarkerOptions markerOptions5 = new MarkerOptions();
        MarkerOptions markerOptions6 = new MarkerOptions();
        MarkerOptions markerOptions7 = new MarkerOptions();
        MarkerOptions markerOptions8 = new MarkerOptions();
        MarkerOptions markerOptions9 = new MarkerOptions();
        markerOptions9.position(Hampton_Beach);
        markerOptions9.title("Hampton Beach");
        markerOptions9.snippet("Welcome to Hampton Beach!");
        markerOptions8.position(Mordialloc_Beach);
        markerOptions8.title("Mordialloc Beach");
        markerOptions8.snippet("Welcome to Mordialloc Beach!");
        markerOptions7.position(Altona_Beach);
        markerOptions7.title("Altona Beach");
        markerOptions7.snippet("Welcome to Altona Beach!");
        markerOptions6.position(Sorrento_Beach);
        markerOptions6.title("Sorrento Beach");
        markerOptions6.snippet("Welcome to Sorrento Beach!");
        markerOptions4.position(Mothers_Beach);
        markerOptions4.title("Mothers Beach");
        markerOptions4.snippet("Welcome to Mothers Beach!");
        markerOptions5.position(Williamstown_Beach);
        markerOptions5.title("Williamstown Beach");
        markerOptions5.snippet("Welcome to Williamtown Beach!");
        markerOptions2.position(Half_Moon_Beach);
        markerOptions2.title("Half Moon Beach");
        markerOptions2.snippet("Welcome to Half Moon Beach!");
        markerOptions3.position(Brighton_Beach);
        markerOptions3.title("Brighton Beach");
        markerOptions3.snippet("Welcome to Brighton Beach!");
        markerOptions1.position(Elwood_Beach);
        markerOptions1.title("Elwood_Beach");
        markerOptions1.snippet("Welcome to Elwood Beach!");
        markerOptions.position(ST_KILDA);
        markerOptions.title("St Kilda");
        markerOptions.snippet("Welcome to St Kilda!");
        mapboxMap.addMarker(markerOptions);
        mapboxMap.addMarker(markerOptions1);
        mapboxMap.addMarker(markerOptions2);
        mapboxMap.addMarker(markerOptions3);
        mapboxMap.addMarker(markerOptions4);
        mapboxMap.addMarker(markerOptions5);
        mapboxMap.addMarker(markerOptions6);
        mapboxMap.addMarker(markerOptions7);
        mapboxMap.addMarker(markerOptions8);
        mapboxMap.addMarker(markerOptions9);
    }
}

