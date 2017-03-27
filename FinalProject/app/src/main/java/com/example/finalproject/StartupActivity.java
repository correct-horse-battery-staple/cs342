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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StartupActivity extends ServerActivity {

    final boolean DEBUG_SKIP_SERVER_ACCESS = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        IntentFilter intentFilter = new IntentFilter("server");
        intentFilter.addAction("access");
        ServerReceiver receiver = new ServerReceiver();
        registerReceiver(receiver,intentFilter);

        if(!DEBUG_SKIP_SERVER_ACCESS) {
            new serverAccessTask().execute();
        }
    }

    @Override
    public void receiveServer(){

    }

    class serverAccessTask extends AsyncTask<String,String,String> {

        final String SERVER_URL = "148.85.77.39";
        @Override
        protected void onPreExecute(){ super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s){
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

                    Intent newIntent = new Intent("server_output");
                    newIntent.setAction("server_output");
                    newIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    newIntent.putExtra("data",response.toString());
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

            return null;
        }

        @Override
        protected void onPostExecute(String s){

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
}