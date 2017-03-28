package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerService extends IntentService {

    public ServerService() {
        super("ServerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();



            final String SERVER_URL = "148.85.77.39";
            try {
                URL url;
                HttpURLConnection connection = null;
                try {
                    //Create connection
                    url = new URL(SERVER_URL);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");

                    connection.setRequestProperty("Content-Length", "" +
                            Integer.toString(0));
                    connection.setRequestProperty("Content-Language", "en-US");

                    connection.setUseCaches (false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    //Send request
                    DataOutputStream wr = new DataOutputStream (
                            connection.getOutputStream ());
                    wr.writeBytes ("");
                    wr.flush ();
                    wr.close ();

                    //Get Response
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();

                    String responseString = response.toString();
                    String type = responseString.split(":")[0];
                    String data = responseString.split(":")[1];

                    Intent newIntent = new Intent("output");
                    newIntent.setAction("output");
                    newIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    newIntent.putExtra("type",type);
                    newIntent.putExtra("data",data);
                    sendBroadcast(newIntent);
                    Log.d("server_output","broadcast sent "+newIntent.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }
            catch(Exception i){
                i.printStackTrace();
            }
        }
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
