package com.baldwin.clockpuncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by alex on 6/4/2014.
 */

/*
    Maybe only do one dimension array and then extract data from single string.
 */
public class ListViewAdapter extends BaseAdapter {

    Context context;
    String[] id;
    String[] timeIn;
    String[] timeOut;
    String[] totalTime;
    private static LayoutInflater inflater = null;

    public  ListViewAdapter(Context context, String[] id, String[] timeIn, String[] timeOut, String[] totalTime){
        this.context = context;
        this.id = id;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.totalTime = totalTime;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return timeIn.length;
    }

    @Override
    public Object getItem(int position) {
        return id[position];
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(id[position]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi==null){
            vi = inflater.inflate(R.layout.row, null);
        }
        TextView tvListIn = (TextView) vi.findViewById(R.id.tvListIn);
        TextView tvListOut = (TextView) vi.findViewById(R.id.tvListOut);
        TextView tvListTotal = (TextView) vi.findViewById(R.id.tvListTotal);

        tvListIn.setText(timeIn[position]);
        tvListOut.setText(timeOut[position]);
        tvListTotal.setText(totalTime[position]);
        return vi;
    }
}
