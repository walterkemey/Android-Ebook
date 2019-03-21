package com.example.admin.androidebook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.Method;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Register extends AppCompatActivity {

    private EditText editText_name, editText_email, editText_password, editText_phoneNo;
    private String name, email, password, phoneNo;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Method.forceRTLIfSupported(getWindow(), Register.this);

        editText_name = (EditText) findViewById(R.id.editText_name_register);
        editText_email = (EditText) findViewById(R.id.editText_email_register);
        editText_password = (EditText) findViewById(R.id.editText_password_register);
        editText_phoneNo = (EditText) findViewById(R.id.editText_phoneNo_register);

        TextView textView_login = (TextView) findViewById(R.id.textView_login_register);

        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
                finishAffinity();
            }
        });

        Button button_submit = (Button) findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editText_name.getText().toString();
                email = editText_email.getText().toString();
                password = editText_password.getText().toString();
                phoneNo = editText_phoneNo.getText().toString();

                form();

            }
        });

    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void form() {

        editText_name.setError(null);
        editText_email.setError(null);
        editText_password.setError(null);
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
        } else if (phoneNo.isEmpty() || phoneNo.equals("")) {
            editText_phoneNo.requestFocus();
            editText_phoneNo.setError(getResources().getString(R.string.please_enter_phone));
        } else {
            editText_name.setText("");
            editText_email.setText("");
            editText_password.setText("");
            editText_phoneNo.setText("");
            if(Method.isNetworkAvailable(Register.this)){
                register(name, email, password, phoneNo);
            }else {
                Toast.makeText(this,getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void register(String sendName, String sendEmail, String sendPassword, String sendPhone) {

        String register = Constant_Api.register + "name=" + sendName + "&email=" + sendEmail + "&password=" + sendPassword + "&phone=" + sendPhone;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(register, null, new AsyncHttpResponseHandler() {
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
                            Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
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
