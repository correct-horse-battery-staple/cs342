package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by panda_000 on 3/27/2017.
 */

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter("server");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter.addAction("output");
        ServerReceiver receiver = new ServerReceiver();
        registerReceiver(receiver,intentFilter);
    }

    public void receiveServer(Intent intent){
        String type = intent.getStringExtra("type");
        String data = intent.getStringExtra("data");

        Log.d("app receiveServer type", type);
        Log.d("app receiveServer data", data);

        //types: ping, error, login, register, token

        if(type.equals("ping")){
            //Log.d("app receiveServer ping", getClass().toString());
            if(getClass().isInstance(new StartupActivity())) {
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }
        }
        else if(type.equals("error")){
            String op = data.split(":")[0];
            String error = data.split(":")[1];
            error.replaceAll("_"," ");
            error = error.substring(0,1).toUpperCase() + error.substring(1);
            Log.d("error",op+" "+error);
            setErrorMessage(error);
        }
        else if(type.equals("login")){
            if(data.split(":")[0].equals("success")){
                String token = data.split(":")[1];

                SharedPreferences preferences = getPreferences(0);
                SharedPreferences.Editor preferencesEditor = preferences.edit();
                preferencesEditor.putString("token",token);
                preferencesEditor.commit();
                Log.d("server token",token);

                Intent homeIntent = new Intent(this,HomeActivity.class);
                startActivity(homeIntent);
            }
        }
        else if(type.equals("register")){

        }
        else if(type.equals("token")){
            String op = data.split(":")[0];
            if(op.equals("load")) {
                try {
                    JSONObject json = new JSONObject(data.split(":")[1]);
                    JSONArray userdata=json.getJSONArray("userdata");
                    for(int i = 0; i < userdata.length(); i++){
                        JSONObject dataObject = userdata.getJSONObject(i);
                        ///to be completed
                    }
                }
                catch(JSONException i) {
                    i.printStackTrace();
                }
            }
            else if(op.equals("write")){

            }
        }
    }

    public void setErrorMessage(String s){
        return;
    }
}
