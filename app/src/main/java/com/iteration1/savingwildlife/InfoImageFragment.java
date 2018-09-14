package com.iteration1.savingwildlife;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iteration1.savingwildlife.entities.Beach;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;

public class InfoImageFragment extends Fragment  implements OnMapReadyCallback {
    private View parentView;
    private Beach selected;
    private MapView mMapView;
    private GoogleMap mMap;
    private ArrayList<String> reports;
    private TextView textView;
    private LatLng center;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.beach_image_fragment, container, false);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        selected = (Beach) bundle.getSerializable("selected");
        reports = (ArrayList<String>) bundle.getStringArrayList("reports");
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("com.google.android.geo.API_KEY");
        }
        Double lat = selected.getLatitude();
        Double lng = selected.getLongitude();
        center = new LatLng(lat, lng);
        textView = (TextView) parentView.findViewById(R.id.txt);
        mMapView = (MapView) parentView.findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.onResume(); // needed to get the map to display immediately


        Log.d("name", selected.getName());
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        if(reports == null){sb.append("No");}
        else{sb.append(reports.size());}
        sb.append(" incident report(s) so far has been made in this beach area!");
        textView.setText(sb);
        mMapView.getMapAsync(this);
        return parentView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker to default location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 9));
        mMap.addMarker(new MarkerOptions()
                .position(center)
                .title(selected.getName()));
    }
}
