package com.mustafaali.sensorssandbox.adapter;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mustafaali.sensorssandbox.R;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Sensor> {

  private Context context;
  private List<Sensor> sensorList;
  private LayoutInflater layoutInflater;

  public SpinnerAdapter(Context context, int resource, List<Sensor> objects) {
    super(context, resource, objects);
    this.context = context;
    sensorList = objects;
    layoutInflater = LayoutInflater.from(this.context);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    View view = super.getView(position, convertView, parent);
    ((TextView) view).setText(sensorList.get(position).getName());
    return view;
  }

  @Override public View getDropDownView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = layoutInflater.inflate(R.layout.spinner_dropdown_item, parent, false);
    }
    ((TextView) convertView).setText(sensorList.get(position).getName());
    return convertView;
  }
}
