package com.leonidas.zt.bycs.basket.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.adapter.MyPagerAdapter;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.basket.normal.fragment.NormalBasketFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华强 on 2018/1/29.
 * Version: V1.0
 * Description:拼购车Fragment（购物车模块共有两种购物车【“菜市篮”和“拼购篮”】）
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class BasketFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llVegetableMarketCart;
    private TextView tvVegetableMarketCart;
    private LinearLayout llGroupMarketCart;
    private TextView tvGroupMarketCart;
    private ViewPager vpCart;

    @Override
    public void initView(View view) {
        findViews(view);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NormalBasketFragment());
        fragments.add(new GroupPurchaseBasketFragment());
        vpCart.setAdapter(new MyPagerAdapter(getFragmentManager(),fragments));
        initListener();
    }

    private void initListener() {
        llVegetableMarketCart.setOnClickListener(this);
        llGroupMarketCart.setOnClickListener(this);
        vpCart.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: //菜市篮（普通商城购物车）
                        tvGroupMarketCart.setTextColor(Color.parseColor("#ED8B75")); //未选中状态
                        tvVegetableMarketCart.setTextColor(Color.parseColor("#FEFCFC")); //选中状态
                        break;
                    case 1: //拼购蓝（拼购商城购物车）
                        tvGroupMarketCart.setTextColor(Color.parseColor("#FEFCFC")); //选中状态
                        tvVegetableMarketCart.setTextColor(Color.parseColor("#ED8B75")); //未选中状态
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void findViews(View view) {
        llVegetableMarketCart = view.findViewById( R.id.ll_vegetable_market_cart );
        tvVegetableMarketCart = view.findViewById( R.id.tv_vegetable_market_cart );
        llGroupMarketCart = view.findViewById( R.id.ll_group_market_cart );
        tvGroupMarketCart = view.findViewById( R.id.tv_group_market_cart );
        vpCart = view.findViewById( R.id.vp_cart );
    }

    @Override
    public int getLayoutResource() {
        return R.layout.leonidas_fragment_basket;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_vegetable_market_cart:
                vpCart.setCurrentItem(0);
                tvGroupMarketCart.setTextColor(Color.parseColor("#ED8B75")); //未选中状态
                tvVegetableMarketCart.setTextColor(Color.parseColor("#FEFCFC")); //选中状态
                break;
            case R.id.ll_group_market_cart:
                vpCart.setCurrentItem(1);
                tvGroupMarketCart.setTextColor(Color.parseColor("#FEFCFC")); //选中状态
                tvVegetableMarketCart.setTextColor(Color.parseColor("#ED8B75")); //未选中状态
                break;
        }
    }

}
