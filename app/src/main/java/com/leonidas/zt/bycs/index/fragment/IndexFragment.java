package com.leonidas.zt.bycs.index.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.activity.SearchActivity;
import com.leonidas.zt.bycs.index.adapter.RcvIndexAdapter;
import com.leonidas.zt.bycs.index.adapter.RcvShopAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shops;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;


public class IndexFragment extends BaseFragment {

    private static final String TAG = "IndexFragment";
    private Toolbar mToolBar;
    private XRecyclerView mRcvIndex;


    @Override
    public void initView(View view) {
        mToolBar = (Toolbar) view.findViewById(R.id.index_toolbar);
        mRcvIndex = (XRecyclerView) view.findViewById(R.id.rcv_index);

        initToolbar();
        initRcvRcmShop();
    }

    /**
     * 初始化 商家列表
     */
    private void initRcvRcmShop() {

        mRcvIndex.setAdapter(new RcvIndexAdapter());
        mRcvIndex.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcvIndex.setLoadingMoreEnabled(false);
        mRcvIndex.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                // 网络请求

                // 更新数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRcvIndex.refreshComplete();
                        Log.d(TAG, "run: ");
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                // 网络请求

                // 更新数据

            }
        });
    }


    /**
     * 初始化 顶部 ToolBar
     */
    private void initToolbar() {
        //mToolBar.inflateMenu(R.menu.index_toolbar);
        EditText mSearchEditText = (EditText) mToolBar.findViewById(R.id.edit_search);
        // mSearchEditText.clearFocus();
        mSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_index;
    }

}
