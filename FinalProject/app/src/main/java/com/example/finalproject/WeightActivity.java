package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeightActivity extends ServerActivity {

    private NumberPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        picker = (NumberPicker)findViewById(R.id.weight_PICKER);
        picker.setMinValue(0);
        picker.setMaxValue(700);
        picker.setValue(140);
    }

    @Override
    public void setErrorMessage(String s){
        TextView textView = (TextView)findViewById(R.id.weight_ERROR);
        textView.setText(s);
    }

    public void submit(View v){
        int weight = picker.getValue();
        putData("weight",weight+"");
    }

    public void getData(View v){
        getData("weight");
    }

    @Override
    private void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            try {
                JSONArray jsonArray = new JSONArray(data.substring(op.length()+1));
                for (int i =0;i<jsonArray.length();i++){

                }
                setErrorMessage(data);
                Log.d("server load",data.substring(op.length()+1));
            }
            catch(JSONException i) {
                i.printStackTrace();
            }
        }
        else if(op.equals("store")){
            setErrorMessage(data);
        }
    }
}