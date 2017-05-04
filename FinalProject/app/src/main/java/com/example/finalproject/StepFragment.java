package com.example.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class StepFragment extends Fragment implements View.OnClickListener{
    Button stepsButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_step, container, false);

        stepsButton = (Button)view.findViewById(R.id.stepsButton);
        stepsButton.setOnClickListener(this);
        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 10000),
                new DataPoint(1, 11000),
                new DataPoint(2, 9000),
                new DataPoint(3, 9500),
                new DataPoint(4, 12000)
        });

        graph.addSeries(series);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.stepsButton){
            startActivity(new Intent(getActivity(),StepsActivity.class));
        }

    }
}
