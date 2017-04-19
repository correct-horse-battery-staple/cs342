package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StartupActivity extends ServerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        verifyPermissions(this);

        Log.d("app startup","sending intent");
        Intent intent = ServerService.genericServerIntent(this, "ping");
        startService(intent);
    }

    public void verifyPermissions(Activity activity) {
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);

        String[] permissions = {Manifest.permission.INTERNET};
        if (cameraPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,permissions,1);
            Log.d("app permissions","requested");
        }
    }

    public void onClick(View v){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void ping(View v){
        Intent intent = ServerService.genericServerIntent(this, "ping");
        startService(intent);
    }
}