package com.renosys.handy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by cpu0131 on 03/11/2017.
 */

public class NotificationActivity extends Activity{
    TextView mTextField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        mTextField = (TextView) findViewById(R.id.reload);
//        startActivity(new Intent( NotificationActivity.this, MainActivity.class ) );
        finish();
        startActivity(getIntent());
    }
}
