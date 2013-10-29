package com.mustafaali.sensorssandbox;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private TextView mListTV;
	private TextView mDataTV;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mListTV = (TextView) findViewById(R.id.sensors_list_tv);
        mDataTV = (TextView) findViewById(R.id.sensor_data_tc);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        
        displaySensorsList();
        
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}



	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}



	private void displaySensorsList() {
		List<Sensor> list = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        
        StringBuilder sb = new StringBuilder();
        for(Sensor s : list){
        	sb.append(s.getName());
        	sb.append("\n");
        }
        
        mListTV.setText(sb);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}



	@Override
	public void onSensorChanged(SensorEvent event) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("X: " + event.values[0] + "\n");
		sb.append("Y: " + event.values[1] + "\n");
		sb.append("Z: " + event.values[2] + "\n");
		
		mDataTV.setText(sb);
	}
    
}
