package com.leonidas.zt.bycs.index.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.ui.loader.MebeeLoader;
import com.leonidas.zt.bycs.index.activity.SearchActivity;
import com.leonidas.zt.bycs.index.adapter.RcvIndexAdapter;
import com.leonidas.zt.bycs.index.bean.RequestDataResult;


public class IndexFragment extends BaseFragment {

    private static final String TAG = "IndexFragment";
    private Toolbar mToolBar;
    private XRecyclerView mRcvIndex;
    private TextView mSearchView;
    private SwipeRefreshLayout mRefreshLayout;
    private RcvIndexAdapter mRcvIndexAdapter;
    private RequestDataResult SORTS_RESULT = RequestDataResult.UNKNOW;
    private RequestDataResult SHOPS_RESULT = RequestDataResult.UNKNOW;


    @Override
    public void initView(View view) {
        mToolBar = (Toolbar) view.findViewById(R.id.index_toolbar);
        mRcvIndex = (XRecyclerView) view.findViewById(R.id.rcv_index);
        mSearchView = view.findViewById(R.id.txt_search);
        MebeeLoader.showLoading(mContext);
        initSearchView();
        initRecyclerView();
    }

    private void initRecyclerView() {

        mRcvIndex.setLoadingMoreEnabled(true);
        mRcvIndex.setPullRefreshEnabled(true);
        mRcvIndex.setLoadingMoreProgressStyle(AVLoadingIndicatorView.BallScaleMultiple);
        mRcvIndex.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleMultiple);
        mRcvIndex.setFootViewText("疯狂加载中...","尴尬^..^ | 数据加载完了");
        mRcvIndex.setArrowImageView(R.mipmap.arrow_down);
        mRcvIndex.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRcvIndexAdapter.refresh();
            }

            @Override
            public void onLoadMore() {
                mRcvIndexAdapter.loadMore();
            }
        });


        mRcvIndexAdapter = new RcvIndexAdapter();
        mRcvIndexAdapter.setReqShopsDataListener(new RcvIndexAdapter.RequestDataListener() {
            @Override
            public void resultState(RequestDataResult state) {
                Log.d(TAG, "resultState: setReqShopsDataListener--" + state.name());
                SHOPS_RESULT = state;
                isUpdateDataSucceed();
            }
        });


        mRcvIndexAdapter.setReqSortsDataListener(new RcvIndexAdapter.RequestDataListener() {
            @Override
            public void resultState(RequestDataResult state) {
                Log.d(TAG, "resultState: setReqSortsDataListener--" + state.name());
                SORTS_RESULT = state;
                isUpdateDataSucceed();
            }
        });

        mRcvIndex.setAdapter(mRcvIndexAdapter);
        mRcvIndex.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void isUpdateDataSucceed(){

        if (SHOPS_RESULT == RequestDataResult.LAST_PAGE) {
            Log.d(TAG, "isUpdateDataSucceed: no more");
            mRcvIndex.setNoMore(true);
        }

        if (SORTS_RESULT == RequestDataResult.SUCCESS &&
                SHOPS_RESULT == RequestDataResult.SUCCESS){
            mRcvIndex.refreshComplete();
            mRcvIndex.loadMoreComplete();
            MebeeLoader.stopLoading();
            SHOPS_RESULT = RequestDataResult.UNKNOW;
            SORTS_RESULT = RequestDataResult.UNKNOW;
        }

        if (SORTS_RESULT == RequestDataResult.ERROR ||
                SORTS_RESULT == RequestDataResult.FAIL ||
                SHOPS_RESULT == RequestDataResult.ERROR ||
                SHOPS_RESULT == RequestDataResult.FAIL){
            Log.d(TAG, "isUpdateDataSucceed: error");
            SHOPS_RESULT = RequestDataResult.UNKNOW;
            SORTS_RESULT = RequestDataResult.UNKNOW;
            mRcvIndex.loadMoreComplete();
            mRcvIndex.refreshComplete();
            MebeeLoader.stopLoading();
            // TODO: 2018/3/2 show a error message or dialog
            Toast.makeText(mContext, "尴尬 |@ @| 出错了", Toast.LENGTH_SHORT).show();
        }

        if ((SORTS_RESULT != RequestDataResult.UNKNOW ||
                SHOPS_RESULT != RequestDataResult.UNKNOW) ){
            Log.d(TAG, "isUpdateDataSucceed: loading");
        }
    }

    private void initSearchView() {

        mSearchView.setOnClickListener(new View.OnClickListener() {
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
