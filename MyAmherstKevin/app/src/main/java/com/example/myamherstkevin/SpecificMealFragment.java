package com.example.myamherstkevin;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by panda_000 on 2/7/2017.
 */

public class SpecificMealFragment extends Fragment {

    private String dump;

    public SpecificMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_specificmeal, container, false);

        ListView listview = (ListView) v.findViewById(R.id.listview_specificmeal);

        String meal = getArguments().getString("Meal");
        Toast.makeText(getActivity(),meal,Toast.LENGTH_SHORT).show();

        new screp().execute(meal);

//        ArrayList<Menu> items = FileUtil.readFromFile(getActivity());
//        ArrayList<Menu> listItems = new ArrayList<>();
//        for(Menu m:items){
//            if(m.getType().equals(meal)){
//                listItems.add(m);
//            }
//        }
//
//        SpecificMealArrayAdapter adapter = new SpecificMealArrayAdapter(getActivity(), listItems);
//        listview.setAdapter(adapter);
//        TextView textview = (TextView)v.findViewById(R.id.specificmeal_type);
//        textview.setText(meal);

        TextView textview2 = (TextView)v.findViewById(R.id.GARBAGE);
        textview2.setText(dump);
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class screp extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... s){
            try {
                Calendar c = Calendar.getInstance(); //year-month-day
                Date d = c.getTime();
                String date = d.getYear()+"-"+d.getMonth()+"-"+d.getDay();
                MenuFileUtil m = new MenuFileUtil();
                verifyPermissions(getActivity());
                m.readFromFile(date,s[0]);
                Log.d("Trash",m.getData());
                dump = m.getData();
            }
            catch(IOException i){
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
                Log.d("permissions","here");
            }
        }
    }
}

