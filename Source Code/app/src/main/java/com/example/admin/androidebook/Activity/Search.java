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

import com.example.admin.androidebook.Adapter.SearchAdapterGV;
import com.example.admin.androidebook.Adapter.SearchAdapterLV;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Search extends AppCompatActivity {

    public Toolbar toolbar;
    private RecyclerView recyclerView;
    private AVLoadingIndicatorView progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static List<SubCategoryList> subCategoryListsSearch;
    private boolean isView = true;
    public MenuItem searchItem;
    private String stringSearch;
    private SearchAdapterGV searchAdapterGV;
    private SearchAdapterLV searchAdapterLV;
    private ImageView imageView_gridView, imageView_listView;
    private TextView textViewCount;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Method.forceRTLIfSupported(getWindow(), Search.this);

        Intent intent = getIntent();
        stringSearch = intent.getStringExtra("search");

        subCategoryListsSearch = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        toolbar.setTitle(stringSearch);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView_gridView = (ImageView) findViewById(R.id.imageView_gridView_search);
        imageView_listView = (ImageView) findViewById(R.id.imageView_listView_search);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_search);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progresbar_search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_search);
        textViewCount = (TextView) findViewById(R.id.textView_number_ofItem_search);

        imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Search.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Method.isNetworkAvailable(Search.this)) {
                    search(isView);
                } else {
                    Toast.makeText(Search.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
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
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Search.this, 3);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (Method.isNetworkAvailable(Search.this)) {
                    search(isView);
                } else {
                    Toast.makeText(Search.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = true;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Search.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (Method.isNetworkAvailable(Search.this)) {
                    search(isView);
                } else {
                    Toast.makeText(Search.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Method.isNetworkAvailable(Search.this)) {
            search(isView);
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
                startActivity(new Intent(Search.this, Search.class)
                        .putExtra("search", query));
                finish();
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

    public void search(final boolean isTrue) {

        subCategoryListsSearch.clear();
        progressBar.show();

        final String searchResult = Constant_Api.searchApi + stringSearch;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchResult, null, new AsyncHttpResponseHandler() {
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
                        String cat_id = object.getString("cat_id");
                        String book_title = object.getString("book_title");
                        String book_description = object.getString("book_description");
                        String book_cover_img = object.getString("book_cover_img");
                        String book_bg_img = object.getString("book_bg_img");
                        String book_file_type = object.getString("book_file_type");
                        String total_rate = object.getString("total_rate");
                        String rate_avg = object.getString("rate_avg");
                        String book_views = object.getString("book_views");
                        String author_id = object.getString("author_id");
                        String author_name = object.getString("author_name");
                        String category_name = object.getString("category_name");


                        subCategoryListsSearch.add(new SubCategoryList(id, cat_id, book_title, book_description, Constant_Api.image + book_cover_img, Constant_Api.image + book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, category_name));

                    }
                    String count = subCategoryListsSearch.size() + " " + getResources().getString(R.string.iteam);
                    textViewCount.setText(count);
                    if (isTrue) {
                        searchAdapterLV = new SearchAdapterLV(Search.this, subCategoryListsSearch);
                        recyclerView.setAdapter(searchAdapterLV);
                        progressBar.hide();
                    } else {
                        searchAdapterGV = new SearchAdapterGV(Search.this, subCategoryListsSearch);
                        recyclerView.setAdapter(searchAdapterGV);
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
            if (searchAdapterLV != null) {
                searchAdapterLV.notifyDataSetChanged();
            }
        } else {
            if (searchAdapterGV != null) {
                searchAdapterGV.notifyDataSetChanged();
            }
        }
        super.onResume();
    }

}
