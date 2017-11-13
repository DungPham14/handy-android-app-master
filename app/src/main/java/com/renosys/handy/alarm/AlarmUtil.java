package com.renosys.handy.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.renosys.handy.AppConstant;

/**
 * Created by linhnm on 11/9/2017.
 */

public class AlarmUtil {
    private final static String TAG = AlarmUtil.class.getSimpleName();
    public final static int TIME_INTERVAL = 10000;

    public static void startAlarm(Context context, int time) {
        long currentTime = System.currentTimeMillis();
        Log.d(TAG, "startAlarm: currentTime: " + currentTime);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AppConstant.ALARM_TASK_REQUEST_CODE, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int timeInterval = time == 0 ? TIME_INTERVAL : time;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentTime + timeInterval, timeInterval, pendingIntent);

        // Hopefully your alarm will have a lower frequency than this!
        /*alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + TIME_INTERVAL,
                TIME_INTERVAL, pendingIntent);*/

    }

    public static void stopAlarm(Context context) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AppConstant.ALARM_TASK_REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public static boolean isExistAlarm(Context context) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, AppConstant.ALARM_TASK_REQUEST_CODE, intent, 0);
        return (pendingIntent != null);
    }
}
