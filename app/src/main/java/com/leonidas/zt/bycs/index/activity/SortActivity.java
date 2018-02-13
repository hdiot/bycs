package com.leonidas.zt.bycs.index.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvShopAdapter;
import com.leonidas.zt.bycs.index.bean.Category;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shops;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class SortActivity extends AppCompatActivity {

    private static final String TAG = "SortActivity";


    private Toolbar mToolbar;
    private SearchView mSearchView;
    private TabLayout mTab;
    private XRecyclerView mRecyclerView;
    private RcvShopAdapter mRcvShopsAdapter = new RcvShopAdapter(null);
    private String mCategoryId;
    private List<Category> mSorts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_sort);
        getExtraData();
        initView();
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mCategoryId = intent.getStringExtra("categoryId");
        mSorts = (List<Category>) intent.getSerializableExtra("sorts");
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_sort);
        mTab = findViewById(R.id.tab_sort);
        mSearchView = findViewById(R.id.searchView_sort);
        mRecyclerView = findViewById(R.id.xrcv_sort);

        initToolbar();
        initSearchView();
        initTab();
        initXRCV();
    }

    private void initSearchView() {
        mSearchView.onActionViewExpanded();
        mSearchView.clearFocus();
        mSearchView.setSubmitButtonEnabled(true);
    }

    private void initXRCV() {
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

        getData("", "","");
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


    private void initTab() {
        if (mSorts != null) {
            for (Category mSort : mSorts) {
                mTab.addTab(mTab.newTab().setText(mSort.getCategoryName()));
            }
        }
    }

    private void initToolbar() {
        /*mToolbar.inflateMenu(R.menu.toolbar_search);

        Menu menu = mToolbar.getMenu();

        MenuItem menuItem = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.clearFocus();
        searchView.setSubmitButtonEnabled(true);

        ImageView imageView = searchView.findViewById(R.id.search_go_btn);
        TextView textView = searchView.findViewById(R.id.search_src_text);
        textView.setBackgroundColor(Color.TRANSPARENT);
        textView.setTextColor(Color.WHITE);

        *//*searchView.onActionViewExpanded();

        searchView.setIconifiedByDefault(false);*//*
        searchView.setIconified(false);

        imageView.setImageResource(R.mipmap.search);
    */
    }

}
