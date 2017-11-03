//package com.renosys.handy;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import java.util.Calendar;
//import java.util.TimeZone;
//
//import static android.content.Context.ALARM_SERVICE;
//
///**
// * Created by cpu0131 on 03/11/2017.
// */
//
//public class SchedulerReload {
//    Context c;
//    // alarm Service
//    AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
//
//    Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//        notificationIntent.addCategory("android.intent.category.DEFAULT");
//
//    PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//    Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 15);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
//}
