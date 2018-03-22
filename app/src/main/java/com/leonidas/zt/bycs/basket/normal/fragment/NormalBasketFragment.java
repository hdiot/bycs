package com.leonidas.zt.bycs.basket.normal.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.ui.error.ErrorLayout;
import com.leonidas.zt.bycs.basket.normal.adapter.BasketAdapter;
import com.leonidas.zt.bycs.basket.normal.bean.ShopItem;
import com.leonidas.zt.bycs.basket.normal.presenter.BasketPresenter;
import com.leonidas.zt.bycs.basket.normal.view.BasketView;

import java.util.List;

/**
 * Created by mebee on 2018/3/18.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class NormalBasketFragment
        extends BaseFragment<BasketView, BasketPresenter> implements BasketView {

    private CheckBox mCheckBox;
    private Button mDelete;
    private TextView mAmount;
    private TextView mEdit;
    private XRecyclerView mXrcv;
    private BasketAdapter mBasketAdapter;
    private ErrorLayout mErrorLayout;


    @Override
    public void showProgress() {
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void loadMore(List<ShopItem> list) {
        Log.e("list", "loadMore: " + list.size() );
        mBasketAdapter.loadMore(list);
    }

    @Override
    public void refresh(List<ShopItem> list) {
        mXrcv.setNoMore(false);
        mBasketAdapter.refresh(list);
    }


    @Override
    public void pullOrder() {

    }

    @Override
    public void deleteBasketItem() {

    }

    @Override
    public void showNoMoreData() {
        mXrcv.setNoMore(true);
    }

    @Override
    public void refreshFinish() {
        mXrcv.refreshComplete();
    }

    @Override
    public void loadMoreFinish() {
        mXrcv.loadMoreComplete();
    }

    @Override
    protected BasketPresenter createPresenter() {
        return new BasketPresenter();
    }

    @Override
    protected void initView(View view) {
        mCheckBox = view.findViewById(R.id.normal_basket_selected_checkbox);
        mErrorLayout = view.findViewById(R.id.normal_basket_error_layout);
        mAmount = view.findViewById(R.id.normal_basket_amount_txt);
        mDelete = view.findViewById(R.id.normal_basket_delete_btn);
        mEdit = view.findViewById(R.id.normal_basket_edit_txt);
        mXrcv = view.findViewById(R.id.normal_basket_xrcv);

        initRcv();
    }

    private void initRcv() {
        mBasketAdapter = new BasketAdapter();
        mXrcv.setFootViewText("正在加载", "已经是最后了");
        mXrcv.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        mXrcv.setArrowImageView(R.mipmap.arrow_down);
        mXrcv.setLayoutManager(new LinearLayoutManager(getContext()));
        mXrcv.setAdapter(mBasketAdapter);
        mXrcv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });

        mPresenter.loadMore();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.mebee_fragment_normal_basket;
    }
}
