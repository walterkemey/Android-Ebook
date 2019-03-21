package com.example.admin.androidebook.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidebook.DataBase.DatabaseHandler;
import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Constant_Api;
import com.example.admin.androidebook.Util.DownloadEpub;
import com.example.admin.androidebook.Util.Method;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FavouriteDetail extends AppCompatActivity {

    public Toolbar toolbar;
    private Menu menu;
    private String bookId;
    private Method method;
    private boolean isMenu = false;
    private DatabaseHandler db;
    private int position = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_detail);

        Method.forceRTLIfSupported(getWindow(), FavouriteDetail.this);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        position = intent.getIntExtra("position", 0);

        db = new DatabaseHandler(FavouriteDetail.this);

        method = new Method(FavouriteDetail.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_favourite_detail);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constant_Api.scdLists.get(position).getBook_title());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView imageView = (ImageView) findViewById(R.id.imageView_favourite_detail);
        ImageView imageView_CoverBook = (ImageView) findViewById(R.id.imageView_book_favourite_detail);
        TextView textView_Description = (TextView) findViewById(R.id.textView_description_favourite_detail);
        TextView textViewBookName = (TextView) findViewById(R.id.textView_bookName_favourite_detail);
        TextView textViewAuthor = (TextView) findViewById(R.id.textView_authorName_favourite_detail);

        Picasso.with(FavouriteDetail.this).load(Constant_Api.scdLists.get(position).getBook_cover_img())
                .placeholder(R.drawable.placeholder_portable)
                .into(imageView_CoverBook);
        Picasso.with(FavouriteDetail.this).load(Constant_Api.scdLists.get(position).getBook_bg_img())
                .placeholder(R.drawable.placeholder_portable)
                .into(imageView);
        textView_Description.setText(Html.fromHtml(Constant_Api.scdLists.get(position).getBook_description()));
        textViewBookName.setText(Constant_Api.scdLists.get(position).getBook_title());
        textViewAuthor.setText(Constant_Api.scdLists.get(position).getAuthor_name());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Method.isNetworkAvailable(FavouriteDetail.this)) {
                    if (Constant_Api.scdLists.get(position).getBook_file_url().contains(".epub")) {
                        DownloadEpub downloadEpub = new DownloadEpub(FavouriteDetail.this);
                        downloadEpub.pathEpub(Constant_Api.scdLists.get(position).getBook_file_url(), Constant_Api.scdLists.get(position).getId());
                    } else {
                        startActivity(new Intent(FavouriteDetail.this, PDFShow.class)
                                .putExtra("link", Constant_Api.scdLists.get(position).getBook_file_url())
                                .putExtra("toolbarTitle", Constant_Api.scdLists.get(position).getBook_title())
                                .putExtra("type", "link"));
                    }
                } else {
                    Toast.makeText(FavouriteDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }


            }
        });

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
                if (db.checkId(Constant_Api.scdLists.get(position).getId())) {
                    method.addData(db, position);
                    item.setIcon(R.drawable.ic_fav_hov);
                } else {
                    db.deleteDetail(Constant_Api.scdLists.get(position).getId());
                    item.setIcon(R.drawable.ic_fav);
                    Toast.makeText(this, getResources().getString(R.string.remove_to_favourite), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.share:
                if (Method.allowPermitionExternalStorage) {
                    Method.share = true;
                    Method.share_save(Constant_Api.scdLists.get(position).getBook_cover_img(),
                            Constant_Api.scdLists.get(position).getBook_title(),
                            Constant_Api.scdLists.get(position).getAuthor_name(),
                            Constant_Api.scdLists.get(position).getBook_description(),
                            Constant_Api.scdLists.get(position).getBook_file_url());

                } else {
                    Toast.makeText(this, getResources().getString(R.string.cannot_use_save_permission), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.read_book:
                if (Method.isNetworkAvailable(FavouriteDetail.this)) {
                    if (Constant_Api.scdLists.get(position).getBook_file_url().contains(".epub")) {
                        DownloadEpub downloadEpub = new DownloadEpub(FavouriteDetail.this);
                        downloadEpub.pathEpub(Constant_Api.scdLists.get(position).getBook_file_url(),Constant_Api.scdLists.get(position).getId());
                    } else {
                        startActivity(new Intent(FavouriteDetail.this, PDFShow.class)
                                .putExtra("link", Constant_Api.scdLists.get(position).getBook_file_url())
                                .putExtra("toolbarTitle", Constant_Api.scdLists.get(position).getBook_title())
                                .putExtra("type", "link"));
                    }
                } else {
                    Toast.makeText(FavouriteDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.download_book:
                if (Method.isNetworkAvailable(FavouriteDetail.this)) {
                    if (db.checkIdPdf(Constant_Api.scdLists.get(position).getId())) {
                        if (Constant_Api.scdLists.get(position).getBook_file_url().contains(".epub")) {
                            method.download(Constant_Api.scdLists.get(position).getId(),
                                    Constant_Api.scdLists.get(position).getBook_title(),
                                    Constant_Api.scdLists.get(position).getBook_cover_img(),
                                    Constant_Api.scdLists.get(position).getAuthor_name(),
                                    Constant_Api.scdLists.get(position).getBook_file_url(), "epub");
                        } else {
                            method.download(Constant_Api.scdLists.get(position).getId(),
                                    Constant_Api.scdLists.get(position).getBook_title(),
                                    Constant_Api.scdLists.get(position).getBook_cover_img(),
                                    Constant_Api.scdLists.get(position).getAuthor_name(),
                                    Constant_Api.scdLists.get(position).getBook_file_url(), "pdf");
                        }
                    } else {
                        Toast.makeText(FavouriteDetail.this, getResources().getString(R.string.you_have_allready_download_book), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FavouriteDetail.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
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
            menu.findItem(R.id.menu_favorite_scd).setIcon(ContextCompat.getDrawable(FavouriteDetail.this, R.drawable.ic_fav));
        } else {
            menu.findItem(R.id.menu_favorite_scd).setIcon(ContextCompat.getDrawable(FavouriteDetail.this, R.drawable.ic_fav_hov));
        }
    }

}
