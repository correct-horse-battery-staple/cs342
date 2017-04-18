package com.example.finalproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by panda_000 on 4/12/2017.
 */

public class HomeActivity extends ServerActivity {

    LineGraphSeries<DataPoint> activity;
    LineGraphSeries<DataPoint> weight;
    LineGraphSeries<DataPoint> heartrate;
    LineGraphSeries<DataPoint> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GraphView graph = (GraphView) findViewById(R.id.home_GRAPH);
        activity = new LineGraphSeries<>(
                new DataPoint[]{
                        new DataPoint(3,5),
                        new DataPoint(4,7),
                        new DataPoint(6,8),
                        new DataPoint(7,2)
                }
        );
        weight = new LineGraphSeries<>();
        heartrate = new LineGraphSeries<>();
        steps = new LineGraphSeries<>();
        graph.addSeries(activity);
    }

    public void addGraphPoints(){

    }
}
