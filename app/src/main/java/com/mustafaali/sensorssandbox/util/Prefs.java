package com.mustafaali.sensorssandbox.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class Prefs {

  private static Prefs instance;
  private static final String PREFS_FILE_NAME = "sensorssandbox";
  private static final String PREFS_VERSION_CODE_KEY = "version_code";

  private SharedPreferences sharedPreferences;
  private int currentVersionCode;

  public static Prefs getInstance(Context context) {
    if (instance == null) {
      instance = new Prefs(context);
    }
    return instance;
  }

  private Prefs() {
  }

  private Prefs(Context context) {
    sharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
    try {
      PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      currentVersionCode = pInfo.versionCode;
      Log.d(">>>", "Current - " + currentVersionCode);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  public boolean shouldShowChangeLog() {
    return (currentVersionCode > getLastVersionCode());
  }

  private int getLastVersionCode() {
    Log.d(">>>", "Last - " + sharedPreferences.getInt(PREFS_VERSION_CODE_KEY, 0));
    return sharedPreferences.getInt(PREFS_VERSION_CODE_KEY, 0);
  }

  public void updateLastVersionCode() {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(PREFS_VERSION_CODE_KEY, currentVersionCode);
    editor.apply();
  }
}
