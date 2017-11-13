package com.renosys.handy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSetting {
    private static AppSetting instance = null;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static synchronized AppSetting getInstance(Context context) {
        if (instance == null) {
            instance = new AppSetting(context);
        }
        return instance;
    }

    public AppSetting(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public void setIpAddress(String ipAddress) {
        editor.putString("IP_ADDRESS", ipAddress);
        editor.commit();
    }

    public String getIpAddress() {
        return preferences.getString("IP_ADDRESS", null);
    }

    public void setCountNewTask(int countNewTask) {
        editor.putInt("COUNT_NEW_TASK", countNewTask);
        editor.commit();
    }

    public int getCountNewTask() {
        return preferences.getInt("COUNT_NEW_TASK", 0);
    }

    public void setInterval(int interval) {
        editor.putInt("INTERVAL", interval);
        editor.commit();
    }

    public int getInterval() {
        return preferences.getInt("INTERVAL", 0);
    }
}
