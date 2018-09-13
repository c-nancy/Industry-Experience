package com.iteration1.savingwildlife;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FishImageFragment extends Fragment {
    View fView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        fView = inflater.inflate(R.layout.fish_population_fragment, container, false);
        return fView;
    }
}
