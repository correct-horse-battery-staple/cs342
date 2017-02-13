package com.example.myamherstkevin;

import android.content.Context;
import android.content.Intent;
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

public class ThreeMealArrayAdapter extends ArrayAdapter<String> {

    private final Context context;

    ArrayList<String> items;

    public ThreeMealArrayAdapter(Context ctx, ArrayList<String> items)
    {
        super(ctx, R.layout.adapter_threemeal);
        this.context = ctx;
        // items = FileUtil.readFromFile(context);

        this.items = items;
        Log.d("ThreeMealArrayAdapter", "Read " + items.size() + " items");
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.adapter_threemeal, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.threemeal_text);

        Log.d("ThreeMealArrayAdapter", "Row "+position);

        final String text = getItem(position);
        textView.setText(text);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,SpecificMealActivity.class);
                intent.putExtra("Meal",text);
                context.startActivity(intent);
            }
        });
        return rowView;
    }

    //1
    @Override
    public int getCount() {
        return items.size();
    }

    //2
    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }
}
