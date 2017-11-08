package com.renosys.handy;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by cpu0131 on 06/11/2017.
 */

public class Dialog extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dialog = new Intent(context, MainActivity.class );
        dialog.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP );
        context.startActivity( dialog );

//
    }
}
