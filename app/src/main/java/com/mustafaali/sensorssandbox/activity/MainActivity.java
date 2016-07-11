package com.mustafaali.sensorssandbox.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import com.mustafaali.sensorssandbox.R;
import com.mustafaali.sensorssandbox.adapter.SpinnerAdapter;
import com.mustafaali.sensorssandbox.fragment.ChangeLogDialogFragment;
import com.mustafaali.sensorssandbox.util.OnDialogDismissedListener;
import com.mustafaali.sensorssandbox.util.Prefs;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogDismissedListener {

  private SensorManager sensorManager;
  private List<Sensor> sensorList;
  private Sensor sensor;

  private Spinner spinner;
  private TextView vendorTextView;
  private TextView versionTextView;
  private TextView typeTextView;
  private TextView maxRangeTextView;
  private TextView minDelayTextView;
  private TextView resolutionTextView;
  private TextView powerTextView;
  private TextView dataTextView;

  private Prefs prefs;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initUi();
    prefs = Prefs.getInstance(this);
    if (savedInstanceState == null) { //
      showChangeLogIfFirstLaunch();
    }
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
    sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
    SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_item, sensorList);
    spinner.setAdapter(adapter);
    spinner.setSelection(0);
  }

  @Override protected void onStop() {
    super.onStop();
    sensorManager.unregisterListener(mSensorEventListener);
  }

  @Override protected void onStart() {
    super.onStart();
    if (null != sensor) {
      sensorManager.registerListener(mSensorEventListener, sensor, SensorManager.SENSOR_DELAY_UI);
    }
  }

  private OnItemSelectedListener onSpinnerItemSelectedListener = new OnItemSelectedListener() {

    @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
      sensor = sensorManager.getDefaultSensor(sensorList.get(pos).getType());

      displaySensorInfo();

      sensorManager.unregisterListener(mSensorEventListener);
      dataTextView.setText(R.string.msg_waiting_for_data);

      sensorManager.registerListener(mSensorEventListener, sensor,
          SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override public void onNothingSelected(AdapterView<?> parent) {

    }
  };

  private SensorEventListener mSensorEventListener = new SensorEventListener() {

    @Override public void onSensorChanged(SensorEvent event) {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < event.values.length; i++) {
        sb.append("values[").append(i).append("] : ").append(event.values[i]).append("\n");
      }

      dataTextView.setText(sb);
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
  };

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
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

  private void showChangeLogIfFirstLaunch() {
    if (prefs.shouldShowChangeLog()) {
      new ChangeLogDialogFragment().show(getFragmentManager(), ChangeLogDialogFragment.TAG);
    }
  }

  private void displaySensorInfo() {
    vendorTextView.setText(sensor.getVendor());
    versionTextView.setText(String.valueOf(sensor.getVersion()));
    typeTextView.setText(String.valueOf(sensor.getType()));
    maxRangeTextView.setText(String.valueOf(sensor.getMaximumRange()));
    minDelayTextView.setText(
        String.format(getString(R.string.value_min_delay), String.valueOf(sensor.getMinDelay())));
    resolutionTextView.setText(String.valueOf(sensor.getResolution()));
    powerTextView.setText(
        String.format(getString(R.string.value_power), String.valueOf(sensor.getPower())));
  }

  private void showShareDialog() {
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.setType("text/plain");
    sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
    sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));

    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
  }

  @Override public void onDismissed() {
    prefs.updateLastVersionCode();
  }
}
