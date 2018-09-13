package com.iteration1.savingwildlife;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bin.david.form.core.SmartTable;

public class PollutionFragment extends Fragment{
    private Histogram his;
    private float[] xs;
    private SmartTable table;
    private View fView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fView = inflater.inflate(R.layout.pollution_fragment, container, false);

        his = new Histogram(fView.getContext(), null);


        return fView;
    }
}
