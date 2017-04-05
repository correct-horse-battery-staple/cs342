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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerService extends IntentService {

    public ServerService() {
        super("ServerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d("service","Service accessed");

            final String action = intent.getAction();

            String mode = intent.getStringExtra("mode");
            String params = intent.getStringExtra("params");

            final String SERVER_URL = "http://148.85.1.65:47158/";
            try {
                URL url;
                HttpURLConnection connection = null;
                try {
                    //Create connection
                    if(mode.equals("GET")) {
                        url = new URL(SERVER_URL+params);
                        connection = (HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                        connection.setRequestProperty( "Accept", "*/*" );

                        connection.setRequestProperty("Content-Length", "" +
                                Integer.toString(params.length()));
                        connection.setRequestProperty("Content-Language", "en-US");

                        connection.setUseCaches(false);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);

                        Log.d("URL GET",url.toString());
                    }
                    else if(mode.equals("POST")){
                        url = new URL(SERVER_URL);
                        connection = (HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type",
                                "application/x-www-form-urlencoded");
                        connection.setRequestProperty( "Accept", "*/*" );

                        connection.setRequestProperty("Content-Length", "" +
                                Integer.toString(params.length()));
                        connection.setRequestProperty("Content-Language", "en-US");

                        connection.setUseCaches(false);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);

                        Log.d("URL POST",url.toString());
                    }

                    Log.d("params",params.toString());

                    //Send request
                    //DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());

                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write (params);
                    writer.flush ();
                    writer.close ();

                    //Get Response
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuffer response = new StringBuffer();
                    Log.d("response",response.length()+"");
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();

                    Log.d("response",response.toString());

                    String responseString = response.toString();
                    //String type = responseString.split(":")[0];
                    //String data = responseString.split(":")[1];

                    Intent newIntent = new Intent("output");
                    newIntent.setAction("output");
                    newIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    //newIntent.putExtra("type",type);
                    //newIntent.putExtra("data",data);
                    newIntent.putExtra("response",responseString);
                    sendBroadcast(newIntent);
                    Log.d("server output","broadcast sent "+newIntent.toString());

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
