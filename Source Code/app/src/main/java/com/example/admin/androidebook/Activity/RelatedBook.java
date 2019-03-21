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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.androidebook.Adapter.RelatedBookAdapterGV;
import com.example.admin.androidebook.Adapter.RelatedBookAdapterLV;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.Method;
import com.wang.avi.AVLoadingIndicatorView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RelatedBook extends AppCompatActivity {


    private ImageView imageView_gridView, imageView_listView;
    public Toolbar toolbar;
    private RecyclerView recyclerView;
    private AVLoadingIndicatorView progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelatedBookAdapterLV relatedBookAdapterLV;
    private RelatedBookAdapterGV relatedBookAdapterGV;
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

        Method.forceRTLIfSupported(getWindow(), RelatedBook.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_sub_category);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView_gridView = (ImageView) findViewById(R.id.imageView_gridView_sub_category);
        imageView_listView = (ImageView) findViewById(R.id.imageView_listView_sub_category);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_sub_category);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progresbar_sub_category);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_sub_category);
        textViewCount = (TextView) findViewById(R.id.textView_number_ofItem_sub_category);

        progressBar.hide();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RelatedBook.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapterSet(isView);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));

        imageView_gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = false;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid_hov));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list));
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(RelatedBook.this, 3);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                adapterSet(isView);
            }
        });

        imageView_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = true;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RelatedBook.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                adapterSet(isView);
            }
        });

        adapterSet(isView);
        isView = true;


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
                startActivity(new Intent(RelatedBook.this, Search.class)
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


    public void adapterSet(final boolean isTrue) {

        if (isTrue) {
            relatedBookAdapterLV = new RelatedBookAdapterLV(RelatedBook.this, Constant_Api.scdLists.get(0).getScdLists());
            recyclerView.setAdapter(relatedBookAdapterLV);
        } else {
            relatedBookAdapterGV = new RelatedBookAdapterGV(RelatedBook.this, Constant_Api.scdLists.get(0).getScdLists());
            recyclerView.setAdapter(relatedBookAdapterGV);
        }

        String count = Constant_Api.scdLists.get(0).getScdLists().size() + " " + getResources().getString(R.string.iteam);
        textViewCount.setText(count);

    }

    @Override
    protected void onResume() {
        if (isView) {
            if (relatedBookAdapterLV != null) {
                relatedBookAdapterLV.notifyDataSetChanged();
            }
        } else {
            if (relatedBookAdapterGV != null) {
                relatedBookAdapterGV.notifyDataSetChanged();
            }
        }
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
