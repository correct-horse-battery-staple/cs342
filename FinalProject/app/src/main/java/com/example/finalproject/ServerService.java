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

    public static Intent serverIntent(Activity activity, String params){
        Intent intent = new Intent(activity, ServerService.class);
        intent.setAction("access");
        intent.putExtra("params",params);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d("service","Service accessed");

            String params = intent.getStringExtra("params");

            final String SERVER_URL = "http://148.85.1.65:47158/";
            try {
                URL url;
                HttpURLConnection connection = null;
                try {

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

                    Log.d("URL POST",url.toString()+"/"+params);

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
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();

                    Log.d("response",response.length()+"");
                    Log.d("response",response.toString());

                    String responseString = response.toString();
                    String type = responseString.split("/")[0];
                    String data = responseString.split("/")[1];

                    Intent newIntent = new Intent("output");
                    newIntent.setAction("output");
                    newIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    newIntent.putExtra("type",type);
                    newIntent.putExtra("data",data);
                    //newIntent.putExtra("response",responseString);
                    sendBroadcast(newIntent);
                    Log.d("server output","broadcast sent "+newIntent.toString());
                } catch (Exception e) {
                    ///modify so if no response, do appropriate things
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
}
