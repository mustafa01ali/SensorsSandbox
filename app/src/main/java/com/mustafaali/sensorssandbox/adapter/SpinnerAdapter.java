/*******************************************************************************
 * Copyright 2013 Mustafa Ali
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
