package com.example.admin.androidebook.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.admin.androidebook.Activity.MainActivity;
import com.example.admin.androidebook.Activity.SCDetail;
import com.example.admin.androidebook.Activity.Search;
import com.example.admin.androidebook.Adapter.LatestAdapterGV;
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
 * Created by admin on 12-03-2018.
 */

public class HomeFragment extends Fragment {

    private SliderLayout mDemoSlider;
    private Method method;
    private int columnWidth;
    public MenuItem searchItem;
    private AVLoadingIndicatorView progressBar;
    private Button button_latest, button_popular;
    public static List<SubCategoryList> sliderList;
    public static List<SubCategoryList> latestList;
    public static List<SubCategoryList> mostPopularList;
    private RecyclerView recyclerViewLatest, recyclerViewPopular;
    private LatestAdapterGV latestAdapterGVLatest;
    private LatestAdapterGV latestAdapterGVPopular;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, container, false);

        MainActivity.toolbar.setTitle(getResources().getString(R.string.home));

        method = new Method(getActivity());
        columnWidth = method.getScreenWidth();
        sliderList = new ArrayList<>();
        latestList = new ArrayList<>();
        mostPopularList = new ArrayList<>();

        mDemoSlider = (SliderLayout) view.findViewById(R.id.custom_indicator_home_fragment);
        mDemoSlider.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth, columnWidth / 2 + 60));

        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progreesbar_home_fragment);
        recyclerViewLatest = (RecyclerView) view.findViewById(R.id.recyclerViewLatest_home_fragment);
        recyclerViewPopular = (RecyclerView) view.findViewById(R.id.recyclerViewPopular_home_fragment);

        recyclerViewLatest.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerLatest = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLatest.setLayoutManager(layoutManagerLatest);
        recyclerViewLatest.setFocusable(false);

        recyclerViewPopular.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerPopular = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
        recyclerViewPopular.setFocusable(false);

        button_latest = (Button) view.findViewById(R.id.button_latest_home_fragment);
        button_popular = (Button) view.findViewById(R.id.button_popular_home_fragment);

        button_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new LatestFragment(), "latest").commit();
                Method.onBackPress = true;
            }
        });

        button_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new MostPopularFragment(), "most").commit();
                Method.onBackPress = true;
            }
        });

        if (Method.isNetworkAvailable(getActivity())) {
            Home();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Home() {

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

                    JSONArray jsonArray = jsonObjectBook.getJSONArray("featured_books");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_id = object.getString("cat_id");
                        String book_title = object.getString("book_title");
                        String book_cover_img = object.getString("book_cover_img");
                        String book_bg_img = object.getString("book_bg_img");
                        String book_file_type = object.getString("book_file_type");
                        String total_rate = object.getString("total_rate");
                        String rate_avg = object.getString("rate_avg");
                        String book_views = object.getString("book_views");
                        String author_id = object.getString("author_id");
                        String author_name = object.getString("author_name");

                        sliderList.add(new SubCategoryList(id, cat_id, book_title, Constant_Api.image + book_cover_img, Constant_Api.image + book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name));

                    }

                    JSONArray jsonArrayLatest = jsonObjectBook.getJSONArray("latest_books");
                    for (int i = 0; i < jsonArrayLatest.length(); i++) {

                        JSONObject object = jsonArrayLatest.getJSONObject(i);
                        String id = object.getString("id");
                        String cat_id = object.getString("cat_id");
                        String book_title = object.getString("book_title");
                        String book_cover_img = object.getString("book_cover_img");
                        String book_bg_img = object.getString("book_bg_img");
                        String book_file_type = object.getString("book_file_type");
                        String total_rate = object.getString("total_rate");
                        String rate_avg = object.getString("rate_avg");
                        String book_views = object.getString("book_views");
                        String author_id = object.getString("author_id");
                        String author_name = object.getString("author_name");

                        latestList.add(new SubCategoryList(id, cat_id, book_title, Constant_Api.image + book_cover_img, Constant_Api.image + book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name));

                    }

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

                    latestAdapterGVLatest = new LatestAdapterGV(getActivity(), latestList, "home_latest");
                    recyclerViewLatest.setAdapter(latestAdapterGVLatest);

                    latestAdapterGVPopular = new LatestAdapterGV(getActivity(), mostPopularList, "home_most");
                    recyclerViewPopular.setAdapter(latestAdapterGVPopular);

                    for (int i = 0; i < sliderList.size(); i++) {
                        TextSliderView textSliderView = new TextSliderView(getActivity());
                        // initialize a SliderLayout
                        textSliderView
                                .name(sliderList.get(i).getBook_title())
                                .sub_name(sliderList.get(i).getAuthor_name())
                                .rating(sliderList.get(i).getRate_avg())
                                .ratingView(sliderList.get(i).getTotal_rate())
                                .logo(sliderList.get(i).getBook_cover_img())
                                .image(sliderList.get(i).getBook_bg_img())
                                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {
                                        getActivity().startActivity(new Intent(getActivity(), SCDetail.class)
                                                .putExtra("bookId", sliderList.get(mDemoSlider.getCurrentPosition()).getId())
                                                .putExtra("position", mDemoSlider.getCurrentPosition())
                                                .putExtra("type", "slider"));
                                    }
                                })
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        mDemoSlider.addSlider(textSliderView);
                    }

                    //mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    //mDemoSlider.setDuration(4000);

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
        if (latestAdapterGVLatest != null) {
            latestAdapterGVLatest.notifyDataSetChanged();
        }
        if (latestAdapterGVPopular != null) {
            latestAdapterGVPopular.notifyDataSetChanged();
        }
        if (Method.slider) {
            if (mDemoSlider != null) {
                Method.slider=false;
                mDemoSlider.removeAllSliders();
                for (int i = 0; i < sliderList.size(); i++) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    // initialize a SliderLayout
                    textSliderView
                            .name(sliderList.get(i).getBook_title())
                            .sub_name(sliderList.get(i).getAuthor_name())
                            .rating(sliderList.get(i).getRate_avg())
                            .ratingView(sliderList.get(i).getTotal_rate())
                            .logo(sliderList.get(i).getBook_cover_img())
                            .image(sliderList.get(i).getBook_bg_img())
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    getActivity().startActivity(new Intent(getActivity(), SCDetail.class)
                                            .putExtra("bookId", sliderList.get(mDemoSlider.getCurrentPosition()).getId())
                                            .putExtra("position", mDemoSlider.getCurrentPosition())
                                            .putExtra("type", "slider"));
                                }
                            })
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    mDemoSlider.addSlider(textSliderView);
                }

                //mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                //mDemoSlider.setDuration(4000);
            }
        }
        super.onResume();
    }
}
