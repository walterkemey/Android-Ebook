package com.example.admin.androidebook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.Method;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Profile extends AppCompatActivity {

    public Toolbar toolbar;
    private TextView textViewName, textViewEmail, textViewPhone;
    private String user_id, name, email, phone, success;
    private Method method;
    private AVLoadingIndicatorView progressBar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Method.forceRTLIfSupported(getWindow(), Profile.this);
        method = new Method(Profile.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        toolbar.setTitle(getResources().getString(R.string.edit_profile));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progressbar_profile);
        textViewName = (TextView) findViewById(R.id.textView_name_profile);
        textViewEmail = (TextView) findViewById(R.id.textView_email_profile);
        textViewPhone = (TextView) findViewById(R.id.textView_phone_profile);

        progressBar.hide();

        profile(method.pref.getString(method.profileId, null));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_edit:
                startActivity(new Intent(Profile.this, EditProfile.class)
                        .putExtra("name", name)
                        .putExtra("email", email)
                        .putExtra("phone", phone)
                        .putExtra("profileId", method.pref.getString(method.profileId, null)));
                break;

            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return true;
    }

    public void profile(String id) {

        progressBar.show();

        String profile = Constant_Api.profile + id;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(profile, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("EBOOK_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        user_id = object.getString("user_id");
                        name = object.getString("name");
                        email = object.getString("email");
                        phone = object.getString("phone");
                        success = object.getString("success");

                    }
                    progressBar.hide();
                    textViewName.setText(name);
                    textViewEmail.setText(email);
                    textViewPhone.setText(phone);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.hide();
            }
        });
    }

}
