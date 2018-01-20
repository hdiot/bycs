package com.leonidas.zt.bycs.index.activity;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvShopAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shops;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class ShopsActivity extends AppCompatActivity {
    private static final String TAG = "ShopsActivity";

    private Toolbar mToolbar;
    private TabLayout mTab;
    private SearchView mSearchView;
    private XRecyclerView mRecyclerView;
    private RcvShopAdapter mRcvShopsAdapter = new RcvShopAdapter(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_shops);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rcv_shops);
        mSearchView = findViewById(R.id.search_view);
        mToolbar = findViewById(R.id.toolbar);
        mTab = findViewById(R.id.tab_order);
        getData("","1","20");
        initTab();
        initToolbar();
        initSearchView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRcvShopsAdapter.setDisplayMoreTipEnable(false);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setAdapter(mRcvShopsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                        Log.d(TAG, "run: ");
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.loadMoreComplete();
                        Log.d(TAG, "run: ");
                    }
                }, 1000);
            }
        });
    }

    private void getData(String name,String num,String size) {

        String params = "?shopName=" + name + "&pageNum=" + num +"&pageSize" + size;
        OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
        okHttpHelper.doGet(Constant.API.getShops+params,
                new BaseCallback<ResMessage<Data<Shops>>>() {

            @Override
            public void OnSuccess(Response response,
                                  ResMessage<Data<Shops>> dataResMessage) {

                Log.d(TAG, "OnSuccess: " +
                        dataResMessage
                                .getData()
                                .getShops()
                                .getList());

                mRcvShopsAdapter.loadMore(dataResMessage
                        .getData()
                        .getShops()
                        .getList());
            }

            @Override
            public void onError(Response response,
                                int errCode,
                                Exception e) {

            }

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request,
                                  IOException e) {

            }

            @Override
            public void onBzError(Response response,
                                  int code,
                                  String hint,
                                  String data) {

            }
        });
    }



    private void initSearchView() {
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
            }
        });

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initTab() {
        mTab.addTab(mTab.newTab().setText("好评优先"));
        mTab.addTab(mTab.newTab().setText("销量高优先"));
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
