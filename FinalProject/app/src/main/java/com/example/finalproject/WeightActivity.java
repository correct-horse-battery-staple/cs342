package com.example.finalproject;

import android.os.Bundle;
import android.widget.NumberPicker;

public class WeightActivity extends ServerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        NumberPicker picker = (NumberPicker)findViewById(R.id.weight_PICKER);
        picker.setMinValue(0);
        picker.setMaxValue(700);
        picker.setValue(140);

        ServerService.getDataServerIntent(this,"weight");
    }
}
