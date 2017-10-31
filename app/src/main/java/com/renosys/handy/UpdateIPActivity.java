package com.renosys.handy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateIPActivity extends AppCompatActivity {

    private EditText mCurrentIPView;
    private EditText mNewIPView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ip);

        // Set up the login form.
        mCurrentIPView = (EditText) findViewById(R.id.txt_current_ip);


        SharedPreferences prefs = getSharedPreferences(getString(R.string.my_ip_address_string), MODE_PRIVATE);
        String ipAddress = prefs.getString(getString(R.string.my_ip_address_string), null);
        mCurrentIPView.setText(ipAddress);
        mCurrentIPView.setFocusable(false);
        mNewIPView = (EditText) findViewById(R.id.txt_new_ip);

        // update button
        Button mUpdateButton = (Button) findViewById(R.id.update_button);
        mUpdateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });


        // back button
        Button mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

    }

    private void attemptUpdate() {

        // Reset errors.
        mCurrentIPView.setError(null);
        mNewIPView.setError(null);

        // Store values at the time of the login attempt.
        String currentIP = mCurrentIPView.getText().toString();
        String newIP = mNewIPView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(currentIP)) {
            mCurrentIPView.setError(getString(R.string.error_field_required));
            focusView = mCurrentIPView;
            cancel = true;

        }else if (!isIP(currentIP)) {
            mCurrentIPView.setError(getString(R.string.error_invalid_ip));
            focusView = mCurrentIPView;
            cancel = true;
        }

        // Check for a valid ip address.
        if (TextUtils.isEmpty(newIP)) {

            mNewIPView.setError(getString(R.string.error_field_required));
            focusView = mNewIPView;
            cancel = true;

        } else if (!isIP(newIP)) {
            mNewIPView.setError(getString(R.string.error_invalid_ip));
            focusView = mNewIPView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // save new ip by SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.my_ip_address_string), MODE_PRIVATE).edit();
            editor.putString(getString(R.string.my_ip_address_string), newIP);
            editor.apply();
            Toast.makeText(UpdateIPActivity.this, getString(R.string.msg_ip_updated),Toast.LENGTH_LONG).show();

            // reload activity
            //onBackPressed();
            startActivity(new Intent(UpdateIPActivity.this, MainActivity.class));
        }
    }


    /**
     *
     * function check for ip
     * **/
    private boolean isIP(String input) {

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