package com.example.myamherstkevin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by panda_000 on 2/7/2017.
 */

public class SpecificMealArrayAdapter extends ArrayAdapter {

    private final Context context;

    ArrayList<Menu> items;

    public SpecificMealArrayAdapter(Context ctx, ArrayList<Menu> items)
    {
        super(ctx, R.layout.adapter_specificmeal);
        this.context = ctx;

        this.items = items;
        Log.d("SpecificMealArrAdapter", "Read " + items.size() + " items");
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.adapter_specificmeal, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.specificmeal_text);
        TextView textView2 = (TextView) rowView.findViewById(R.id.specificmeal_section);

        Log.d("ThreeMealArrayAdapter", "Row "+position);

        final String text = getItem(position).getItems();
        textView1.setText(text);
        textView2.setText(getItem(position).getSection());
        return rowView;
    }

    //1
    @Override
    public int getCount() {
        return items.size();
    }

    //2
    @Override
    public Menu getItem(int position) {
        return items.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }
}
