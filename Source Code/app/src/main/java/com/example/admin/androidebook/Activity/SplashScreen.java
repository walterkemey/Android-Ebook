package com.example.admin.androidebook.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.Method;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SplashScreen extends AppCompatActivity {

    // splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    private Method method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace_screen);

        method = new Method(SplashScreen.this);
        method.login();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Method.forceRTLIfSupported(getWindow(), SplashScreen.this);

        if (Method.isNetworkAvailable(SplashScreen.this)) {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    if (method.pref.getBoolean(method.pref_login, false)) {
                        Log.d("value", String.valueOf(method.pref.getBoolean(method.pref_login, false)));
                        login(method.pref.getString(method.userEmail, null), method.pref.getString(method.userPassword, null));
                    } else {
                        Intent i = new Intent(SplashScreen.this, Login.class);
                        startActivity(i);
                    }
                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            method.editor.putBoolean(method.pref_login, false);
            method.editor.commit();
            startActivity(new Intent(SplashScreen.this, Login.class));

        }
    }

    public void login(final String sendEmail, final String sendPassword) {

        String login = Constant_Api.login + "&email=" + sendEmail + "&password=" + sendPassword;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(login, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("EBOOK_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            method.editor.putBoolean(method.pref_login, false);
                            method.editor.commit();
                            startActivity(new Intent(SplashScreen.this, Login.class));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
