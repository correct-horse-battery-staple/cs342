package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeightActivity extends ServerActivity {

    private NumberPicker picker;
    private boolean LBorKG;
    private GraphView graph;
    private BarGraphSeries<DataPoint> dataSeries;
    private int graphCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        picker = (NumberPicker)findViewById(R.id.weight_PICKER);
        picker.setMinValue(0);
        picker.setMaxValue(700);
        picker.setValue(140);

        LBorKG=true;
        graph = (GraphView)findViewById(R.id.weight_GRAPH);
        dataSeries = new BarGraphSeries<>();
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        graph.addSeries(dataSeries);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this,dateFormat));
        graph.getGridLabelRenderer().setNumHorizontalLabels(graphCount);
        graph.getGridLabelRenderer().setHumanRounding(false);

        getData("weight");

        Switch toggle = (Switch) findViewById(R.id.weight_LBKG);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LBorKG = !LBorKG;
            }
        });
    }

    @Override
    protected void setErrorMessage(String s){
        TextView textView = (TextView)findViewById(R.id.weight_ERROR);
        //textView.setText(s);
    }

    public void submit(View v){
        int weight = picker.getValue();
        putData("weight","'value':"+weight+",'unit':'"+(LBorKG?"lb":"kg")+"'");
    }

    public void getData(View v){
        getData("weight");
    }

    @Override
    protected void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            try {
                JSONArray jsonArray = new JSONArray(data.substring(op.length()+1));
                DataPoint[] points = new DataPoint[jsonArray.length()];
                long minX= 0;
                long maxX =70;
                for (int i =0;i<jsonArray.length();i++){
                    JSONObject object = (JSONObject)jsonArray.get(i);
                    int value = (Integer)(object.get("value"));
                    String unit = (String)object.get("unit");
                    long datetime = Long.parseLong((String)object.get("datetime"));
                    //Log.d("app load",value+" "+unit+" "+datetime);
                    points[i]=new DataPoint(datetime,(unit.equals("kg")?value*2.20462:value));
                    if(i==jsonArray.length()-graphCount-1)
                        minX = datetime;
                    if(i==jsonArray.length()-1)
                        maxX = datetime;
                    Log.d("app load",points[i].toString());
                }
                dataSeries.resetData(points);
                graph.getViewport().setMinX(minX);
                graph.getViewport().setMaxX(maxX);
                //setErrorMessage(data);
                Log.d("server load",data.substring(op.length()+1));
            }
            catch(JSONException i) {
                i.printStackTrace();
            }
        }
        else if(op.equals("store")){
            //setErrorMessage(data);
        }
    }
}