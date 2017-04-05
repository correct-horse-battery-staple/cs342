package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StartupActivity extends ServerActivity {

    final boolean DEBUG_SKIP_SERVER_ACCESS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        IntentFilter intentFilter = new IntentFilter("server");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter.addAction("output");
        ServerReceiver receiver = new ServerReceiver();
        registerReceiver(receiver,intentFilter);
    }

    public void onClick(View v){
        Log.d("button","clicked");
        if(!DEBUG_SKIP_SERVER_ACCESS) {
            Intent intent = new Intent(this, ServerService.class);
            intent.setAction("access");
            intent.putExtra("mode","GET");
            intent.putExtra("params","ping");
            startService(intent);
        }
    }

    @Override
    public void receiveServer(Intent i){
        TextView textView = (TextView)findViewById(R.id.startup_text);
        textView.setText("Server response received:\n"+i.getStringExtra("response"));
        Log.d("server","Server response received:"+i.getStringExtra("response"));
    }
}