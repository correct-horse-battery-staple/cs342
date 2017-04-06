package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by panda_000 on 3/27/2017.
 */

public class ServerActivity extends AppCompatActivity {

    protected SharedPreferences preferences = getPreferences(0);
    protected SharedPreferences.Editor preferencesEditor = preferences.edit();

    public void receiveServer(Intent intent){

        String type = intent.getStringExtra("type");
        String data = intent.getStringExtra("data");

        //types: ping, error, login, register, token
        if(type.equals("ping")){
            if(data.split(":")[0].equals("success")){
                if(!getClass().isInstance(new StartupActivity())) {
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        }
        else if(type.equals("error")){
            String op = data.split(":")[0];
            String error = data.split(":")[1];
            Log.d("error",op+" "+error);
        }
        else if(type.equals("login")){
            if(data.split(":")[0].equals("success")){
                String token = data.split(":")[1];
                preferencesEditor.putString("token",token);
                preferencesEditor.commit();
                Log.d("token",token);
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
}
