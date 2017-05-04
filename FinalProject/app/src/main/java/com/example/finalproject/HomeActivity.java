package com.example.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by panda_000 on 4/12/2017.
 */

public class HomeActivity extends ServerActivity {

    BarGraphSeries<DataPoint> activity;
    BarGraphSeries<DataPoint> weight;
    BarGraphSeries<DataPoint> heartrate;
    BarGraphSeries<DataPoint> steps;

    @Override
    protected void onStart(){
        super.onStart();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GraphView graph = (GraphView) findViewById(R.id.home_GRAPH);
        activity = new BarGraphSeries<>(
                new DataPoint[]{
                        new DataPoint(3,5),
                        new DataPoint(4,7),
                        new DataPoint(6,8),
                        new DataPoint(7,2)
                }
        );
        weight = new BarGraphSeries<>();
        heartrate = new BarGraphSeries<>();
        steps = new BarGraphSeries<>();
        graph.addSeries(activity);
    }

    public void addGraphPoints(){

    }

    public void toActivity(View v){
        Intent intent = new Intent(this,ActivityActivity.class);
        startActivity(intent);
    }
    public void toSteps(View v){
        Intent intent = new Intent(this,StepsActivity.class);
        startActivity(intent);
    }
    public void toWeight(View v){
        Intent intent = new Intent(this,WeightActivity.class);
        startActivity(intent);
    }
    public void toHeartrate(View v){
        Intent intent = new Intent(this,HeartrateActivity.class);
        startActivity(intent);
    }
}
