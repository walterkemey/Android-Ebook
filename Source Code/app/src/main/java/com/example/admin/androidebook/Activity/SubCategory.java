package com.example.admin.androidebook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.Adapter.LatestAdapterGV;
import com.example.admin.androidebook.Adapter.LatestAdapterLV;
import com.example.admin.androidebook.Item.SubCategoryList;
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

public class SubCategory extends AppCompatActivity {

    private ImageView imageView_gridView, imageView_listView;
    public Toolbar toolbar;
    private String toolbarTitle, id, type;
    private RecyclerView recyclerView;
    private AVLoadingIndicatorView progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LatestAdapterLV latestAdapterLV;
    private LatestAdapterGV latestAdapterGV;
    private boolean isView = true;
    public MenuItem searchItem;
    private TextView textViewCount;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Method.forceRTLIfSupported(getWindow(), SubCategory.this);

        Intent intent = getIntent();
        toolbarTitle = intent.getStringExtra("toolbarTitle");
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");

        toolbar = (Toolbar) findViewById(R.id.toolbar_sub_category);
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView_gridView = (ImageView) findViewById(R.id.imageView_gridView_sub_category);
        imageView_listView = (ImageView) findViewById(R.id.imageView_listView_sub_category);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_sub_category);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progresbar_sub_category);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_sub_category);
        textViewCount = (TextView) findViewById(R.id.textView_number_ofItem_sub_category);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SubCategory.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Method.isNetworkAvailable(SubCategory.this)) {
                    subCategory(isView);
                } else {
                    Toast.makeText(SubCategory.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        imageView_gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = false;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid_hov));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list));
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SubCategory.this, 3);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (Method.isNetworkAvailable(SubCategory.this)) {
                    subCategory(isView);
                } else {
                    Toast.makeText(SubCategory.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = true;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SubCategory.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (Method.isNetworkAvailable(SubCategory.this)) {
                    subCategory(isView);
                } else {
                    Toast.makeText(SubCategory.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Method.isNetworkAvailable(SubCategory.this)) {
            subCategory(isView);
            isView = true;
        } else {
            Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        searchItem = menu.findItem(R.id.ic_searchView);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(SubCategory.this, Search.class)
                        .putExtra("search", query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        }));

        MenuItemCompat.setOnActionExpandListener(searchItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
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

    public void subCategory(final boolean isTrue) {

        Constant_Api.subCategoryLists.clear();

        progressBar.show();

        String url;

        if (type.equals("category")) {
            url = Constant_Api.sub_category + id;
        } else {
            url = Constant_Api.author_list + id;
        }
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
                        String book_file_type = object.getString("book_file_type");
                        String total_rate = object.getString("total_rate");
                        String rate_avg = object.getString("rate_avg");
                        String book_views = object.getString("book_views");
                        String author_name = object.getString("author_name");

                        Constant_Api.subCategoryLists.add(new SubCategoryList(id, book_title, book_description, Constant_Api.image + book_cover_img, book_file_type, total_rate, rate_avg, book_views, author_name));

                    }
                    String count = Constant_Api.subCategoryLists.size() + " " + getResources().getString(R.string.iteam);
                    textViewCount.setText(count);
                    if (isTrue) {
                        latestAdapterLV = new LatestAdapterLV(SubCategory.this, Constant_Api.subCategoryLists, "latest");
                        recyclerView.setAdapter(latestAdapterLV);
                        progressBar.hide();
                    } else {
                        latestAdapterGV = new LatestAdapterGV(SubCategory.this, Constant_Api.subCategoryLists, "latest");
                        recyclerView.setAdapter(latestAdapterGV);
                        progressBar.hide();
                    }

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

    @Override
    protected void onResume() {
        if (isView) {
            if (latestAdapterLV != null) {
                latestAdapterLV.notifyDataSetChanged();
            }
        } else {
            if (latestAdapterGV != null) {
                latestAdapterGV.notifyDataSetChanged();
            }
        }
        super.onResume();
    }

}
