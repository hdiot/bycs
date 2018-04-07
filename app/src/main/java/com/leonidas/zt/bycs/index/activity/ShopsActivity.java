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

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvShopAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.RequestDataCase;
import com.leonidas.zt.bycs.index.bean.RequestDataResult;
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
    //private SearchView mSearchView;
    private XRecyclerView mRecyclerView;
    private RcvShopAdapter mRcvShopsAdapter = new RcvShopAdapter(null);

    private RequestDataResult requestDataResult = RequestDataResult.UNKNOW;
    private RequestDataCase requestDataCase = RequestDataCase.FIRST;

    private final int DEFAULT_PAGE = 1;
    private int currentPage = 1;
    private int totalPage = 1;
    private int pageSize = 15;
    private boolean isLastPage = true;

    private Shops mShops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_shops);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rcv_shops);
        mToolbar = findViewById(R.id.toolbar);
        mTab = findViewById(R.id.tab_order);

        initTab();
        initToolbar();
        initRecyclerView();
        getData(DEFAULT_PAGE);
    }

    private void initRecyclerView() {
        mRecyclerView.setLoadingMoreProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        mRecyclerView.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        mRecyclerView.setArrowImageView(R.mipmap.arrow_down);
        mRcvShopsAdapter.setDisplayMoreTipEnable(false);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setAdapter(mRcvShopsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }



    private void refresh() {
        requestDataCase = RequestDataCase.REFRESH;
        getData(DEFAULT_PAGE);
    }

    private void loadMore() {
        if (!isLastPage) {
            getData(currentPage + 1);
        } else {
            mRecyclerView.setNoMore(true);
        }
    }

    private void getUpdateState(RequestDataResult result) {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
    }

    private void getData(int num) {

        StringBuffer params = new StringBuffer();
        params.append("?pageNum=");
        params.append(num);
        params.append("&pageSize=");
        params.append(pageSize);
        params.append("&orderBy=");
        params.append(mTab.getSelectedTabPosition());
        Log.e(TAG, "params" + params.toString());
        //String params = "?shopName=" + name + "&pageNum=" + num +"&pageSize" + size + "&orderBy=" + mTab.getSelectedTabPosition();
        OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
        okHttpHelper.doGet(Constant.API.getShops+params.toString(),
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

                mShops = dataResMessage.getData().getShops();
                currentPage = mShops.getPageNum();
                totalPage = mShops.getTotalPages();
                pageSize = mShops.getPageSize();
                isLastPage = mShops.isLastPage();

                //errorLayout.setVisibility(View.GONE);
                getUpdateState(RequestDataResult.SUCCESS);

                if (mShops.getList() != null && mShops.getList().size() != 0) {
                    Log.e("null", "yes: ");
                    switch (requestDataCase) {
                        case FIRST:
                            requestDataCase = RequestDataCase.LOAD_MORE;
                            mRcvShopsAdapter.loadMore(mShops.getList());
                            break;
                        case REFRESH:
                            requestDataCase = RequestDataCase.LOAD_MORE;
                            mRcvShopsAdapter.refresh(mShops.getList());
                            break;
                        case LOAD_MORE:
                            mRcvShopsAdapter.loadMore(mShops.getList());
                            break;
                    }
                    //errorLayout.setVisibility(View.GONE);
                } else if (requestDataCase == RequestDataCase.FIRST ||
                        requestDataCase == RequestDataCase.REFRESH) {
                    //errorOrNull("没有订单");
                }



            }

            @Override
            public void onError(Response response,
                                int errCode,
                                Exception e) {
                getUpdateState(RequestDataResult.ERROR);
            }

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                getUpdateState(RequestDataResult.FAIL);
            }

            @Override
            public void onBzError(Response response,
                                  int code,
                                  String hint,
                                  String data) {
                getUpdateState(RequestDataResult.ERROR);
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
                refresh();
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
