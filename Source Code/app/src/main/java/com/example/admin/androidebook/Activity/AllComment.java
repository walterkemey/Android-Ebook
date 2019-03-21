package com.example.admin.androidebook.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.Adapter.AllCommentAdapter;
import com.example.admin.androidebook.Item.CommentList;
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

public class AllComment extends AppCompatActivity {

    public Toolbar toolbar;
    private String bookId;
    private Method method;
    private AllCommentAdapter allCommentAdapter;
    private EditText editTextComment;
    private InputMethodManager inputMethodManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comment);

        Method.forceRTLIfSupported(getWindow(), AllComment.this);

        method = new Method(AllComment.this);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");

        toolbar = (Toolbar) findViewById(R.id.toolbar_all_comment);
        toolbar.setTitle(getResources().getString(R.string.toolbar_Title_AllComment));
        setSupportActionBar(toolbar);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textViewNoCommentFound = (TextView) findViewById(R.id.textView_noComment_all_Comment);
        editTextComment = (EditText) findViewById(R.id.EditText_comment_allComment);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_all_comment);

        editTextComment.setFocusable(false);

        textViewNoCommentFound.setVisibility(View.GONE);

        if (Constant_Api.scdLists.get(0).getCommentLists().size() == 0) {
            textViewNoCommentFound.setVisibility(View.VISIBLE);
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllComment.this);
        recyclerView.setLayoutManager(layoutManager);

        allCommentAdapter = new AllCommentAdapter(AllComment.this, Constant_Api.scdLists);
        recyclerView.setAdapter(allCommentAdapter);

        editTextComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (method.pref.getBoolean(method.pref_login, false)) {

                    final Dialog dialog = new Dialog(AllComment.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialogbox_comment);
                    dialog.getWindow().setLayout(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.BOTTOM;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);
                    ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView_dialogBox_comment);
                    final EditText editText = (EditText) dialog.findViewById(R.id.editText_dialogbox_comment);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setError(null);
                            String comment = editText.getText().toString();
                            if (comment.isEmpty()) {
                                editText.requestFocus();
                                editText.setError(getResources().getString(R.string.please_enter_comment));
                            } else {
                                if (Method.isNetworkAvailable(AllComment.this)) {
                                    editText.clearFocus();
                                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                    Comment(method.pref.getString(method.userName, null), comment);
                                } else {
                                    Toast.makeText(AllComment.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }
                    });

                    dialog.show();

                } else {
                    Method.loginBack=true;
                    startActivity(new Intent(AllComment.this, Login.class));
                }


            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void Comment(final String userName, final String comment) {

        String Comment = Constant_Api.commentApi + bookId + "&user_name=" + userName + "&comment_text=" + comment;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Comment, null, new AsyncHttpResponseHandler() {
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
                            Toast.makeText(AllComment.this, msg, Toast.LENGTH_SHORT).show();
                            Constant_Api.scdLists.get(0).getCommentLists().add(0, new CommentList(bookId, method.pref.getString(method.userName, null), comment, getResources().getString(R.string.Today)));
                            allCommentAdapter.notifyDataSetChanged();
                            String buttonTotal = getResources().getString(R.string.button_view_all_scd) + " " + "(" + Constant_Api.scdLists.get(0).getCommentLists().size() + ")";
                            SCDetail.buttonAllComment.setText(buttonTotal);
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
