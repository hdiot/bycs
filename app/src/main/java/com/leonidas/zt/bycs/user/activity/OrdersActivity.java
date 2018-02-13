package com.leonidas.zt.bycs.user.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.adapter.MyPagerAdapter;
import com.leonidas.zt.bycs.user.fragment.AllOrdersFragment;

import java.util.LinkedHashMap;

public class OrdersActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTab;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_order);
        mTab = findViewById(R.id.tab_order);
        mViewPager = findViewById(R.id.viewpager_order);

        initToolbar();
        initTab();
        initViewPager();
    }

    private void initViewPager() {
        LinkedHashMap<String, Fragment> fragments = new LinkedHashMap<>();
        fragments.put("全部", new AllOrdersFragment());
        fragments.put("待付款", new AllOrdersFragment());
        fragments.put("待成团", new AllOrdersFragment());
        fragments.put("待发货", new AllOrdersFragment());
        fragments.put("待收货", new AllOrdersFragment());
        fragments.put("待评价", new AllOrdersFragment());
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(pagerAdapter);
    }

    private void initTab() {
        mTab.setupWithViewPager(mViewPager);

        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initToolbar() {

    }


}
