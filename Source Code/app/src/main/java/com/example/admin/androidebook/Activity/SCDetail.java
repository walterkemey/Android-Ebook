package com.example.admin.androidebook.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.Adapter.CommentAdapter;
import com.example.admin.androidebook.Adapter.RelatedBookAdapterGV;
import com.example.admin.androidebook.DataBase.DatabaseHandler;
import com.example.admin.androidebook.Fragment.HomeFragment;
import com.example.admin.androidebook.Fragment.MostPopularFragment;
import com.example.admin.androidebook.Item.CommentList;
import com.example.admin.androidebook.Item.ScdList;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.DownloadEpub;
import com.example.admin.androidebook.Util.Method;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SCDetail extends AppCompatActivity {

    public Toolbar toolbar;
    private Menu menu;
    private String bookId, type;
    private Method method;
    private boolean isMenu = false;
    private ImageView imageView, imageView_CoverBook;
    private RecyclerView recyclerViewComment, recyclerViewRelatedBook;
    private TextView textView_Description;
    private AVLoadingIndicatorView progressBar;
    private int rate;
    private CommentAdapter commentAdapter;
    private DatabaseHandler db;
    private LinearLayout linearLayoutRelatedBook;
    private TextView textViewNoBookFound, textViewNoCommentFound, textViewBookName, textViewAuthor, textViewRating, textView_view;
    private RatingBar ratingBar;
    private EditText editTextComment;
    public static Button buttonAllComment;
    private InputMethodManager inputMethodManager;
    private int position = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scd);

        Method.forceRTLIfSupported(getWindow(), SCDetail.this);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        type = intent.getStringExtra("type");
        position = intent.getIntExtra("position", 0);

        db = new DatabaseHandler(SCDetail.this);

        method = new Method(SCDetail.this);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar_scd);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageView_scd);
        imageView_CoverBook = (ImageView) findViewById(R.id.imageView_book_scd);
        editTextComment = (EditText) findViewById(R.id.EditText_comment_scd);
        ImageView imageViewRating = (ImageView) findViewById(R.id.imageView_rating_scd);
        buttonAllComment = (Button) findViewById(R.id.button_allComment_scd);
        Button buttonViewAll = (Button) findViewById(R.id.button_viewall_scd);
        textView_Description = (TextView) findViewById(R.id.textView_description_scdetial);
        textViewBookName = (TextView) findViewById(R.id.textView_bookName_scd);
        textViewAuthor = (TextView) findViewById(R.id.textView_authorName_scd);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progresbar_scd);
        recyclerViewRelatedBook = (RecyclerView) findViewById(R.id.recyclerView_relatedBook_scd);
        recyclerViewComment = (RecyclerView) findViewById(R.id.recyclerView_comment_scd);
        linearLayoutRelatedBook = (LinearLayout) findViewById(R.id.linearLayout_relatedBook_scdetail);
        textViewNoBookFound = (TextView) findViewById(R.id.textView_noBookFound_scdetail);
        textViewNoCommentFound = (TextView) findViewById(R.id.textView_noComment_scdetail);
        textViewRating = (TextView) findViewById(R.id.textView_ratingCount_scd);
        textView_view = (TextView) findViewById(R.id.textView_view_scd);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_scd);

        editTextComment.setFocusable(false);

        linearLayoutRelatedBook.setVisibility(View.VISIBLE);
        textViewNoBookFound.setVisibility(View.GONE);
        textViewNoCommentFound.setVisibility(View.GONE);

        recyclerViewComment.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SCDetail.this);
        recyclerViewComment.setLayoutManager(layoutManager);
        recyclerViewComment.setFocusable(false);
        recyclerViewComment.setNestedScrollingEnabled(false);

        recyclerViewRelatedBook.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerRelatedBook = new LinearLayoutManager(SCDetail.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRelatedBook.setLayoutManager(layoutManagerRelatedBook);
        recyclerViewRelatedBook.setFocusable(false);
        recyclerViewRelatedBook.setNestedScrollingEnabled(false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setting the intent for pdf reader
                if (Method.isNetworkAvailable(SCDetail.this)) {

                    if (Constant_Api.scdLists.get(0).getBook_file_url().contains(".epub")) {
                        DownloadEpub downloadEpub = new DownloadEpub(SCDetail.this);
                        downloadEpub.pathEpub(Constant_Api.scdLists.get(0).getBook_file_url(), Constant_Api.scdLists.get(0).getId());
                    } else {
                        startActivity(new Intent(SCDetail.this, PDFShow.class)
                                .putExtra("link", Constant_Api.scdLists.get(0).getBook_file_url())
                                .putExtra("toolbarTitle", Constant_Api.scdLists.get(0).getBook_title())
                                .putExtra("type", "link"));
                    }

                } else {
                    Toast.makeText(SCDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SCDetail.this, AllComment.class)
                        .putExtra("bookId", bookId));
            }
        });

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SCDetail.this, RelatedBook.class));
            }
        });

        imageViewRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (method.pref.getBoolean(method.pref_login, false)) {

                    final Dialog dialog = new Dialog(SCDetail.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialogbox_rating);
                    dialog.getWindow().setLayout(ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                    Button button = (Button) dialog.findViewById(R.id.button_dialogBox_rating);
                    final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar_dialogbox_comment);

                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            rate = (int) rating;
                        }
                    });

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Method.isNetworkAvailable(SCDetail.this)) {
                                rating(rate);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(SCDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    dialog.show();

                } else {
                    Method.loginBack = true;
                    startActivity(new Intent(SCDetail.this, Login.class));
                }


            }
        });

        editTextComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (method.pref.getBoolean(method.pref_login, false)) {

                    final Dialog dialog = new Dialog(SCDetail.this);
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
                                if (Method.isNetworkAvailable(SCDetail.this)) {
                                    editText.clearFocus();
                                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                    Comment(method.pref.getString(method.userName, null), comment);
                                } else {
                                    Toast.makeText(SCDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }
                    });

                    dialog.show();

                } else {
                    Method.loginBack = true;
                    startActivity(new Intent(SCDetail.this, Login.class));
                }


            }
        });

        if (Method.isNetworkAvailable(SCDetail.this)) {
            scd();
        } else {
            Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scd, menu);
        this.menu = menu;
        if (!isMenu) {
            isMenu = true;
            Toolbar_fav();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.menu_favorite_scd:
                if (db.checkId(Constant_Api.scdLists.get(0).getId())) {
                    method.addData(db, 0);
                    item.setIcon(R.drawable.ic_fav_hov);
                } else {
                    db.deleteDetail(Constant_Api.scdLists.get(0).getId());
                    item.setIcon(R.drawable.ic_fav);
                    Toast.makeText(this, getResources().getString(R.string.remove_to_favourite), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.share:
                if (Method.allowPermitionExternalStorage) {
                    Method.share = true;
                    Method.share_save(Constant_Api.scdLists.get(0).getBook_cover_img(),
                            Constant_Api.scdLists.get(0).getBook_title(),
                            Constant_Api.scdLists.get(0).getAuthor_name(),
                            Constant_Api.scdLists.get(0).getBook_description(),
                            Constant_Api.scdLists.get(0).getBook_file_url());

                } else {
                    Toast.makeText(this, getResources().getString(R.string.cannot_use_save_permission), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.read_book:
                if (Method.isNetworkAvailable(SCDetail.this)) {
                    if (Constant_Api.scdLists.get(0).getBook_file_url().contains(".epub")) {
                        DownloadEpub downloadEpub = new DownloadEpub(SCDetail.this);
                        downloadEpub.pathEpub(Constant_Api.scdLists.get(0).getBook_file_url(), Constant_Api.scdLists.get(0).getId());
                    } else {
                        startActivity(new Intent(SCDetail.this, PDFShow.class)
                                .putExtra("link", Constant_Api.scdLists.get(0).getBook_file_url())
                                .putExtra("toolbarTitle", Constant_Api.scdLists.get(0).getBook_title())
                                .putExtra("type", "link"));
                    }

                } else {
                    Toast.makeText(SCDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.download_book:
                if (Method.isNetworkAvailable(SCDetail.this)) {
                    if (db.checkIdPdf(Constant_Api.scdLists.get(0).getId())) {
                        if (Constant_Api.scdLists.get(0).getBook_file_url().contains(".epub")) {
                            method.download(Constant_Api.scdLists.get(0).getId(),
                                    Constant_Api.scdLists.get(0).getBook_title(),
                                    Constant_Api.scdLists.get(0).getBook_cover_img(),
                                    Constant_Api.scdLists.get(0).getAuthor_name(),
                                    Constant_Api.scdLists.get(0).getBook_file_url(), "epub");
                        } else {
                            method.download(Constant_Api.scdLists.get(0).getId(),
                                    Constant_Api.scdLists.get(0).getBook_title(),
                                    Constant_Api.scdLists.get(0).getBook_cover_img(),
                                    Constant_Api.scdLists.get(0).getAuthor_name(),
                                    Constant_Api.scdLists.get(0).getBook_file_url(), "pdf");
                        }

                    } else {
                        Toast.makeText(SCDetail.this, getResources().getString(R.string.you_have_allready_download_book), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SCDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void Toolbar_fav() {
        if (db.checkId(bookId)) {
            menu.findItem(R.id.menu_favorite_scd).setIcon(ContextCompat.getDrawable(SCDetail.this, R.drawable.ic_fav));
        } else {
            menu.findItem(R.id.menu_favorite_scd).setIcon(ContextCompat.getDrawable(SCDetail.this, R.drawable.ic_fav_hov));
        }
    }

    public void scd() {

        Constant_Api.scdLists.clear();

        progressBar.show();

        String url = Constant_Api.detail + bookId;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("EBOOK_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String book_title = object.getString("book_title");
                        String book_description = object.getString("book_description");
                        String book_cover_img = object.getString("book_cover_img");
                        String book_bg_img = object.getString("book_bg_img");
                        String book_file_type = object.getString("book_file_type");
                        String book_file_url = object.getString("book_file_url");
                        String total_rate = object.getString("total_rate");
                        String rate_avg = object.getString("rate_avg");
                        String book_views = object.getString("book_views");
                        String author_name = object.getString("author_name");

                        JSONArray jsonArrayRb = object.getJSONArray("related_books");
                        List<ScdList> arrayListRb = new ArrayList<>();

                        if (jsonArrayRb.length() != 0) {

                            for (int j = 0; j < jsonArrayRb.length(); j++) {

                                JSONObject objectRb = jsonArrayRb.getJSONObject(j);
                                String idRb = objectRb.getString("id");
                                String book_titleRb = objectRb.getString("book_title");
                                String book_descriptionRb = objectRb.getString("book_description");
                                String book_cover_imgRb = objectRb.getString("book_cover_img");
                                String book_file_typeRb = objectRb.getString("book_file_type");
                                String total_rateRb = objectRb.getString("total_rate");
                                String rate_avgRb = objectRb.getString("rate_avg");
                                String book_viewsRb = objectRb.getString("book_views");
                                String author_nameRb = objectRb.getString("author_name");

                                arrayListRb.add(new ScdList(idRb, book_titleRb, book_descriptionRb, Constant_Api.image + book_cover_imgRb, book_file_typeRb, total_rateRb, rate_avgRb, book_viewsRb, author_nameRb));

                            }
                        }

                        JSONArray jsonArrayComment = object.getJSONArray("user_comments");
                        List<CommentList> arrayList = new ArrayList<>();

                        if (jsonArrayComment.length() != 0) {

                            for (int j = 0; j < jsonArrayComment.length(); j++) {

                                JSONObject objectComment = jsonArrayComment.getJSONObject(j);

                                String book_id = objectComment.getString("book_id");
                                String user_name = objectComment.getString("user_name");
                                String comment_text = objectComment.getString("comment_text");
                                String dt_rate = objectComment.getString("dt_rate");

                                arrayList.add(new CommentList(book_id, user_name, comment_text, dt_rate));

                            }
                        }

                        Constant_Api.scdLists.add(new ScdList(id, book_title, book_description, Constant_Api.image + book_cover_img, Constant_Api.image + book_bg_img, book_file_type, book_file_url, total_rate, rate_avg, book_views, author_name, arrayListRb, arrayList));

                    }

                    int value = Integer.parseInt(Constant_Api.scdLists.get(0).getBook_views());
                    value++;
                    Constant_Api.scdLists.get(0).setBook_views(String.valueOf(value));
                    if (type.equals("search")) {
                        Search.subCategoryListsSearch.get(position).setBook_views(String.valueOf(value));
                    } else if (type.equals("related")) {
                        Constant_Api.scdLists.get(0).getScdLists().get(position).setBook_views(String.valueOf(value));
                    } else if (type.equals("latest")) {
                        Constant_Api.subCategoryLists.get(position).setBook_views(String.valueOf(value));
                    } else if (type.equals("home_latest")) {
                        HomeFragment.latestList.get(position).setBook_views(String.valueOf(value));
                        Log.d("value", String.valueOf(value));
                    } else if (type.equals("home_most")) {
                        HomeFragment.mostPopularList.get(position).setBook_views(String.valueOf(value));
                    } else if (type.equals("most_popular")) {
                        MostPopularFragment.mostPopularList.get(position).setBook_views(String.valueOf(value));
                    } else if (type.equals("slider")) {
                        HomeFragment.sliderList.get(position).setBook_views(String.valueOf(value));
                    }

                    toolbar.setTitle(Constant_Api.scdLists.get(0).getBook_title());

                    Picasso.with(SCDetail.this).load(Constant_Api.scdLists.get(0).getBook_cover_img())
                            .placeholder(R.drawable.placeholder_portable)
                            .into(imageView_CoverBook);
                    Picasso.with(SCDetail.this).load(Constant_Api.scdLists.get(0).getBook_bg_img())
                            .placeholder(R.drawable.placeholder_portable)
                            .into(imageView);
                    textView_Description.setText(Html.fromHtml(Constant_Api.scdLists.get(0).getBook_description()));
                    textViewBookName.setText(Constant_Api.scdLists.get(0).getBook_title());
                    textViewAuthor.setText(Constant_Api.scdLists.get(0).getAuthor_name());
                    textViewRating.setText(Constant_Api.scdLists.get(0).getTotal_rate());
                    textView_view.setText(Constant_Api.scdLists.get(0).getBook_views());
                    ratingBar.setRating(Float.parseFloat(Constant_Api.scdLists.get(0).getRate_avg()));

                    String buttonTotal = getResources().getString(R.string.button_view_all_scd) + " " + "(" + Constant_Api.scdLists.get(0).getCommentLists().size() + ")";
                    buttonAllComment.setText(buttonTotal);

                    if (Constant_Api.scdLists.get(0).getScdLists().size() == 0) {
                        linearLayoutRelatedBook.setVisibility(View.GONE);
                        textViewNoBookFound.setVisibility(View.VISIBLE);
                    } else {
                        RelatedBookAdapterGV relatedBookAdapterGV = new RelatedBookAdapterGV(SCDetail.this, Constant_Api.scdLists.get(0).getScdLists());
                        recyclerViewRelatedBook.setAdapter(relatedBookAdapterGV);
                    }

                    if (Constant_Api.scdLists.get(0).getCommentLists().size() == 0) {
                        textViewNoCommentFound.setVisibility(View.VISIBLE);
                    } else {
                        commentAdapter = new CommentAdapter(SCDetail.this, Constant_Api.scdLists);
                        recyclerViewComment.setAdapter(commentAdapter);
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

    public void rating(final int rate) {

        @SuppressLint("HardwareIds") String deviceId = Settings.Secure.getString(SCDetail.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        String rating = Constant_Api.ratingApi + bookId + "&device_id=" + deviceId + "&user_id=" + method.pref.getString(method.profileId, null) + "&rate=" + rate;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(rating, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("EBOOK_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String MSG = object.getString("MSG");

                        if ("You have already rated".equals(MSG)) {

                        } else {
                            String total_rate = object.getString("total_rate");
                            String rate_avg = object.getString("rate_avg");
                            ratingBar.setRating(Float.parseFloat(rate_avg));
                            Constant_Api.scdLists.get(0).setRate_avg(String.valueOf(rate_avg));
                            Constant_Api.scdLists.get(0).setTotal_rate(String.valueOf(total_rate));
                            textViewRating.setText(total_rate);
                            if (type.equals("search")) {
                                Search.subCategoryListsSearch.get(position).setTotal_rate(String.valueOf(total_rate));
                                Search.subCategoryListsSearch.get(position).setRate_avg(String.valueOf(rate_avg));
                            } else if (type.equals("related")) {
                                Constant_Api.scdLists.get(0).getScdLists().get(position).setTotal_rate(String.valueOf(total_rate));
                                Constant_Api.scdLists.get(0).getScdLists().get(position).setRate_avg(String.valueOf(rate_avg));
                            } else if (type.equals("latest")) {
                                Constant_Api.subCategoryLists.get(position).setTotal_rate(String.valueOf(total_rate));
                                Constant_Api.subCategoryLists.get(position).setRate_avg(String.valueOf(rate_avg));
                            } else if (type.equals("home_latest")) {
                                HomeFragment.latestList.get(position).setTotal_rate(String.valueOf(total_rate));
                                HomeFragment.latestList.get(position).setRate_avg(String.valueOf(rate_avg));
                            } else if (type.equals("home_most")) {
                                HomeFragment.mostPopularList.get(position).setTotal_rate(String.valueOf(total_rate));
                                HomeFragment.mostPopularList.get(position).setRate_avg(String.valueOf(rate_avg));
                            } else if (type.equals("most_popular")) {
                                MostPopularFragment.mostPopularList.get(position).setTotal_rate(String.valueOf(total_rate));
                                MostPopularFragment.mostPopularList.get(position).setRate_avg(String.valueOf(rate_avg));
                            } else if (type.equals("slider")) {
                                Method.slider = true;
                                HomeFragment.sliderList.get(position).setTotal_rate(String.valueOf(total_rate));
                                HomeFragment.sliderList.get(position).setRate_avg(String.valueOf(rate_avg));
                            }
                        }

                        Toast.makeText(SCDetail.this, MSG, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SCDetail.this, msg, Toast.LENGTH_SHORT).show();
                            if (commentAdapter == null) {
                                commentAdapter = new CommentAdapter(SCDetail.this, Constant_Api.scdLists);
                                recyclerViewComment.setAdapter(commentAdapter);
                                Constant_Api.scdLists.get(0).getCommentLists().add(new CommentList(bookId, method.pref.getString(method.userName, null), comment, getResources().getString(R.string.Today)));
                                commentAdapter.notifyDataSetChanged();
                                String buttonTotal = getResources().getString(R.string.button_view_all_scd) + " " + "(" + Constant_Api.scdLists.get(0).getCommentLists().size() + ")";
                                buttonAllComment.setText(buttonTotal);
                            } else {
                                Constant_Api.scdLists.get(0).getCommentLists().add(0, new CommentList(bookId, method.pref.getString(method.userName, null), comment, getResources().getString(R.string.Today)));
                                commentAdapter.notifyDataSetChanged();
                                String buttonTotal = getResources().getString(R.string.button_view_all_scd) + " " + "(" + Constant_Api.scdLists.get(0).getCommentLists().size() + ")";
                                buttonAllComment.setText(buttonTotal);
                            }
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
