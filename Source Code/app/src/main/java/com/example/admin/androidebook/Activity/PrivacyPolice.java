package com.example.admin.androidebook.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.Method;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by admin on 29-06-2017.
 */

public class PrivacyPolice extends AppCompatActivity {

    public Toolbar toolbar;
    private TextView privacy_policy_textview;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        Method.forceRTLIfSupported(getWindow(), PrivacyPolice.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_privacy_policy);
        toolbar.setTitle(getResources().getString(R.string.privacy_policy));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        privacy_policy_textview = (TextView) findViewById(R.id.textview_privacy_policy);
        if(Method.isNetworkAvailable(PrivacyPolice.this)){
            privacy_policy_textview.setText(Html.fromHtml(Constant_Api.aboutUsList.getApp_privacy_policy()));
        }else {
            Toast.makeText(this,getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
