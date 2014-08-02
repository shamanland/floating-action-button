package com.shamanland.fab.example;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExampleAdapter extends BaseAdapter {
    private static final String[] sWords = {
            "World",
            "Android",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
    };

    @Override
    public int getCount() {
        return 1000;
    }

    @Override
    public String getItem(int position) {
        return "[" + (position + 1) + "] Hello " + sWords[position % sWords.length];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(parent.getContext());
        }

        ((TextView) convertView).setText(getItem(position));
        return convertView;
    }
}
