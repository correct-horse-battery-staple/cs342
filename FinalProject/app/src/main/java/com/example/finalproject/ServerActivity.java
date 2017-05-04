package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by panda_000 on 3/27/2017.
 */

public class ServerActivity extends AppCompatActivity {

    private ServerReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart(){
        super.onStart();

        IntentFilter intentFilter = new IntentFilter("server");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter.addAction("output");
        receiver = new ServerReceiver();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(receiver);
    }


    public void receiveServer(Intent intent){
        String type = intent.getStringExtra("type");
        String data = intent.getStringExtra("data");

        Log.d("app receiveServer type", type);
        Log.d("app receiveServer data", data);

        //types: ping, error, login, register, token

        if(type.equals("ping")){
            ping(data);
        }
        else if(type.equals("error")){
            error(data);
        }
        else if(type.equals("login")){
            login(data);
        }
        else if(type.equals("register")){
            register(data);
        }
        else if(type.equals("token")){
            token(data);
        }
    }

    protected void ping(String data){
        //Log.d("app receiveServer ping", getClass().toString());
        if(getClass().isInstance(new StartupActivity())) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }
    protected void error(String data){
        String op = data.split(":")[0];
        String error = data.substring(op.length()+1);//split(":")[1];
        error.replaceAll("_"," ");
        error = error.substring(0,1).toUpperCase() + error.substring(1);
        Log.d("error",op+" "+error);
        setErrorMessage(error);
    }
    protected void login(String data){
        if(data.split(":")[0].equals("success")){
            String token = data.split(":")[1];

            SharedPreferences preferences = getSharedPreferences("tokens", Context.MODE_PRIVATE);
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putString("token",token);
            preferencesEditor.commit();
            Log.d("server token",token);

            Intent homeIntent = new Intent(this,HomeActivity.class);
            startActivity(homeIntent);
        }
    }

    protected void register(String data){
        // probably will never be reached with the way the code is currently implemented
    }

    protected void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            try {
                JSONArray jsonArray = new JSONArray(data.substring(op.length()+1));
                for (int i =0;i<jsonArray.length();i++){
                    Log.d("app json",jsonArray.get(i).toString());
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

    protected void setErrorMessage(String s){
        return;
    }

    public void getData(String type){
        SharedPreferences preferences = getSharedPreferences("tokens", Context.MODE_PRIVATE);
        String token = preferences.getString("token",null);
        Intent i = ServerService.ServerIntent(this,"token/"+token+"?load:"+type);
        startService(i);
    }

    public void putData(String type, String value){
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        SharedPreferences preferences = getSharedPreferences("tokens", Context.MODE_PRIVATE);
        String token = preferences.getString("token",null);
        Intent i = ServerService.ServerIntent(this,"token/"+token+"?store:"+type+"/{"+value+",'datetime':'"+datetime+"'}");
        startService(i);
    }
}
