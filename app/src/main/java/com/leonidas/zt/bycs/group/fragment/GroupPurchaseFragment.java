package com.leonidas.zt.bycs.group.fragment;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.group.adapter.GroupPurchaseRvAdapter;
import com.leonidas.zt.bycs.index.activity.SearchActivity;

/**
 * Created by 华强 on 2018/1/4.
 * Version: V1.0
 * Description: 拼购Fragment
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseFragment extends BaseFragment {

    private static final String TAG = "GroupPurchaseFragment";
    private XRecyclerView rvGroupPurchase;
    private Toolbar mToolBar;

    @Override
    public void initView(View view) {
        mToolBar = view.findViewById(R.id.tb_group_purchase);
        rvGroupPurchase = view.findViewById(R.id.rv_group_purchase);
        rvGroupPurchase.setAdapter(new GroupPurchaseRvAdapter(mContext, rvGroupPurchase));
        rvGroupPurchase.setLayoutManager(new GridLayoutManager(getContext(),1));
        rvGroupPurchase.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
        rvGroupPurchase.setLoadingMoreEnabled(true);


        initToolbar();
        initRvGroupPurchaseListener();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.leonidas_fragment_group_purchase;
    }

    /**
     * 初始化“拼购RecyclerView”的监听
     */
    private void initRvGroupPurchaseListener() {
        final GroupPurchaseRvAdapter mGroupPurchaseRvAdapter = (GroupPurchaseRvAdapter) rvGroupPurchase.getAdapter();
        rvGroupPurchase.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh");
                // 刷新分类数据
                mGroupPurchaseRvAdapter.initClassificationDate(1, mGroupPurchaseRvAdapter.getCPageSize(), GroupPurchaseRvAdapter.DataOperation.REFRESH.ordinal());
                //刷新拼购商品数据
                mGroupPurchaseRvAdapter.initGroupPurchaseGoodsDate(1, mGroupPurchaseRvAdapter.getGpgPageSize(),
                        1515557064589L,GroupPurchaseRvAdapter.DataOperation.REFRESH.ordinal());
            }

            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: ");
                //加载“下一页”的拼购商品
                mGroupPurchaseRvAdapter.initGroupPurchaseGoodsDate(mGroupPurchaseRvAdapter.getGpgNextPageNum(), mGroupPurchaseRvAdapter.getGpgPageSize(),
                        1515557064589L,GroupPurchaseRvAdapter.DataOperation.MORE.ordinal());
            }

        });
    }

    /**
     * 初始化顶部的ToolBar
     */
    private void initToolbar() {
        /*EditText etSearch = mToolBar.findViewById(R.id.et_search);
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });*/
    }

}
