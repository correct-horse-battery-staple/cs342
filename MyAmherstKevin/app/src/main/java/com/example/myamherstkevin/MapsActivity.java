package com.example.myamherstkevin;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ServiceCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static GoogleMap mMap;
    private static final LatLng SMUD = new LatLng(42.369773,-72.516238);
    private Marker marker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Fragment inputFragment = new InputFragment();
        fm.beginTransaction().add(R.id.map,inputFragment).commit();

        IntentFilter filter = new IntentFilter("polyline");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new LocationReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean granted = true;
        for(String permission:permissions){
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                granted = false;
                break;
            }
        }
        if(granted){
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,permissions,1);
        }

        if(mLastLocation!=null) {
            LatLng currentLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Marker at " + mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,3));
        }
        else {
            mMap.addMarker(new MarkerOptions().position(SMUD).title("Marker at SMUD"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SMUD,3));
            Log.d("current location","Unsuccessful access of current location");
            Toast.makeText(this,"Unsuccessful access of current location",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if(mGoogleApiClient!=null)
            mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop(){
        unregisterReceiver(receiver);
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void newMarker(View view){
        EditText lat = (EditText)findViewById(R.id.latitude);
        EditText lon = (EditText)findViewById(R.id.longitude);
        double latNum = SMUD.latitude+Double.parseDouble(lat.getText().toString());
        double lonNum = SMUD.longitude+Double.parseDouble(lon.getText().toString());
        if(marker!=null)
            marker.setVisible(false);
        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latNum,lonNum)).title("Marker at "+latNum+", "+lonNum));
        Log.d("marker","New marker at "+latNum+", "+lonNum);
    }

    public void refresh(View view){
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                LatLng currentLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Current location: " + mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,3));
            }
        }
        catch (SecurityException s){
            s.printStackTrace();
        }
    }

    public void navigate(View view){
        refresh(view);
        double latNum;
        double lonNum;
        if(marker!=null) {
            latNum = marker.getPosition().latitude;
            lonNum = marker.getPosition().longitude;
            Log.d("marker","marker not null");
        }
        else {
            latNum = SMUD.latitude;
            lonNum = SMUD.longitude;
            Log.d("marker","marker null");
        }
        String header = "https://maps.googleapis.com/maps/api/directions/";
        String origin = "json?origin=";
        String destination = "&destination=";
        String key = "&key=AIzaSyDiURSUkHNFP1f3kk2ApYXLwmMD1gCP3Kk";

        Log.d("location",mLastLocation.toString());
        Log.d("location",new LatLng(latNum,lonNum).toString());
        String params = origin+mLastLocation.getLatitude()+","+mLastLocation.getLongitude()+destination+latNum+","+lonNum+key;

        Intent intent = LocationIntentService.newIntent(this);
        String[] extras = {header,params};
        intent.putExtra("url",extras);

        startService(intent);
    }

    public void addPolyline(String polyline){
        Log.d("polyline","add polyline "+polyline);
        List<Integer> trucks = new java.util.ArrayList<Integer>(2);
        int truck = 0;
        int carriage_q = 0;
        for (int x = 0, xx = polyline.length(); x < xx; ++x) {
            int i = polyline.charAt(x);
            i -= 63;
            int _5_bits = i << (32 - 5) >>> (32 - 5);
            truck |= _5_bits << carriage_q;
            carriage_q += 5;
            boolean is_last = (i & (1 << 5)) == 0;
            if (is_last) {
                boolean is_negative = (truck & 1) == 1;
                truck >>>= 1;
                if (is_negative) {
                    truck = ~truck;
                }
                trucks.add(truck);
                carriage_q = 0;
                truck = 0;
            }
        }
        ArrayList<LatLng> coords = new ArrayList<>(trucks.size());
        LatLng prev = new LatLng(0,0);
        for(int i = 0; i < trucks.size(); i+=2) {
            double lat = 0;
            double lon = 0;
            for(int j = 0; j < 2; j++){
                if(j==0)
                    lat = trucks.get(i+j)/100000.0;
                else
                    lon = trucks.get(i+j)/100000.0;
            }
            LatLng point = new LatLng(lat+prev.latitude,lon+prev.longitude);
            prev = point;
            Log.d("polyline", point.toString());
            coords.add(point);
        }
        mMap.addPolyline(new PolylineOptions().addAll(coords));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords.get(0),3));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                Log.d("location","successful initial location get");
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        }
        catch (SecurityException s){
            s.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("connection","Connection failed");
    }
}