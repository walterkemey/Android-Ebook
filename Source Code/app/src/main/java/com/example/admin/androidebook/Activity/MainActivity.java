package com.example.admin.androidebook.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.admin.androidebook.Adapter.MainAdapter;
import com.example.admin.androidebook.Fragment.AuthorFragment;
import com.example.admin.androidebook.Fragment.CategoryFragment;
import com.example.admin.androidebook.Fragment.DownloadFragment;
import com.example.admin.androidebook.Fragment.FavouriteFragment;
import com.example.admin.androidebook.Fragment.HomeFragment;
import com.example.admin.androidebook.Fragment.LatestFragment;
import com.example.admin.androidebook.Item.AboutUsList;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.Method;
import com.example.admin.androidebook.Util.RecyclerTouchListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private AdView mAdView;
    private Method method;
    private RecyclerView recyclerView;
    public static MainAdapter mainAdapter;

    private String[] name = {"Home", "Latest", "Category", "Author", "Download", "Favorite",
            "Share App", "Rate App", "More App", "About Us", "Privacy Policy", "Profile", "Logout"
    };

    private Integer[] image =
            {
                    R.drawable.ic_home, R.drawable.ic_latest, R.drawable.ic_category, R.drawable.ic_author,
                    R.drawable.ic_download_slider, R.drawable.ic_favorite, R.drawable.ic_share_slider, R.drawable.ic_rate,
                    R.drawable.ic_more, R.drawable.ic_about, R.drawable.ic_parivacy, R.drawable.ic_profile,
                    R.drawable.ic_logout
            };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Method.forceRTLIfSupported(getWindow(), MainActivity.this);

        method = new Method(MainActivity.this);

        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        mAdView = findViewById(R.id.adView_mian);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView_main);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        mainAdapter = new MainAdapter(MainActivity.this, name, image);
        recyclerView.setAdapter(mainAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, recyclerView, new MainAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                navigation(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_side_nav);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment;
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
        } else {
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, homeFragment, "home").commit();
            Method.onBackPress = true;
        }

        OneSignal.startInit(this)
                .init();

        Method.trackScreenView(MainActivity.this, getResources().getString(R.string.main_activity));

        checkPer();

        if (Method.isNetworkAvailable(MainActivity.this)) {
            aboutUs();
        } else {
            Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                return;
            }
            if (Method.onBackPress) {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, getResources().getString(R.string.Please_click_BACK_again_to_exit), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        //Closing drawer on item click
        drawer.closeDrawers();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            default:
                return true;
        }
    }

    public void checkPer() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE" + "android.permission.WRITE_INTERNAL_STORAGE" + "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.WRITE_INTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                Method.allowPermitionExternalStorage = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        boolean canUseExternalStorage = false;

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    canUseExternalStorage = true;
                    Method.allowPermitionExternalStorage = true;
                }
                if (!canUseExternalStorage) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.cannot_use_save_permission), Toast.LENGTH_SHORT).show();
                    Method.allowPermitionExternalStorage = false;
                }
            }
        }
    }

    private void navigation(int position) {

        switch (position) {

            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "home").commit();
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new LatestFragment(), "latest").commit();
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new CategoryFragment(), "category").commit();
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new AuthorFragment(), "author").commit();
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new DownloadFragment(), "download").commit();
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new FavouriteFragment(), "favourite").commit();
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 6:
                shareApp();
                drawer.closeDrawers();
                break;

            case 7:
                rateApp();
                drawer.closeDrawers();
                break;

            case 8:
                moreApp();
                drawer.closeDrawers();
                break;

            case 9:
                startActivity(new Intent(MainActivity.this, AboutUs.class));
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 10:
                startActivity(new Intent(MainActivity.this, PrivacyPolice.class));
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 11:
                if (Method.isNetworkAvailable(MainActivity.this)) {
                    if (method.pref.getBoolean(method.pref_login, false)) {
                        startActivity(new Intent(MainActivity.this, Profile.class));
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.you_have_not_login), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 12:
                if (method.pref.getBoolean(method.pref_login, false)) {
                    method.editor.putBoolean(method.pref_login, false);
                    method.editor.commit();
                    finishAffinity();
                    drawer.closeDrawers();
                } else {
                    startActivity(new Intent(MainActivity.this, Login.class));
                    drawer.closeDrawers();
                    finishAffinity();
                }
                break;
        }

    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
        }
    }

    private void moreApp() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_more_app))));
    }

    private void shareApp() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\n" + getResources().getString(R.string.Let_me_recommend_you_this_application) + "\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getApplication().getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }

    }

    public void aboutUs() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constant_Api.app_info, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray("EBOOK_APP");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String app_name = object.getString("app_name");
                        String app_logo = object.getString("app_logo");
                        String app_version = object.getString("app_version");
                        String app_author = object.getString("app_author");
                        String app_contact = object.getString("app_contact");
                        String app_email = object.getString("app_email");
                        String app_website = object.getString("app_website");
                        String app_description = object.getString("app_description");
                        String app_privacy_policy = object.getString("app_privacy_policy");

                        Constant_Api.aboutUsList = new AboutUsList(app_name, app_logo, app_version, app_author, app_contact, app_email, app_website, app_description, app_privacy_policy);

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

