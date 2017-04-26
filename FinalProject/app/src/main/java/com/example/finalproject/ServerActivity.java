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

    private void ping(String data){
        //Log.d("app receiveServer ping", getClass().toString());
        if(getClass().isInstance(new StartupActivity())) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }
    private void error(String data){
        String op = data.split(":")[0];
        String error = data.split(":")[1];
        error.replaceAll("_"," ");
        error = error.substring(0,1).toUpperCase() + error.substring(1);
        Log.d("error",op+" "+error);
        setErrorMessage(error);
    }
    private void login(String data){
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
    private void register(String data){

    }
    private void token(String data){
        setErrorMessage("token response received");
        String op = data.split(":")[0];
        if(op.equals("load")) {
            try {
                JSONObject json = new JSONObject(data.split(":")[1]);
            }
            catch(JSONException i) {
                i.printStackTrace();
            }
        }
        else if(op.equals("store")){

        }
    }

    public void setErrorMessage(String s){
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
        Intent i = ServerService.ServerIntent(this,"token/"+token+"?store:"+type+"/{'value'='"+value+"','datetime'='"+datetime+"'}");
        startService(i);
    }
}
