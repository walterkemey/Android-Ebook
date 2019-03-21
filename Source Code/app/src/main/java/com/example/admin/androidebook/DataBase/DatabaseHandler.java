package com.example.admin.androidebook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.androidebook.Item.DownloadList;
import com.example.admin.androidebook.Item.ScdList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 09-09-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "My Book";

    // book table name
    private static final String TABLE_NAME = "book";

    // book table name
    private static final String TABLE_NAME_DOWNLOAD = "book_download";

    // book Table Columns names
    private static final String KEY_BOOK_ID = "id";
    private static final String KEY_BOOK_TITLE = "book_title";
    private static final String KEY_BOOK_DESCRIPTION = "book_description";
    private static final String KEY_BOOK_IMAGE = "image";
    private static final String KEY_BOOK_BACKGROUND_IMAGE = "cover_image";
    private static final String KEY_BOOK_FILE_TYPE = "book_file_type";
    private static final String KEY_BOOK_FILE_URL = "book_file_url";
    private static final String KEY_BOOK_RATE = "book_rate";
    private static final String KEY_BOOK_RATE_AVG = "book_rate_avg";
    private static final String KEY_BOOK_VIEW = "book_view";
    private static final String KEY_BOOK_AUTHOR_NAME = "book_author_name";

    // book download Table Columns names
    private static final String KEY_BOOK_DOWNLOAD_ID = "book_id";
    private static final String KEY_BOOK_DOWNLOAD_TITLE = "book_download_title";
    private static final String KEY_BOOK_DOWNLOAD_IMAGE = "image_download";
    private static final String KEY_BOOK_DOWNLOAD_AUTHOR_NAME = "book_download_author_name";
    private static final String KEY_BOOK_DOWNLOAD_URL = "book_download_url";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_BOOK_ID + " INTEGER PRIMARY KEY,"
                + KEY_BOOK_TITLE + " TEXT," + KEY_BOOK_DESCRIPTION + ""
                + " TEXT," + KEY_BOOK_IMAGE + " TEXT," + KEY_BOOK_BACKGROUND_IMAGE + " TEXT," + KEY_BOOK_FILE_TYPE + " TEXT,"
                + KEY_BOOK_FILE_URL + " TEXT," + KEY_BOOK_RATE + " TEXT,"
                + KEY_BOOK_RATE_AVG + " TEXT," + KEY_BOOK_VIEW + " TEXT," + KEY_BOOK_AUTHOR_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_BOOK_TABLE);

        String CREATE_TABLE_DOWNLOAD = "CREATE TABLE " + TABLE_NAME_DOWNLOAD + "("
                + KEY_BOOK_DOWNLOAD_ID + " INTEGER PRIMARY KEY,"
                + KEY_BOOK_DOWNLOAD_TITLE + " TEXT,"
                + KEY_BOOK_DOWNLOAD_IMAGE + " TEXT," + KEY_BOOK_DOWNLOAD_AUTHOR_NAME + "" + " TEXT,"
                + KEY_BOOK_DOWNLOAD_URL + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_DOWNLOAD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DOWNLOAD);
        onCreate(db);
    }


    // Adding new cat detail
    public void addDetail(ScdList scdList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_ID, scdList.getId());
        values.put(KEY_BOOK_TITLE, scdList.getBook_title());
        values.put(KEY_BOOK_DESCRIPTION, scdList.getBook_description());
        values.put(KEY_BOOK_IMAGE, scdList.getBook_cover_img());
        values.put(KEY_BOOK_BACKGROUND_IMAGE, scdList.getBook_bg_img());
        values.put(KEY_BOOK_FILE_TYPE, scdList.getBook_file_type());
        values.put(KEY_BOOK_FILE_URL, scdList.getBook_file_url());
        values.put(KEY_BOOK_RATE, scdList.getTotal_rate());
        values.put(KEY_BOOK_RATE_AVG, scdList.getRate_avg());
        values.put(KEY_BOOK_VIEW, scdList.getBook_views());
        values.put(KEY_BOOK_AUTHOR_NAME, scdList.getAuthor_name());


        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }


    // Getting All category
    public List<ScdList> getBookDetail() {
        List<ScdList> scdLists = new ArrayList<ScdList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ScdList list = new ScdList();
                list.setId(cursor.getString(0));
                list.setBook_title(cursor.getString(1));
                list.setBook_description(cursor.getString(2));
                list.setBook_cover_img(cursor.getString(3));
                list.setBook_bg_img(cursor.getString(4));
                list.setBook_file_type(cursor.getString(5));
                list.setBook_file_url(cursor.getString(6));
                list.setTotal_rate(cursor.getString(7));
                list.setRate_avg(cursor.getString(8));
                list.setBook_views(cursor.getString(9));
                list.setAuthor_name(cursor.getString(10));


                // Adding video to list
                scdLists.add(list);
            } while (cursor.moveToNext());
        }

        // return category list
        return scdLists;
    }

    // Deleting single category
    public boolean deleteDetail(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_BOOK_ID + "=" + id, null) > 0;

    }

    //check category id in database or not
    public boolean checkId(String id) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_BOOK_ID + "=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //-------------Download Table-------------------//

    // Adding new cat detail
    public void addDownload(DownloadList downloadList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_DOWNLOAD_ID, downloadList.getId());
        values.put(KEY_BOOK_DOWNLOAD_TITLE, downloadList.getTitle());
        values.put(KEY_BOOK_DOWNLOAD_IMAGE, downloadList.getImage());
        values.put(KEY_BOOK_DOWNLOAD_AUTHOR_NAME, downloadList.getAuthor());
        values.put(KEY_BOOK_DOWNLOAD_URL, downloadList.getUrl());

        // Inserting Row
        db.insert(TABLE_NAME_DOWNLOAD, null, values);
        db.close(); // Closing database connection
    }


    // Getting All category
    public List<DownloadList> getDownload() {
        List<DownloadList> downloadLists = new ArrayList<DownloadList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_DOWNLOAD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DownloadList list = new DownloadList();
                list.setId(cursor.getString(0));
                list.setTitle(cursor.getString(1));
                list.setImage(cursor.getString(2));
                list.setAuthor(cursor.getString(3));
                list.setUrl(cursor.getString(4));

                // Adding video to list
                downloadLists.add(list);
            } while (cursor.moveToNext());
        }

        // return category list
        return downloadLists;
    }

    // Deleting single category
    public boolean deletePdf(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_DOWNLOAD, KEY_BOOK_DOWNLOAD_ID + "=" + id, null) > 0;

    }

    //check category id in database or not
    public boolean checkIdPdf(String id) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_DOWNLOAD + " WHERE " + KEY_BOOK_DOWNLOAD_ID + "=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //-------------Download Table-------------------//

}
