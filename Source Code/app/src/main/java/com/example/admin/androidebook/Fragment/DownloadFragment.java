package com.example.admin.androidebook.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.admin.androidebook.Adapter.DownloadAdapter;
import com.example.admin.androidebook.DataBase.DatabaseHandler;
import com.example.admin.androidebook.Item.DownloadList;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by admin on 13-03-2018.
 */

public class DownloadFragment extends Fragment {

    private RecyclerView recyclerView;
    private AVLoadingIndicatorView progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DownloadAdapter downloadAdapter;
    public MenuItem searchItem;
    private List<File> inFiles;
    private List<DownloadList> downloadListsCompair;
    private String root;
    private DatabaseHandler db;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_fragment, container, false);

        MainActivity.toolbar.setTitle(getResources().getString(R.string.download));

        db = new DatabaseHandler(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        inFiles = new ArrayList<>();
        downloadListsCompair = new ArrayList<>();
        root = Environment.getExternalStorageDirectory() + "/AndroidEBook/";

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh_category_fragment);
        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progressbar_category_fragment);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category_fragment);

        progressBar.hide();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Execute().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        new Execute().execute();

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

    class Execute extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(false);
            Constant_Api.downloadLists.clear();
            inFiles.clear();
            downloadListsCompair.clear();
            Constant_Api.db = new DatabaseHandler(getContext());
            Constant_Api.downloadLists = Constant_Api.db.getDownload();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            File file = new File(root);
            getListFiles(file);
            getDownloadLists(inFiles);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            downloadAdapter = new DownloadAdapter(getActivity(), downloadListsCompair);
            recyclerView.setAdapter(downloadAdapter);
            progressDialog.dismiss();
            super.onPostExecute(s);
        }
    }

    private List<File> getListFiles(File parentDir) {
        // List<File> inFiles = new ArrayList<>();
        Queue<File> files = new LinkedList<>();
        files.addAll(Arrays.asList(parentDir.listFiles()));
        while (!files.isEmpty()) {
            File file = files.remove();
            if (file.isDirectory()) {
                files.addAll(Arrays.asList(file.listFiles()));
            } else if (file.getName().endsWith(".epub") || file.getName().endsWith(".pdf")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    private List<DownloadList> getDownloadLists(List<File> list) {
        for (int i = 0; i < Constant_Api.downloadLists.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).toString().contains(Constant_Api.downloadLists.get(i).getUrl())) {
                    downloadListsCompair.add(Constant_Api.downloadLists.get(i));
                    break;
                } else {
                    if (j == list.size() - 1) {
                        db.deletePdf(Constant_Api.downloadLists.get(i).getId());
                    }
                }
            }
        }
        return downloadListsCompair;
    }

}
