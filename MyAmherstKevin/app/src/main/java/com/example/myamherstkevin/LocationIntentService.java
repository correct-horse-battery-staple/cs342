package com.example.myamherstkevin;

import android.*;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ServiceCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocationIntentService extends IntentService {

    public LocationIntentService() {
        super("LocationIntentService");
    }

    public void onCreate(){
        super.onCreate();
    }

    public static Intent newIntent(Context c){
        Intent intent = new Intent(c, LocationIntentService.class).setAction("Location");
        //PendingIntent pi = PendingIntent.getService(c,0,intent,0);
        return intent;
    }
    static String polyline;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("handle","Intent received");
        if (intent != null) {

            String[] targetURL = intent.getStringArrayExtra("url");
            //Code obtained from http://www.xyzws.com/Javafaq/how-to-use-httpurlconnection-post-data-to-web-server/139

            URL url;
            HttpURLConnection connection = null;
            try {
                //Create connection
                url = new URL(targetURL[0]+targetURL[1]);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" +
                        Integer.toString(targetURL[1].getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches (false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream ());
                wr.writeBytes (targetURL[1]);
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
                //Log.d("received",response.toString());

                String JSON = response.toString();
                int i = JSON.indexOf("polyline");
                polyline = JSON.substring(i).replace(" ","").split(",")[0].split(":")[2];
                polyline=polyline.substring(1,polyline.length()-3);
                Log.d("polyline",polyline);

                Intent newIntent = new Intent("Polyline");
                newIntent.putExtra("polyline",polyline);
                newIntent.setAction("polyline");
                newIntent.addCategory(Intent.CATEGORY_DEFAULT);
                sendBroadcast(newIntent);
                Log.d("polyline","broadcast sent "+newIntent.toString());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
            }

        }
    }
}