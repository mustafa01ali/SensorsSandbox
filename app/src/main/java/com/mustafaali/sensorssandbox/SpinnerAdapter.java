package com.mustafaali.sensorssandbox;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mustafa on 10/28/14.
 */
public class SpinnerAdapter extends ArrayAdapter<Sensor> {

    private Context mContext;
    private List<Sensor> mList;
    private LayoutInflater mLayoutInflater;

    public SpinnerAdapter(Context context, int resource, List<Sensor> objects) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        ((TextView) view).setText(mList.get(position).getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.spinner_dropdown_item, parent, false);
        }
        ((TextView) convertView).setText(mList.get(position).getName());
        return convertView;
    }
}
