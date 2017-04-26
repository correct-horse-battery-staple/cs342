package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

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

        getData("weight");
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
}