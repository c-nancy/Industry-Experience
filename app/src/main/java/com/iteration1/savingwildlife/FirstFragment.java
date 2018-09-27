package com.iteration1.savingwildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
        create = thisView.findViewById(R.id.create_event);
        create.setOnClickListener(v -> {
            cleanBackStack(fragmentManager);
            // TODO: Edit when we have a create event page
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new CreateEvent(), null)
                    .addToBackStack(null)
                    .commit();
        });
        find = thisView.findViewById(R.id.find_event);
        find.setOnClickListener(v -> {
            cleanBackStack(fragmentManager);
            // TODO: Edit when we have a find event page
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new EventList(), null)
                    .addToBackStack(null)
                    .commit();
        });
        report = thisView.findViewById(R.id.make_report);
        report.setOnClickListener(v -> {
            cleanBackStack(fragmentManager);
            // TODO: Edit when we have a report page
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new HomeScreenFragment(), null)
                    .addToBackStack(null)
                    .commit();
        });
        beachlist = thisView.findViewById(R.id.beach_list);
        beachlist.setOnClickListener(v -> {
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
