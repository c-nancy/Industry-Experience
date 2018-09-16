package com.iteration1.savingwildlife;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.iteration1.savingwildlife.entities.Beach;

public class InfoStatisticFragment extends Fragment {
    private View parentView;
    private Beach selected;
    private BarChart chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.beach_statistic_fragment, container, false);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        selected = (Beach) bundle.getSerializable("selected");
        chart = (BarChart) parentView.findViewById(R.id.chart);
        connectDatabase();
        chart.setNoDataText("Graph is loading...");
        return parentView;
    }

    private void connectDatabase(){


    }
}
