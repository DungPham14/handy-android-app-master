package com.renosys.handy.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.renosys.handy.alarm.AlarmUtil;
import com.renosys.handy.data.task.Detail;
import com.renosys.handy.data.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by linhnm on 11/9/2017.
 */

public class TaskUti {

    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private final static String STT_UNFINISH = "unfinish";
    private final static String TAG = TaskUti.class.getSimpleName();

    private static TaskUti instance = null;

    public static TaskUti getInstance() {
        if (instance == null) {
            instance = new TaskUti();
        }
        return instance;
    }

    private Gson gson;

    private TaskUti() {
        gson = new Gson();
    }

    public String getJson(Task entry) {
        return gson.toJson(entry);
    }

    public Task getObject(String json) {
        return gson.fromJson(json, Task.class);
    }

    public Task getObjectMock() {
        String json = "{\"id\":1,\"table\":{\"id\":1,\"visitorId\":1,\"name\":\"TBL-001\",\"status\":\"" + "assignable\"},\"priority\":\"high\",\"timestamp\":\"2017-09-01T12:00:00+09:00\",\"elapsed\":3," + "\"details\":[{\"id\":1,\"orderDetail\":{\"id\":1,\"menuCode\":\"MENU-001\",\"quantity\":1," + "\"options\":[{\"typeCode\":\"TYPE-001\",\"itemCode\":\"ITEM-001\"}],\"addons\":[{}]," + "\"sets\":[{\"frameCode\":\"FRM-001\",\"detail\":{}}],\"timing\":\"earlier\",\"status\":\"" + "waiting\",\"canceledAt\":\"2017-09-01T12:00:00+09:00\",\"deliveredAt\":\"2017-09-01T12:00:00+09:00\"" + "},\"title\":\"Title\",\"status\":\"unfinished\"},{\"id\":2,\"orderDetail\":{\"id\":1,\"menuCode\":" + "\"MENU-002\",\"quantity\":1,\"options\":[{\"typeCode\":\"TYPE-002\",\"itemCode\":\"ITEM-002\"}]," + "\"addons\":[{}],\"sets\":[{\"frameCode\":\"FRM-001\",\"detail\":{}}],\"timing\":\"earlier\",\"status\":" + "\"waiting\",\"canceledAt\":\"2017-09-01T12:00:00+09:00\",\"deliveredAt\":\"2017-09-01T12:00:00+09:00\"" + "},\"title\":\"Title\",\"status\":\"unfinished\"}]}";
        return getObject(json);
    }

    public int countNewTask(Task task) {
        if (task == null) return 0;

        int count = 0;
        String timeStamp = task.getTimestamp();
        if (checkTime(timeStamp)) {
            Log.d(TAG, "checkTime: timeServer > lastTimeStamp");
            for (Detail detail : task.getDetails()) {
                if (detail.getStatus().equals(STT_UNFINISH)) {
                    count++;
                }
            }
        } else {
            Log.d(TAG, "checkTime: timeServer <= lastTimeStamp");
        }

        // // TODO: 11/9/2017 fake api
        Random random = new Random();
        count = random.nextInt(10);

        return count;
    }//// check follow version 1: time stamp vs status

    public int countNewTask(Context context, Task task) {
        if (task == null) return 0;

        int count = 0;

        for (Detail detail : task.getDetails()) {
            if (detail.getStatus().equals(STT_UNFINISH)) {
                count++;
            }
        }

        // TODO: 11/9/2017 fake api
        Random random = new Random();
        count = random.nextInt(10);
        return count;

        /*int lastCount = AppSetting.getInstance(context).getCountNewTask();
        if (count > lastCount) {
            AppSetting.getInstance(context).setCountNewTask(count);

            return count;
        } else {
            return 0;
        }*/
    } // check follow version 2: status only

    private boolean checkTime(String timeStamp) {
        long currentTime = System.currentTimeMillis();
        long lastTimeStamp = currentTime - AlarmUtil.TIME_INTERVAL;
        long timeServer = convertDateToMiliSecond(timeStamp);
        Log.d(TAG, "checkTime: lastTimeStamp: " + lastTimeStamp);
        Log.d(TAG, "checkTime: timeServer: " + timeServer);
        return (timeServer > lastTimeStamp);
    }

    private long convertDateToMiliSecond(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            Date date = simpleDateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
