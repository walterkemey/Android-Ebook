package com.example.admin.androidebook.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.androidebook.Activity.MainActivity;
import com.example.admin.androidebook.Activity.Search;
import com.example.admin.androidebook.Adapter.CategoryAdapter;
import com.example.admin.androidebook.Item.CategoryList;
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

/**
 * Created by admin on 13-03-2018.
 */

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private AVLoadingIndicatorView progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CategoryList> categoryLists;
    public MenuItem searchItem;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_fragment, container, false);

        MainActivity.toolbar.setTitle(getResources().getString(R.string.category));

        categoryLists = new ArrayList<>();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_category_fragment);
        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progressbar_category_fragment);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category_fragment);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Method.isNetworkAvailable(getActivity())) {
                    Category();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        if (Method.isNetworkAvailable(getActivity())) {
            Category();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        searchItem = menu.findItem(R.id.ic_searchView);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(getActivity(),Search.class)
                        .putExtra("search",query));
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

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Category() {

        categoryLists.clear();
        progressBar.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant_Api.category, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("EBOOK_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String cid = object.getString("cid");
                        String category_name = object.getString("category_name");
                        String total_books = object.getString("total_books");


                        categoryLists.add(new CategoryList(cid, category_name, total_books));

                    }

                    categoryAdapter = new CategoryAdapter(getActivity(), categoryLists);
                    recyclerView.setAdapter(categoryAdapter);
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
