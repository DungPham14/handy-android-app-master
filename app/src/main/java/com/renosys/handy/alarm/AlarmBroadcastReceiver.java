package com.renosys.handy.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.renosys.handy.AppConstant;
import com.renosys.handy.MainActivity;
import com.renosys.handy.R;
import com.renosys.handy.data.TaskUti;
import com.renosys.handy.data.task.Task;

/**
 * Created by linhnm on 11/9/2017.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmBroadcastReceiver.class.getSimpleName();
    public static final String NOTIFICATION_EXTRA = "Notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        Task task = TaskUti.getInstance().getObjectMock();
        Log.d(TAG, "onReceive: TASK: " + task);
        int msgNew = TaskUti.getInstance().countNewTask(context, task);
        Log.d(TAG, "onReceive: msgNew: " + msgNew);
        if (msgNew > 0) {
            StringBuilder builder = new StringBuilder();
            //builder.append("Handy App: ");
            builder.append(msgNew);
            builder.append(" new tasks");
            sendNotification(context, builder.toString());
        }
    }

    private void sendNotification(Context context, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(NOTIFICATION_EXTRA, true);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                AppConstant.NOTIFICATION_TASK_REQUEST_CODE /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Handy App")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(AppConstant.NOTIFICATION_TASK_ID /* ID of notification */, notificationBuilder.build());
        wakeScreen(context);
    }

    private void wakeScreen(Context context) {
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();
    }
}
