package com.iteration1.savingwildlife;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bin.david.form.data.column.Column;
import com.google.android.gms.common.util.ArrayUtils;
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
import com.iteration1.savingwildlife.utils.HistogramTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bin.david.form.core.SmartTable;

public class Statistics extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<WeightedLatLng> fishLocations;
    private LatLng center;
    private Toolbar toolbar;
    private MapView mMapView;
    private Histogram his;
    private float[] xs;
    private SmartTable table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        mMapView = (MapView) findViewById(R.id.basemap_map);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView ttitle = (TextView) findViewById(R.id.toolbar_title);
        ttitle.setText("Australian beaches");
        center = new LatLng(-23.7440165, 133.2164058);
//        showKnownTipView(visualization);
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

        DatabaseReference nReference = FirebaseDatabase.getInstance().getReference("pollutionresource");
        nReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                xs = new float[0];
                List<HistogramTable> ht = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Long val = (Long) child.getValue();
//                    float count = val.floatValue();
                    int count = val.intValue();
                    xs = add_element(xs, count);
                    String item = child.getKey();
                    HistogramTable onetable = new HistogramTable(item, count);
                    ht.add(onetable);
                }
                his = new Histogram(getApplicationContext(), null);
                his.setValues(xs);
                table = (SmartTable) findViewById(R.id.table);
                table.setData(ht);
                table.getConfig().setShowColumnTitle(false)
                        .setShowXSequence(false)
                        .setShowYSequence(false);
                his.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });
    }

    private float[] add_element(float[] series, float element) {
        series = Arrays.copyOf(series, series.length + 1);
        series[series.length - 1] = element;
        return series;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle("MapViewBundleKey");
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle("MapViewBundleKey", mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}