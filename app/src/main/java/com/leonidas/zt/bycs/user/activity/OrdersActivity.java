package com.leonidas.zt.bycs.user.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.adapter.MyPagerAdapter;
import com.leonidas.zt.bycs.user.fragment.OrdersFragment;

import java.util.LinkedHashMap;

public class OrdersActivity extends FragmentActivity{

    private static final String TAG = "OrdersActivity";

    private Toolbar mToolbar;
    private TabLayout mTab;
    private ViewPager mViewPager;
    private int index = 0;
    private String userId  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getExtData();
        initView();
    }

    private void getExtData() {
        index = getIntent().getIntExtra("index",0);
        userId = getIntent().getStringExtra("userId");
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
        fragments.put("全部", new OrdersFragment(OrdersFragment.ORDER_CLASSIFICATIONS.ALL));
        fragments.put("待付款", new OrdersFragment(OrdersFragment.ORDER_CLASSIFICATIONS.WAIT4PAY));
        fragments.put("待成团", new OrdersFragment(OrdersFragment.ORDER_CLASSIFICATIONS.WAIT4GROUP));
        fragments.put("待发货", new OrdersFragment(OrdersFragment.ORDER_CLASSIFICATIONS.WAIT4DELIVER));
        fragments.put("待收货", new OrdersFragment(OrdersFragment.ORDER_CLASSIFICATIONS.WAIT4RECEIVE));
        fragments.put("待评价", new OrdersFragment(OrdersFragment.ORDER_CLASSIFICATIONS.WAIT4EVALUATE));
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);

        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(index);
    }

    private void initTab() {
        mTab.setupWithViewPager(mViewPager);

        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.toolbar_search);
        MenuItem item = mToolbar.getMenu().findItem(R.id.menu_search);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: ");
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
