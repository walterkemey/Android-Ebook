package com.example.admin.androidebook.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.androidebook.R;
import com.example.admin.androidebook.Util.Method;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFShow extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private PDFView pdfView;
    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;
    private String uri, type;
    private InputStream is;
    public Toolbar toolbar;
    private ProgressDialog progressDialog;

    private static final String TAG = PDFShow.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        Method.forceRTLIfSupported(getWindow(), PDFShow.this);

        Intent intent = getIntent();
        uri = intent.getStringExtra("link");
        String toolbarTitle = intent.getStringExtra("toolbarTitle");
        type = intent.getStringExtra("type");

        progressDialog = new ProgressDialog(PDFShow.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_pdfView);
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pdfView = (PDFView) findViewById(R.id.pdfView_activity);
        pdfView.setBackgroundColor(Color.LTGRAY);

        if (type.equals("link")) {
            new PdfAsyncTask().execute(uri);
        } else {
            File file = new File(uri);
            displayFromFile(file);
        }

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(type.equals("link")){
                displayFromUri(is);
            }else {
                File file=new File(uri);
                displayFromFile(file);
            }
        }
    }*/

    public void displayFromUri(InputStream inputStream) {
        pdfView.fromStream(inputStream)
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    public void displayFromFile(File file) {
        pdfView.fromFile(file)
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {
        progressDialog.dismiss();
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());
    }

    @Override
    public void onPageError(int page, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }

    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, "error_launchPicker", Toast.LENGTH_SHORT).show();
        }
    }

    class PdfAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL myURL = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                Log.d("uri", uri.toString());

            } catch (Exception e) {
                Log.d("toast", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            displayFromUri(is);
            super.onPostExecute(s);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
