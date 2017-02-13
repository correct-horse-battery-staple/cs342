package com.example.myamherstkevin;

import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by panda_000 on 2/2/2017.
 */

public class ThreeMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threemeal);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_threemeal);
        if (fragment == null) {
            fragment = new ThreeMealFragment();
            fm.beginTransaction().add(R.id.activity_threemeal, fragment).commit();
        }
    }

    public void goToMeal(View view){
        int id = view.getId();
        Toast.makeText(this,""+id,Toast.LENGTH_SHORT).show();
    }
}
