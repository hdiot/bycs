package com.leonidas.zt.bycs.group.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.adapter.MyPagerAdapter;
import com.leonidas.zt.bycs.group.fragment.ClassificationGpProductsFragment;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsClassificationVO;

import java.util.ArrayList;
import java.util.List;

public class ClassificationActivity extends AppCompatActivity {

    private static final String TAG = "ClassificationActivity";


    private Toolbar tbClassification;
    private SearchView svClassification;
    private SlidingTabLayout tabClassification;
    private ViewPager vpClassification;
    private List<GroupPurchaseGoodsClassificationVO.DataBean.ProductCategoriesBean.ListBean> mClassifications;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int mPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        getExtraData();
        initView();
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mClassifications = (List<GroupPurchaseGoodsClassificationVO.DataBean.ProductCategoriesBean.ListBean>) intent.getSerializableExtra("classifications");
        mPosition = intent.getIntExtra("position", 0);
    }

    private void initView() {
        tbClassification = findViewById(R.id.tb_classification);
        svClassification = findViewById(R.id.sv_classification);
        tabClassification = findViewById(R.id.tab_classification);
        vpClassification = findViewById(R.id.vp_classification);

        initToolbar();
        initSearchView();
        initViewPager();
    }


    private void initViewPager() {

        String[] titles = new String[mClassifications.size()];

        for (int index = 0; index < mClassifications.size(); index++) {
            titles[index] = mClassifications.get(index).getCategoryName();
            ClassificationGpProductsFragment fragment = new ClassificationGpProductsFragment();
            fragment.setCategoryId(mClassifications.get(index).getCategoryId() + "");
            fragments.add(fragment);
        }


        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        vpClassification.setOffscreenPageLimit(6 );
        vpClassification.setAdapter(pagerAdapter);
        tabClassification.setViewPager(vpClassification, titles);
        tabClassification.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpClassification.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vpClassification.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabClassification.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        vpClassification.setCurrentItem(mPosition);
    }

    private void initSearchView() {
        svClassification.onActionViewExpanded();
        svClassification.clearFocus();
        svClassification.setSubmitButtonEnabled(true);
    }

    private void initToolbar() {
        tbClassification.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
