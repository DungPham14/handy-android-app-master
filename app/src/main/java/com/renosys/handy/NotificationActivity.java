package com.renosys.handy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * Created by cpu0131 on 03/11/2017.
 */

public class NotificationActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                mTextField.setText("done!");
        startActivity(new Intent(NotificationActivity.this, MainActivity.class));
//            }
//        }.start();



    }
}
