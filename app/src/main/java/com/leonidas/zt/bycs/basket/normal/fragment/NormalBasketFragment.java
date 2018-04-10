package com.leonidas.zt.bycs.basket.normal.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.ui.error.ErrorLayout;
import com.leonidas.zt.bycs.basket.normal.adapter.BasketAdapter;
import com.leonidas.zt.bycs.basket.normal.adapter.BasketShopAdapter;
import com.leonidas.zt.bycs.basket.normal.bean.ShopItem;
import com.leonidas.zt.bycs.basket.normal.callback.ShopCheckedCallBack;
import com.leonidas.zt.bycs.basket.normal.presenter.BasketPresenter;
import com.leonidas.zt.bycs.basket.normal.view.BasketView;
import com.leonidas.zt.bycs.user.activity.LoginActivity;

import java.util.List;

/**
 * Created by mebee on 2018/3/18.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class NormalBasketFragment
        extends BaseFragment<BasketView, BasketPresenter> implements BasketView, View.OnClickListener, ShopCheckedCallBack {

    private CheckBox mCheckBox;
    private Button mDelete;
    private TextView mAmount;
    private TextView mEdit;
    private XRecyclerView mXrcv;
    private BasketAdapter mBasketAdapter;
    private BasketShopAdapter mBasketShopAdapter;
    private ErrorLayout mErrorLayout;
    private AlertDialog mDialog;

    @Override
    public void showProgress() {
        mErrorLayout.setLoadingIndicatorVisibility(View.VISIBLE);
        mErrorLayout.setErrorMessage("服务器正在疯狂加载...");
        mErrorLayout.setToDoBtnVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mErrorLayout.setDismissAll();
    }

    @Override
    public void loadMore(List<ShopItem> list) {
        //Log.e("list", "loadMore: " + list.size() );
        mBasketAdapter.loadMore(list);
        mBasketShopAdapter.loadMore(list);
    }

    @Override
    public void refresh(List<ShopItem> list) {
        mXrcv.setNoMore(false);
        mBasketAdapter.refresh(list);
        mBasketShopAdapter.refresh(list);
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
    public void onError(CharSequence s, int type) {
        switch (type) {
            case 0:
                showUnLoginTip();
                break;
            default:
                mErrorLayout.setErrorMessage(s);
                mErrorLayout.setVisibility(View.VISIBLE);
                mErrorLayout.setErrorMessageVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void selectShopFail() {
        if (mBasketShopAdapter != null) {
            mBasketShopAdapter.selectShopFail();
        }
        onError("网络出错，将重新加载", 4);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.refresh();
            }
        }, 5000);
    }

    @Override
    public void selectShopSucceed() {
        if (mBasketShopAdapter != null) {
            mBasketShopAdapter.selectShopSucceed();
        }
    }

    @Override
    public void selectProductFail() {
        if (mBasketShopAdapter != null) {
            mBasketShopAdapter.selectProductSucceed();
        }

        onError("网络出错，将重新加载", 4);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.refresh();
            }
        }, 5000);
    }

    @Override
    public void selectProductSucceed() {
        if (mBasketShopAdapter != null) {
            mBasketShopAdapter.selectProductFail();
        }
    }

    @Override
    protected BasketPresenter createPresenter() {
        return new BasketPresenter();
    }

    @Override
    protected void initView(View view) {
        mCheckBox = view.findViewById(R.id.normal_basket_selected_clear);
        mErrorLayout = view.findViewById(R.id.normal_basket_error_layout);
        mAmount = view.findViewById(R.id.normal_basket_amount_txt);
        mDelete = view.findViewById(R.id.normal_basket_delete_btn);
        mEdit = view.findViewById(R.id.normal_basket_edit_txt);
        mXrcv = view.findViewById(R.id.normal_basket_xrcv);
        initRcv();
        initCheckBox();
        buildDialog();
    }

    private void initCheckBox() {
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBasketShopAdapter.selectAll(mCheckBox.isChecked());
            }
        });
    }


    private void initRcv() {
        mBasketAdapter = new BasketAdapter();
        mBasketShopAdapter = new BasketShopAdapter();
        mBasketShopAdapter.setShopCheckedCallBack(this);

        mXrcv.setFootViewText("正在加载", "已经是最后了");
        mXrcv.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        mXrcv.setArrowImageView(R.mipmap.arrow_down);
        mXrcv.setLoadingMoreEnabled(false);
        mXrcv.setPullRefreshEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mXrcv.setLayoutManager(layoutManager);
        mXrcv.setAdapter(mBasketShopAdapter);
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

        mPresenter.refresh();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.mebee_fragment_normal_basket;
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("登录后才能使用购物车功能哦\n现在就去登陆？");

        builder.setTitle("温馨提示");
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
                mDialog.cancel();
            }
        });
        mDialog = builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_error_layout:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
                mErrorLayout.setDismissAll();
                break;
        }
    }



    private void showUnLoginTip() {
        mErrorLayout.setErrorMessage("登录后才能使用购物车哦！");
        mErrorLayout.setErrorMessageVisibility(View.VISIBLE);
        mErrorLayout.setLoadingIndicatorVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setToDoBtnVisibility(View.VISIBLE);
        mErrorLayout.setToDoBtnOnClickListener(this);
        mErrorLayout.setToDoBtnText("登录");
    }

    @Override
    public void onChecked(String id, int position) {
        mPresenter.selectShop(id, true);
    }

    @Override
    public void onUnchecked(String id, int position) {
        mPresenter.selectShop(id, false);
    }

    @Override
    public void onItemCheckedChange(List<String> ids, boolean check) {
        mPresenter.selectProduct(ids, check);
    }

    @Override
    public void onAllCheck(boolean allCheck) {
        mCheckBox.setChecked(allCheck);
    }
}
