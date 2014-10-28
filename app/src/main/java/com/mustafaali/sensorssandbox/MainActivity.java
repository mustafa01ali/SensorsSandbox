/*******************************************************************************
 * Copyright 2013 Mustafa Ali
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mustafaali.sensorssandbox;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private SensorManager mSensorManager;
    private List<Sensor> mSensors;
    private Sensor mSensor;

    private Spinner spinner;
    private TextView vendorTextView;
    private TextView versionTextView;
    private TextView typeTextView;
    private TextView maxRangeTextView;
    private TextView minDelayTextView;
    private TextView resolutionTextView;
    private TextView powerTextView;
    private TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        displaySensorsList();
    }

    private void initUi() {
        spinner = (Spinner) findViewById(R.id.sensors_spinner);
        spinner.setOnItemSelectedListener(onSpinnerItemSelectedListener);
        vendorTextView = (TextView) findViewById(R.id.vendor_name_tv);
        versionTextView = (TextView) findViewById(R.id.version_tv);
        typeTextView = (TextView) findViewById(R.id.type_tv);
        maxRangeTextView = (TextView) findViewById(R.id.max_range_tv);
        minDelayTextView = (TextView) findViewById(R.id.min_delay_tv);
        resolutionTextView = (TextView) findViewById(R.id.resolution_tv);
        powerTextView = (TextView) findViewById(R.id.power_tv);
        dataTextView = (TextView) findViewById(R.id.sensor_data_tv);
    }

    private void displaySensorsList() {

        mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_item, mSensors);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.spinner_dropdown_item, R.id.text1);


//        for (Sensor s : mSensors) {
//            adapter.add(s.getName());
//        }

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mSensor)
            mSensorManager.registerListener(mSensorEventListener, mSensor,
                    SensorManager.SENSOR_DELAY_UI);
    }

    private OnItemSelectedListener onSpinnerItemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {
            mSensor = mSensorManager.getDefaultSensor(mSensors.get(pos)
                    .getType());

            displaySensorInfo();

            mSensorManager.unregisterListener(mSensorEventListener);
            dataTextView.setText(R.string.msg_waiting_for_data);

            mSensorManager.registerListener(mSensorEventListener, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < event.values.length; i++) {
                sb.append("values[" + i + "] : " + event.values[i] + "\n");
            }

            dataTextView.setText(sb);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            showShareDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void showShareDialog() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));

        startActivity(Intent.createChooser(sendIntent,
                getResources().getText(R.string.send_to)));
    }

    private void displaySensorInfo() {
        vendorTextView.setText(mSensor.getVendor());
        versionTextView.setText(String.valueOf(mSensor.getVersion()));
        typeTextView.setText(String.valueOf(mSensor.getType()));
        maxRangeTextView.setText(String.valueOf(mSensor.getMaximumRange()));
        minDelayTextView.setText(String.valueOf(mSensor.getMinDelay()) + " micro seconds");
        resolutionTextView.setText(String.valueOf(mSensor.getResolution()));
        powerTextView.setText(String.valueOf(mSensor.getPower()) + " mA");
    }

}
