package com.leonidas.zt.bycs.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.adapter.MyPagerAdapter;
import com.leonidas.zt.bycs.index.bean.Category;
import com.leonidas.zt.bycs.index.fragment.SortProductsFragment;

import java.util.ArrayList;
import java.util.List;

public class SortActivity extends AppCompatActivity {

    private static final String TAG = "SortActivity";


    private Toolbar mToolbar;
    private SearchView mSearchView;
    private SlidingTabLayout mTabLayout;
    private TextView mSearch;
    private ViewPager mViewPager;
    private List<Category> mSorts;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int mPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_sort);
        getExtraData();
        initView();
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mSorts = (List<Category>) intent.getSerializableExtra("sorts");
        mPosition = intent.getIntExtra("position", 0);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_sort);
        mSearchView = findViewById(R.id.searchView_sort);
        mTabLayout = findViewById(R.id.tab_sort);
        mViewPager = findViewById(R.id.viewpager_sort);
        mSearch =  (TextView) findViewById(R.id.txt_search_sort);

        initToolbar();
        initSearchView();
        initViewPager();
    }


    private void initViewPager() {

        String[] titles = new String[mSorts.size()];

        for (int index = 0; index < mSorts.size(); index++) {
            titles[index] = mSorts.get(index).getCategoryName();
            SortProductsFragment fragment = new SortProductsFragment();
            fragment.setCategoryId(mSorts.get(index).getCategoryId());
            fragments.add(fragment);
        }


        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(6 );
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setViewPager(mViewPager, titles);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        mViewPager.setCurrentItem(mPosition);
    }

    private void initSearchView() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SortActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        };

        mSearchView.onActionViewExpanded();
        mSearchView.clearFocus();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnClickListener(onClickListener);

        mSearch.setOnClickListener(onClickListener);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
