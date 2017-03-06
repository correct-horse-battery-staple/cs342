package com.example.midterm;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panda_000 on 3/5/2017.
 */

public class AArrayAdapter extends ArrayAdapter<Task> {

    private Context context;
    private ArrayList<Task> items;

    public AArrayAdapter(Context ctx, ArrayList<Task> items)
    {
        super(ctx, R.layout.adapter_a);
        this.context = ctx;
        this.items = items;
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.adapter_a, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.a);
        TextView textView2 = (TextView) rowView.findViewById(R.id.b);
        TextView textView3 = (TextView) rowView.findViewById(R.id.c);

        textView1.setText(getItem(position).getS1());
        textView2.setText(getItem(position).getS2());
        textView3.setText(""+getItem(position).getI());

        Log.d("adapter",textView1.getText().toString());

        CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.check);
        checkBox.setTag(getItem(position));

        checkBox.setChecked(getItem(position).isB());
        if(items.get(position).isB()){
            textView1.setPaintFlags(textView1.getPaintFlags() | ( Paint.STRIKE_THRU_TEXT_FLAG));
            textView2.setPaintFlags(textView2.getPaintFlags() | ( Paint.STRIKE_THRU_TEXT_FLAG));
            textView3.setPaintFlags(textView3.getPaintFlags() | ( Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else{
            textView1.setPaintFlags(textView1.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            textView2.setPaintFlags(textView2.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            textView3.setPaintFlags(textView3.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return rowView;

    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Task getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
