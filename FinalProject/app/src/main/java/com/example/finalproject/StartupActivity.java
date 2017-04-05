package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        IntentFilter intentFilter = new IntentFilter("server");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter.addAction("output");
        ServerReceiver receiver = new ServerReceiver();
        registerReceiver(receiver,intentFilter);

        verifyPermissions(this);

        preferences=getPreferences(0);
        preferencesEditor = preferences.edit();
    }

    public void onClick(View v){
        Log.d("button","clicked");
        if(!DEBUG_SKIP_SERVER_ACCESS) {
            Intent intent = ServerService.serverIntent(this, "ping");
            startService(intent);
        }
    }

    @Override
    public void receiveServer(Intent intent){

        String type = intent.getStringExtra("type");
        String data = intent.getStringExtra("data");


        //types: error, login, register, token
        if(type.equals("error")){
            String op = data.split(":")[0];
            String error = data.split(":")[1];
            setText(op+" error: "+error);
        }
        else if(type.equals("login")){
            if(data.split(":")[0].equals("success")){
                preferencesEditor.putString("token",data.split(":")[1]);
                preferencesEditor.commit();
            }
        }
        else if(type.equals("register")){

        }
        else if(type.equals("text")){

        }
    }

    private void setText(String s){
        TextView textView = (TextView)findViewById(R.id.startup_text);
        textView.setText(s);
        Log.d("text","Text changed to \""+s+"\"");
    }

    public void verifyPermissions(Activity activity) {
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);

        String[] permissions = {Manifest.permission.INTERNET};
        if (cameraPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,permissions,1);
            Log.d("permissions","requested");
        }
    }
}