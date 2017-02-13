package com.example.myamherstkevin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by panda_000 on 2/7/2017.
 */

public class SpecificMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificmeal);

        String meal = getIntent().getStringExtra("Meal");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_specificmeal);
        if(fragment == null){
            fragment = new SpecificMealFragment();
            Bundle b = new Bundle();
            b.putString("Meal",meal);
            fragment.setArguments(b);
            fm.beginTransaction().add(R.id.activity_specificmeal, fragment).commit();
        }
    }
}
