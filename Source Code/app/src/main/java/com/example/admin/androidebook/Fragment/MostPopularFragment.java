package com.example.admin.androidebook.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.Activity.MainActivity;
import com.example.admin.androidebook.Activity.Search;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 13-03-2018.
 */

public class MostPopularFragment extends Fragment {

    private ImageView imageView_gridView, imageView_listView;
    private RecyclerView recyclerView;
    private AVLoadingIndicatorView progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public MenuItem searchItem;
    public static List<SubCategoryList> mostPopularList;
    private LatestAdapterLV latestAdapterLV;
    private LatestAdapterGV latestAdapterGV;
    private boolean isView = true;
    private TextView textViewCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.latest_fragment, container, false);

        MainActivity.toolbar.setTitle(getResources().getString(R.string.popular_books));

        mostPopularList = new ArrayList<>();

        imageView_gridView = (ImageView) view.findViewById(R.id.imageView_gridView_latest_fragment);
        imageView_listView = (ImageView) view.findViewById(R.id.imageView_listView_latest_fragment);


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_latest_fragment);
        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progresbar_latest_fragment);
        textViewCount=(TextView)view.findViewById(R.id.textView_number_ofItem_latest_fragment);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_latest_fragment);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Method.isNetworkAvailable(getActivity())) {
                    MostPopular(isView);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
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
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (Method.isNetworkAvailable(getActivity())) {
                    MostPopular(isView);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = true;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (Method.isNetworkAvailable(getActivity())) {
                    MostPopular(isView);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Method.isNetworkAvailable(getActivity())) {
            MostPopular(isView);
            isView = true;
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
                startActivity(new Intent(getActivity(), Search.class)
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

        super.onCreateOptionsMenu(menu, inflater);

    }

    public void MostPopular(final boolean isTrue) {

        mostPopularList.clear();

        progressBar.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant_Api.home, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");

                    JSONArray jsonArrayPopular = jsonObjectBook.getJSONArray("popular_books");
                    for (int i = 0; i < jsonArrayPopular.length(); i++) {

                        JSONObject object = jsonArrayPopular.getJSONObject(i);
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

                        mostPopularList.add(new SubCategoryList(id, cat_id, book_title, book_description, Constant_Api.image + book_cover_img, Constant_Api.image + book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name));

                    }
                    if (isTrue) {
                        latestAdapterLV = new LatestAdapterLV(getActivity(), mostPopularList,"most_popular");
                        recyclerView.setAdapter(latestAdapterLV);
                        progressBar.hide();
                    } else {
                        latestAdapterGV = new LatestAdapterGV(getActivity(), mostPopularList,"most_popular");
                        recyclerView.setAdapter(latestAdapterGV);
                        progressBar.hide();
                    }

                    String count=mostPopularList.size() + " " + getResources().getString(R.string.iteam);
                    textViewCount.setText(count);

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

    @Override
    public void onResume() {
        if(isView){
            if(latestAdapterLV!=null){
                latestAdapterLV.notifyDataSetChanged();
            }
        }else {
            if(latestAdapterGV!=null){
                latestAdapterGV.notifyDataSetChanged();
            }
        }
        super.onResume();
    }

}
