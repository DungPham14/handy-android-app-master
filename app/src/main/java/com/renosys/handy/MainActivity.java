package com.renosys.handy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import static android.Manifest.permission.READ_CONTACTS;


public class MainActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */


    // UI references.
    private EditText mIPView;
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mIPView = (EditText) findViewById(R.id.txt_ip);

        mWebView = (WebView) findViewById(R.id.webView);
//        mWebView.clearCache(true);
//        mWebView.clearHistory();
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.getSettings().setAppCacheEnabled(true);
//        mWebView.getSettings().setDomStorageEnabled(true);
//        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
//        mWebView.loadUrl("file:///android_asset/home");
        SharedPreferences prefs = getSharedPreferences(getString(R.string.my_ip_address_string), MODE_PRIVATE);
        String ipAddress = prefs.getString(getString(R.string.my_ip_address_string), null);
        if (ipAddress != null){
            mIPView.setText(ipAddress);
            mWebView.setVisibility(View.VISIBLE);
            loadingWebview(ipAddress);


        }else {

            mWebView.setVisibility(View.GONE);
        }

        Button mNewIPButton = (Button) findViewById(R.id.email_next_button);
        mNewIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSetIP();
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences prefs = getSharedPreferences(getString(R.string.my_ip_address_string), MODE_PRIVATE);
//        String ipAddress = prefs.getString(getString(R.string.my_ip_address_string), null);
//        if (ipAddress != null){
//            mIPView.setText(ipAddress);
//            mWebView.setVisibility(View.VISIBLE);
//            loadingWebview(ipAddress);
//
//
//        }else {
//
//            mWebView.setVisibility(View.GONE);
//        }
//
//        Button mNewIPButton = (Button) findViewById(R.id.email_next_button);
//        mNewIPButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptSetIP();
//            }
//        });
//    }

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


//            Intent i = new Intent(MainActivity.this, UpdateIPActivity.class);
//            startActivityForResult(i, );
            startActivity(new Intent(MainActivity.this, UpdateIPActivity.class));
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private  void loadingWebview(String ipAddress){
//        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "", true);
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
            } });

        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.my_ip_address_string), MODE_PRIVATE).edit();
                editor.remove(getString(R.string.my_ip_address_string));
                editor.apply();
                mWebView.setVisibility(View.GONE);

            }

//

//            public void onPageFinished(WebView view, String url) {
////                dialog.dismiss();
//            }

    });

        mWebView.loadUrl(ipAddress);
    }
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
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
//
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.my_ip_address_string), MODE_PRIVATE).edit();
            editor.putString(getString(R.string.my_ip_address_string), ipAddress);
            editor.apply();

//
            mWebView.setVisibility(View.VISIBLE);

//            loadingWebview(ipAddress);
        }
    }

    private boolean isIP( String input ) {

        if ( input.contains(".") && input.length() > 1 ) {
            String ip = input.replace(".", "").trim().replace("https://","").trim().replace("http://","").trim().replace(":","").trim();
            Log.v( "MyActivity", ip );
            return TextUtils.isDigitsOnly( ip);
        }
        else {
            return false;
        }
    }

}

