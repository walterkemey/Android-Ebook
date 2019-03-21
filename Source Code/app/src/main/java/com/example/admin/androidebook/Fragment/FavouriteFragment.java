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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.androidebook.Activity.MainActivity;
import com.example.admin.androidebook.Activity.Search;
import com.example.admin.androidebook.Adapter.FavouriteAdapter;
import com.example.admin.androidebook.DataBase.DatabaseHandler;
import com.example.admin.androidebook.Item.ScdList;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 13-03-2018.
 */

public class FavouriteFragment extends Fragment {

    private AVLoadingIndicatorView progressBar;
    private FavouriteAdapter favouriteAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public MenuItem searchItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_fragment, container, false);

        MainActivity.toolbar.setTitle(getResources().getString(R.string.favorites));

        Constant_Api.db = new DatabaseHandler(getContext());
        Constant_Api.scdLists = Constant_Api.db.getBookDetail();

        MainActivity.toolbar.setTitle(getResources().getString(R.string.favorites));

        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progressbar_category_fragment);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category_fragment);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_category_fragment);

        progressBar.hide();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        progressBar.setVisibility(View.GONE);

        favouriteAdapter = new FavouriteAdapter(getActivity(), Constant_Api.scdLists);
        recyclerView.setAdapter(favouriteAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Constant_Api.scdLists.clear();
                Constant_Api.db = new DatabaseHandler(getContext());
                Constant_Api.scdLists = Constant_Api.db.getBookDetail();
                favouriteAdapter = new FavouriteAdapter(getActivity(), Constant_Api.scdLists);
                recyclerView.setAdapter(favouriteAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        if (favouriteAdapter != null) {
            Constant_Api.scdLists.clear();
            Constant_Api.scdLists = Constant_Api.db.getBookDetail();
            favouriteAdapter = new FavouriteAdapter(getActivity(), Constant_Api.scdLists);
            recyclerView.setAdapter(favouriteAdapter);
        }
        super.onResume();
    }

}
