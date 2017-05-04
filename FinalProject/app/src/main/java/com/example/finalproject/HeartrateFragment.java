package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class HeartrateFragment extends Fragment implements OnClickListener {

    private Button mBtnPublishAction;
    private ImageView mImgVwLaunchBeatMonitor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_heart_rate_monitor, container, false);

        mBtnPublishAction=(Button)view.findViewById(R.id.btnPublishAction);
        mBtnPublishAction.setOnClickListener(this);
        mImgVwLaunchBeatMonitor=(ImageView)view.findViewById(R.id.imgvwLaunchHeartRater);
        mImgVwLaunchBeatMonitor.setOnClickListener(this);
        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 75),
                new DataPoint(1, 78),
                new DataPoint(2, 77),
                new DataPoint(3, 73),
                new DataPoint(4, 74),
                new DataPoint(5, 74),
                new DataPoint(6, 73),
                new DataPoint(7, 72),
                new DataPoint(8, 72)
        });

        graph.addSeries(series);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.imgvwLaunchHeartRater){
            startActivity(new Intent(getActivity(),HeartrateActivity.class));
        }

    }

}
