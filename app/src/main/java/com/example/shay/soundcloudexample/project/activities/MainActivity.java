package com.example.shay.soundcloudexample.project.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shay.soundcloudexample.R;
import com.example.shay.soundcloudexample.project.adapters.CollectionsAdapter;
import com.example.shay.soundcloudexample.project.adapters.DividerItemDecoration;
import com.example.shay.soundcloudexample.project.adapters.RecyclerItemClickListener;
import com.example.shay.soundcloudexample.project.app.AppController;
import com.example.shay.soundcloudexample.project.dataObjects.Collection;
import com.example.shay.soundcloudexample.project.dataObjects.SearchResultResponse;
import com.example.shay.soundcloudexample.project.utils.DialogHelper;
import com.example.shay.soundcloudexample.project.utils.LoggingHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String SAVED_LAST_SEARCH_KEY = "saved_last_search_key";
    private static String TAG = MainActivity.class.getSimpleName();
    public static final String FILE_NAME = MainActivity.class.getSimpleName();
    private static String URL = "https://api.soundcloud.com/search?q=###&client_id=d652006c469530a4a7d6184b18e16c81&limit=20";

    private AppController app;
    private String tag_json_obj = "jobj_req";
    private ProgressDialog pDialog;

    private String searchUrl;
    private SearchResultResponse searchResultResponse;

    private EditText searchInputEt;
    private Button searchBtn;
    private Button listBtn;
    private Button gridBtn;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView tracksListRv;

    private List<Collection> collectionList = new ArrayList<>();
    private List<Collection> onlyTracksCollectionList;
    private CollectionsAdapter adapter;

    private boolean mIsGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initApp();
        initViews();
        initListeners();
        checkIfThereIsLastSearch();
    }

    private void initApp() {
        app = (AppController) getApplication();
    }


    private void initViews() {
        searchInputEt = (EditText) findViewById(R.id.searchInputEt);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        listBtn = (Button) findViewById(R.id.listBtn);
        gridBtn = (Button) findViewById(R.id.gridBtn);
        tracksListRv = (RecyclerView) findViewById(R.id.tracksListRv);

    }

    private void initListeners() {

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String searchInputText = searchInputEt.getText().toString().trim();
                if (searchInputText != null && !searchInputText.isEmpty()) {
                    searchUrl = URL;
                    searchUrl = searchUrl.replace("###", searchInputText);
                    searchUrl = searchUrl.replace(" ", "%20");
                    app.writeToDisk(FILE_NAME, SAVED_LAST_SEARCH_KEY, searchInputText);
                    makeJsonObjReq(searchUrl);
                }
            }
        });


        tracksListRv.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Collection collection = onlyTracksCollectionList.get(position);

                Intent intent = new Intent(MainActivity.this, StreamSongActivity.class);

                intent.putParcelableArrayListExtra(StreamSongActivity.LIST_KEY, (ArrayList<? extends Parcelable>) onlyTracksCollectionList);
                intent.putExtra(StreamSongActivity.POSITION_KEY, position);
                startActivity(intent);


            }
        }));

        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAdapter(true, false);
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAdapter(false, false);
            }
        });


    }


    private void checkIfThereIsLastSearch() {
        String lastSearch = app.readFromDisk(FILE_NAME, SAVED_LAST_SEARCH_KEY);
        if (lastSearch != null) {
            searchInputEt.setText(lastSearch.replace("%20", " "));
            searchUrl = URL;
            searchUrl = searchUrl.replace("###", lastSearch);
            searchUrl = searchUrl.replace(" ", "%20");

            makeJsonObjReq(searchUrl);
        }
    }

    private void initAdapter(boolean isGrid, boolean isSearchBtn) {

        if (isGrid == mIsGrid && !isSearchBtn) {
            return;
        }

        mIsGrid = isGrid;
        onlyTracksCollectionList = getOnlyTracksCollectionList(collectionList);


        if (isGrid) {
            adapter = new CollectionsAdapter(MainActivity.this, onlyTracksCollectionList, true);
        } else {
            adapter = new CollectionsAdapter(MainActivity.this, onlyTracksCollectionList, false);
        }


        if (isGrid) {
            layoutManager = new GridLayoutManager(MainActivity.this, 3);
        } else {
            layoutManager = new LinearLayoutManager(MainActivity.this);
        }

        tracksListRv.setLayoutManager(layoutManager);
        tracksListRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        tracksListRv.setItemAnimator(new DefaultItemAnimator());
        tracksListRv.setAdapter(adapter);


    }

    @NonNull
    private List<Collection> getOnlyTracksCollectionList(List<Collection> collectionList) {
        List<Collection> onlyTracksCollectionList = new ArrayList<>();
        for (Collection collection : collectionList) {

            if (collection.isTrack()) {
                onlyTracksCollectionList.add(collection);
            }
        }
        return onlyTracksCollectionList;
    }


    private void initProgressDialog() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);
    }


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }


    private void makeJsonObjReq(String searchUrl) {

        initProgressDialog();

        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                searchUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        LoggingHelper.d(TAG, response.toString());

                        searchResultResponse = new SearchResultResponse(response);
                        collectionList = searchResultResponse.getCollectionList();
                        hideProgressDialog();
                        initAdapter(false, true);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(searchInputEt.getWindowToken(), 0);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                DialogHelper.showErrorDialog(MainActivity.this, error.getMessage());
                hideProgressDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }


        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }


}
