package com.leonidas.zt.bycs.index.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.ui.error.ErrorLayout;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.Product;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar mToobar;
    private SearchView mSearchView;
    private XRecyclerView mXrcv;
    private ErrorLayout mErrorLayout;
    private TextView mSearchTV;

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_search);
        initView();
    }

    private void initView() {
        mToobar = findViewById(R.id.toolbar_search_activity);
        mXrcv = findViewById(R.id.xr_search_activity);
        mErrorLayout = findViewById(R.id.error_layout_search_activity);
        mSearchView = findViewById(R.id.searchView_search_activity);
        mSearchTV = findViewById(R.id.txt_search_search_activity);
        initToolbar();
        initSearchView();
        initTab();
        initXRCV();
    }

    private void initSearchView() {
        mSearchView.onActionViewExpanded();
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setOnQueryTextListener(this);

        mSearchTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doQuery();
            }
        });
    }

    private void doQuery() {
    }

    private void postQuery(){
        httpHelper.doGet(Constant.API.getProducts, null, new BaseCallback<ResMessage<Data<Product>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<Product>> dataResMessage) {

            }

            @Override
            public void onError(Response response, int errCode, Exception e) {

            }

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {

            }
        });
    }

    private void initXRCV() {

    }

    private void initTab() {
    }

    private void initToolbar() {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Query", "onQueryTextSubmit: " +query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Query", "onQueryTextChange: ");
        return false;
    }
}
