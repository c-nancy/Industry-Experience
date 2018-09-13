package com.iteration1.savingwildlife;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class FishPopulationFragment extends Fragment implements OnMapReadyCallback {
    private MapView mMapView;
    private View fView;
    private GoogleMap mMap;
    private LatLng center;
    private ArrayList<WeightedLatLng> fishLocations;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        fView = inflater.inflate(R.layout.fish_population_fragment, container, false);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        mMapView = (MapView) fView.findViewById(R.id.basemap_map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
        center = new LatLng(-23.7440165, 133.2164058);

        return fView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker to default location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 3));
        connectDatabase();
    }




    private void connectDatabase() {
        // Get the reference of firebase instance
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("fishcluster");
        fishLocations = new ArrayList<>();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    HashMap<String, Double> map = (HashMap<String, Double>) child.getValue();

                    Double a = (Double) map.get("lat");
                    Double b = (Double) map.get("lng");
                    Double w = (Double) map.get("weight");

                    // Add weights to points
                    WeightedLatLng p = new WeightedLatLng(new LatLng(a, b), w * 10);
                    fishLocations.add(p);
                }
                Log.d("list size", Integer.toString(fishLocations.size()));

                // Create a heat map tile provider, passing it the latlngs of the police stations.
                HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                        .weightedData(fishLocations)
                        .build();
                mProvider.setRadius(50);
                int[] colors = {
                        Color.CYAN, Color.RED};

                float[] startPoints = {
                        0.2f, 1f
                };
                mProvider.setGradient(new Gradient(colors, startPoints));
                // Add a tile overlay to the map, using the heat map tile provider.
                TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });
    }
}
