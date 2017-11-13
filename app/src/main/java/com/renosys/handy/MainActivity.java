package com.renosys.handy;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.renosys.handy.alarm.AlarmBroadcastReceiver;
import com.renosys.handy.alarm.AlarmUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static java.util.TimeZone.getDefault;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // UI references.
    private EditText mIPView;
    private WebView mWebView;

    // the time repeat alarm for everyday
    private static long TIME_REPEAT = 24 * 60 * 60 * 1000;

    private boolean isNotify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Hide the status bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);

        mIPView = (EditText) findViewById(R.id.txt_ip);

        mWebView = (WebView) findViewById(R.id.webView);

        String ipAddress = AppSetting.getInstance(this).getIpAddress();
        try {
            isNotify = getIntent().getBooleanExtra(AlarmBroadcastReceiver.NOTIFICATION_EXTRA, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: isNotify: " + isNotify);
        Log.d(TAG, "onCreate: isExistAlarmTask: " + AlarmUtil.isExistAlarm(this));

        if (ipAddress != null) {
            mIPView.setText(ipAddress);
            mWebView.setVisibility(View.VISIBLE);

            loadingWebview(ipAddress);

        }
        else {

            mWebView.setVisibility(View.GONE);
        }

        Button mNewIPButton = (Button) findViewById(R.id.email_next_button);
        mNewIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSetIP();
            }
        });

        schedulerNotification();
        schedulerReloadapp();

    }

    /*
    *
    * scheduler reload app
    * at 10am app will reload
    *
    * */
    private void schedulerReloadapp() {

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent reload = new Intent(this, MainActivity.class);
        reload.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent = PendingIntent.getActivity(this, 0,
                reload, PendingIntent.FLAG_UPDATE_CURRENT );

        // setting calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());

        // set alarm reload app at 10am everyday
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        long startUpTime = calendar.getTimeInMillis();

        if (System.currentTimeMillis() > startUpTime) {
            startUpTime = startUpTime + TIME_REPEAT;
        }

        // setRepeating lets you specify a precise custom interval--in this case, 1 day

        alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                startUpTime,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        );
    }

    /*
    *
    * scheduler notification display me
    *
    * */
    private void schedulerNotification() {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        alarmIntent = PendingIntent.getBroadcast(this,
                0, notificationIntent, 0);

        // Set alarm to start at 9.59 AM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(getDefault());
        calendar.setTimeInMillis(System.currentTimeMillis());

        // set alarm display notification at 9.59am everyday
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 00);

        long startUpTime = calendar.getTimeInMillis();
        if (System.currentTimeMillis() > startUpTime) {
            startUpTime = startUpTime + TIME_REPEAT;
        }

        // setRepeating lets you specify a precise custom interval--in this case, 1 day

        alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                startUpTime,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        );

    }


    /**
     *
     * class connection between app and webview
     **/
    public class WebAppInterface {

        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * event onclick button Host Registration in webview
         */
        @JavascriptInterface
        public void performClick(String _id) {
            startActivity(new Intent(MainActivity.this, UpdateIPActivity.class));
        }

        @JavascriptInterface
        public void setAllData(String data) {
            Log.d(TAG, "WebAppInterface: setAllData: " + data);
            setData(data);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    /**
     *
     *  loading webview
     *
     * **/
    private void loadingWebview(String ipAddress) {

        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        final Activity activity = this;

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

                AlertDialog dialog = new AlertDialog.Builder(view.getContext()).
                        setTitle("").
                        setMessage(message).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        }).create();
                dialog.show();
                result.confirm();
                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
                AppSetting.getInstance(MainActivity.this).setIpAddress(null);
                mWebView.setVisibility(View.GONE);

            }

//

//            public void onPageFinished(WebView view, String url) {
////                dialog.dismiss();
//            }

        });

        if (isNotify) {
            mWebView.loadUrl(ipAddress + "?goToTask=1");
        } else {
            mWebView.loadUrl(ipAddress);
        }
    }


    /**
     * setting ip
     * **/
    private void attemptSetIP() {

        // Reset errors.
        mIPView.setError(null);
        // Store values at the time of the login attempt.
        String ipAddress = mIPView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(ipAddress)) {
            mIPView.setError(getString(R.string.error_field_required));
            focusView = mIPView;
            cancel = true;
        } else if (!isIP(ipAddress)) {
            mIPView.setError(getString(R.string.error_invalid_ip));
            focusView = mIPView;
            cancel = true;
        }

        if (cancel) {

            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();

        } else {

            AppSetting.getInstance(this).setIpAddress(ipAddress);

            mWebView.setVisibility(View.VISIBLE);
            loadingWebview(ipAddress);

        }
    }


    /*
    *
    * check ip
    * */
    private boolean isIP(String input) {

        if (input.contains(".") && input.length() > 1) {
            String ip = input.replace(".", "").
                    trim().replace("https://", "").
                    trim().replace("http://", "").
                    trim().replace(":", "").
                    trim();
            Log.v("MyActivity", ip);

            return TextUtils.isDigitsOnly(ip);

        }
        else {
            return false;
        }
    }

    private void setData(String data) {
        Log.d(TAG, "setData: " + data);

        int interval = 0;
        boolean isChangeInterval = false;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String intervalStr = jsonObject.getString("pollingInterval");
            interval = Integer.valueOf(intervalStr);
            if (AppSetting.getInstance(this).getInterval() == 0) {
                AppSetting.getInstance(this).setInterval(interval);
            } else if (AppSetting.getInstance(this).getInterval() != interval) {
                isChangeInterval = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        if (!AlarmUtil.isExistAlarm(this) || isChangeInterval) {
//            AlarmUtil.startAlarm(this, interval*1000);
//        }
        AlarmUtil.startAlarm(this, interval*1000);
    }
}
