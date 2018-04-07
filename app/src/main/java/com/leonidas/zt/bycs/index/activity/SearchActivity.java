package com.leonidas.zt.bycs.index.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.ui.error.ErrorLayout;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.SearchAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.SearchShop;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.util.List;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "SearchActivity";

    private Toolbar mToolbar;
    private SearchView mSearchView;
    private RecyclerView mRcv;
    private ErrorLayout mErrorLayout;
    private TextView mSearchTV;
    private SearchAdapter mSearchAdapter = new SearchAdapter();

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_search);
        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_search_activity);
        mRcv = findViewById(R.id.rv_search_activity);
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
        if (!mSearchView.getQuery().toString().equals("") && mSearchView.getQuery() != null) {
            postQuery(mSearchView.getQuery().toString());
        } else {
            Toast.makeText(this, "请输入你想买的商品、商店的名称", Toast.LENGTH_SHORT).show();
        }
    }

    private void postQuery(String query){
        String params = "?selectName=" + query;
        httpHelper.doGet(Constant.API.search + params, new BaseCallback<ResMessage<Data<List<SearchShop>>>>() {


            @Override
            public void OnSuccess(Response response, ResMessage<Data<List<SearchShop>>> dataResMessage) {
                try {
                    mSearchAdapter.add(dataResMessage.getData().getShops());
                    mErrorLayout.setVisibility(View.GONE);
                } catch (NullPointerException e){
                    nullResult();
                } finally {
                    if (dataResMessage.getData().getShops()==null || dataResMessage.getData().getShops().size() ==0)
                        nullResult();
                }
            }

            @Override
            public void onError(Response response, int errCode, Exception e) {
                Toast.makeText(SearchActivity.this, "服务器出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(SearchActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {
                Toast.makeText(SearchActivity.this, hint, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nullResult() {
        Toast.makeText(this, "没有找到相应商家、商品，换个试试吧", Toast.LENGTH_SHORT).show();
        /*mErrorLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setErrorMessage("没有找到相应商家、商品，换个试试吧");*/
    }

    private void initXRCV() {
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mRcv.setAdapter(mSearchAdapter);
    }

    private void initTab() {
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Query", "onQueryTextSubmit: " +query);
        if (!query.equals("")) {
            postQuery(query);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Query", "onQueryTextChange: ");
        return false;
    }
}
