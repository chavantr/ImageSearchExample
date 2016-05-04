package com.microsoft.imageseach;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.microsoft.imageseach.Binder.ImageLoaderAdapter;
import com.microsoft.imageseach.Process.GetImages;
import com.microsoft.imageseach.Process.OnProcessListener;

import java.net.URLEncoder;

/**
 * @author Tatyabhau Chavan
 * @created 03/05/2016
 */
public class ImageSearch extends AppCompatActivity implements OnProcessListener {


    /***************/

    private GetImages getImages;
    private String URL = "https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=900&pilimit=50&generator=prefixsearch&gpssearch=";


    // UI variables

    private EditText txtSearch;
    private ProgressBar progressBar;
    private RecyclerView lstPages;
    private ImageLoaderAdapter imageLoaderAdapter;

    /***************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        events();
    }

    private void init() {
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        lstPages = (RecyclerView) findViewById(R.id.lstPages);
        lstPages.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void events() {
        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide(txtSearch);
                if (!txtSearch.getText().toString().isEmpty()) {
                    initGetImages(txtSearch.getText().toString().trim());
                } else {
                    show("Please enter search title", v);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * @param what
     */
    private void initGetImages(String what) {
        progressBar.setVisibility(View.VISIBLE);
        getImages = new GetImages(ImageSearch.this);
        getImages.setOnProcessListener(this, URL + URLEncoder.encode(what));
    }

    @Override
    public void onProcessComplete(com.microsoft.imageseach.Model.ImageSearch result, Exception exception) {
        progressBar.setVisibility(View.GONE);
        if (null != result && exception == null) {
            imageLoaderAdapter = new ImageLoaderAdapter(result.getQuery().getPage());
            lstPages.setAdapter(imageLoaderAdapter);
        } else {
            show(exception.getMessage(), txtSearch);
        }
        hide(txtSearch);
    }

    /**
     * @param message
     * @param id
     */
    private void show(String message, View id) {
        Snackbar.make(id, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }


    /**
     * @param view
     */
    private void hide(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(view.getApplicationWindowToken(), 0);
    }
}
