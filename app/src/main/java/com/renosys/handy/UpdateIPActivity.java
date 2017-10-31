package com.renosys.handy;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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


        Button mUpdateButton = (Button) findViewById(R.id.update_button);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
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
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.my_ip_address_string), MODE_PRIVATE).edit();
            editor.putString(getString(R.string.my_ip_address_string), newIP);
            editor.apply();
            Toast.makeText(UpdateIPActivity.this, getString(R.string.msg_ip_updated),Toast.LENGTH_LONG).show();

            mCurrentIPView.setText(newIP);
            mNewIPView.setText("");
        }
    }

    private boolean isIP(String input) {

        if (input.contains(".") && input.length()>1) {
            return TextUtils.isDigitsOnly( input.replace(".", "").trim() );
        }
        else {
            return false;
        }
    }
}