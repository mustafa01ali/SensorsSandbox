package com.mustafaali.sensorssandbox.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mustafaali.sensorssandbox.R;

public class AboutActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
