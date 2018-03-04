package com.leonidas.zt.bycs.index.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;

/**
 * Created by mebee on 2018/2/28.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
@SuppressLint("ValidFragment")
public class ShopInfoFragment extends BaseFragment {
    private final String mShopId;

    public ShopInfoFragment(String id) {
        super();
        mShopId = id;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_shop_info;
    }

    @Override
    public void initData() {

    }
}
