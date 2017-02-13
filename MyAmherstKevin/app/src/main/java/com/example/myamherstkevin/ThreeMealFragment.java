package com.example.myamherstkevin;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by panda_000 on 2/5/2017.
 */

public class ThreeMealFragment extends Fragment {
    public ThreeMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_threemeal, container, false);

        ListView listview = (ListView) v.findViewById(R.id.listview_threemeal);

        //Simple string list
        ArrayList<Menu> items = FileUtil.readFromFile(getActivity());

        ArrayList<String> listItems = new ArrayList<String>();
        listItems.add("Breakfast");
        listItems.add("Lunch");
        listItems.add("Dinner");

        ThreeMealArrayAdapter adapter = new ThreeMealArrayAdapter
                (getActivity(), listItems);

        listview.setAdapter(adapter);
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
