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
import android.widget.EditText;
import android.widget.Toast;

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

public class EditProfile extends AppCompatActivity {

    public Toolbar toolbar;
    private EditText editText_name, editText_email, editText_password, editText_confirm_password, editText_phoneNo/*,editText_address*/;
    private String profileId;
    private AVLoadingIndicatorView progressBar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Method.forceRTLIfSupported(getWindow(), EditProfile.this);

        Intent intent = getIntent();
        String set_name = intent.getStringExtra("name");
        String set_email = intent.getStringExtra("email");
        String set_phone = intent.getStringExtra("phone");
        profileId = intent.getStringExtra("profileId");

        toolbar = (Toolbar) findViewById(R.id.toolbar_edit_profile);
        toolbar.setTitle(getResources().getString(R.string.edit_profile));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progressbar_edit_profile);
        editText_name = (EditText) findViewById(R.id.editText_name_edit_profile);
        editText_email = (EditText) findViewById(R.id.editText_email_edit_profile);
        editText_password = (EditText) findViewById(R.id.editText_password_edit_profile);
        editText_confirm_password = (EditText) findViewById(R.id.editText_confirm_password_edit_profile);
        editText_phoneNo = (EditText) findViewById(R.id.editText_phone_edit_profile);

        progressBar.hide();

        editText_name.setText(set_name);
        editText_email.setText(set_email);
        editText_phoneNo.setText(set_phone);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_save:

                String name = editText_name.getText().toString();
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();
                String confirm_password = editText_confirm_password.getText().toString();
                String phoneNo = editText_phoneNo.getText().toString();

                editText_name.setError(null);
                editText_email.setError(null);
                editText_password.setError(null);
                editText_confirm_password.setError(null);
                editText_phoneNo.setError(null);

                if (name.isEmpty() || name.equals("")) {
                    editText_name.requestFocus();
                    editText_name.setError(getResources().getString(R.string.please_enter_name));
                } else if (!isValidMail(email) || email.isEmpty()) {
                    editText_email.requestFocus();
                    editText_email.setError(getResources().getString(R.string.please_enter_email));
                } else if (password.isEmpty() || password.equals("")) {
                    editText_password.requestFocus();
                    editText_password.setError(getResources().getString(R.string.please_enter_password));
                } else if (confirm_password.isEmpty() || confirm_password.equals("")) {
                    editText_confirm_password.requestFocus();
                    editText_confirm_password.setError(getResources().getString(R.string.please_enter_confirm_password));
                } else if (phoneNo.isEmpty() || phoneNo.equals("")) {
                    editText_phoneNo.requestFocus();
                    editText_phoneNo.setError(getResources().getString(R.string.please_enter_phone));
                } else if (!password.equals(confirm_password)) {
                    Toast.makeText(this, getResources().getString(R.string.password_and_confirmpassword_does_not_match), Toast.LENGTH_SHORT).show();
                } else {
                    profileUpdate(profileId, name, email, password, phoneNo);
                }

                break;

            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return true;
    }

    public void profileUpdate(String id, String sendName, String sendEmail, String sendPassword, String sendPhone) {

        progressBar.show();

        String profileUpdate = Constant_Api.profileUpdate + id + "&name=" + sendName + "&email=" + sendEmail + "&password=" + sendPassword + "&phone=" + sendPhone;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(profileUpdate, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("EBOOK_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String msg = object.getString("msg");
                        String success = object.getString("success");

                        if (success.equals("1")) {
                            Toast.makeText(EditProfile.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditProfile.this, Profile.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            Toast.makeText(EditProfile.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    }

                    progressBar.hide();

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
