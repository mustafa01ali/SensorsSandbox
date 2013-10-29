package com.mustafaali.sensorssandbox;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SensorManager mSensorManager;
	private List<Sensor> mSensors;
	private Sensor mSensor;

	private Spinner spinner;
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
		dataTextView = (TextView) findViewById(R.id.sensor_data_tv);
	}

	private void displaySensorsList() {

		mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, android.R.id.text1);

		for (Sensor s : mSensors) {
			adapter.add(s.getName());
		}

		spinner.setAdapter(adapter);
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
					SensorManager.SENSOR_DELAY_NORMAL);
	}

	private OnItemSelectedListener onSpinnerItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			mSensor = mSensorManager.getDefaultSensor(mSensors.get(pos)
					.getType());
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

			sb.append("X: " + event.values[0] + "\n");
			sb.append("Y: " + event.values[1] + "\n");
			sb.append("Z: " + event.values[2] + "\n");

			dataTextView.setText(sb);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_about) {
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}

	}

}
