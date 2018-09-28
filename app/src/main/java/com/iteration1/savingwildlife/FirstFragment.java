package com.iteration1.savingwildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FirstFragment extends Fragment{

    View thisView;
    private ImageView create;
    private ImageView find;
    private ImageView report;
    private ImageView beachlist;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        thisView = inflater.inflate(R.layout.first_page, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Beach Step");
        create = thisView.findViewById(R.id.create_event);
        create.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), CreateEvent.class);
            startActivity(intent);
        });
        find = thisView.findViewById(R.id.find_event);
        find.setOnClickListener(v -> {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Events");
            cleanBackStack(fragmentManager);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new EventList(), null)
                    .addToBackStack(null)
                    .commit();
        });
        report = thisView.findViewById(R.id.make_report);
        report.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), MakeReport.class);
            startActivity(intent);
        });
        beachlist = thisView.findViewById(R.id.beach_list);
        beachlist.setOnClickListener(v -> {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Beaches");
            cleanBackStack(fragmentManager);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new HomeScreenFragment(), null)
                    .addToBackStack(null)
                    .commit();
        });
        return thisView;
    }

    private void cleanBackStack(FragmentManager fragmentManager){
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }
}
