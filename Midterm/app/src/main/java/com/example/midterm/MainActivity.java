package com.example.midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AArrayAdapter adapter;
    AArrayAdapter adapter2;

    ArrayList<Task> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.listview);

        listItems = new ArrayList<>();
        listItems.add(new Task("do things","wow many stuff to do",0));
        listItems.add(new Task("many things yes","indeedio there are thing do",1));
        listItems.add(new Task("ahhhhh","be very scare of everything",2));
        listItems.add(new Task("how do thhingy aw jeez spelling bad","there not time do no thing only do thing",6));
        listItems.add(new Task("asdf","those are random keys",3));
        listItems.add(new Task("stop lying","those keys are next to each other",4));
        listItems.add(new Task("dvorak","sometimes those keys are next to each other",5));
        listItems.add(new Task("no reason not to believe that","wow",7));

        Log.d("asdf",listItems.toString());

        adapter = new AArrayAdapter(this, listItems);
        listview.setAdapter(adapter);
        Log.d("asdf","TRUE");
    }

    public void check(View v){
        Log.d("check","Asdf");
        CheckBox cb = (CheckBox)v;
        Task t = (Task)cb.getTag();
        t.toggle();
        adapter.notifyDataSetChanged();
        if(adapter2!=null){
            adapter2.notifyDataSetChanged();
        }
    }
    public void search(View v){
        Log.d("search","Asdf");
        EditText e = (EditText)findViewById(R.id.search);
        String search = e.getText().toString().toLowerCase();
        ArrayList<Task> s = new ArrayList<Task>();
        for (int i = 0; i< listItems.size(); i++){
            if(listItems.get(i).getS1().contains(search)||listItems.get(i).getS2().contains(search))
                s.add(listItems.get(i));
        }
        adapter2 = new AArrayAdapter(v.getContext(), s);
        ((ListView) findViewById(R.id.listview)).setAdapter(adapter2);
    }
    public void clear (View v){
        Log.d("clars","Asdf");
        ((ListView) findViewById(R.id.listview)).setAdapter(adapter);
    }
}
