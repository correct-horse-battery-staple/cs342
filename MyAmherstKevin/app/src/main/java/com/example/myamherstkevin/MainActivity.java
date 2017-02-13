package com.example.myamherstkevin;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_main);
        if(fragment == null){
            fragment = new MainFragment();
            fm.beginTransaction().add(R.id.activity_main, fragment).commit();
        }
    }

    public void goTo(View view){
        Intent intent = new Intent(this, ThreeMealActivity.class);
        startActivity(intent);
    }
}
