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
        Intent dialog = new Intent(context, DialogActivity.class );

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack( DialogActivity.class );
        stackBuilder.addNextIntent(dialog);

        PendingIntent pendingIntent = stackBuilder
                .getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT );

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("HANDY");
        alertDialog.setMessage("00:10");
//        alertDialog.set
        alertDialog.show();   //

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                alertDialog.setCanceledOnTouchOutside(false);

                alertDialog.setMessage(" App will reload in 1 minute " + " 00: " + (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                alertDialog.cancel();

            }
        }.start();
    }
}
