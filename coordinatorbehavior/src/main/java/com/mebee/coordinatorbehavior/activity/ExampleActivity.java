package com.mebee.coordinatorbehavior.activity;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaeger.library.StatusBarUtil;
import com.mebee.coordinatorbehavior.R;
import com.mebee.coordinatorbehavior.behavior.AppBarLayoutOverScrollViewBehavior;
import com.mebee.coordinatorbehavior.fragment.ItemFragment1;
import com.mebee.coordinatorbehavior.fragment.MyFragmentPagerAdapter;
import com.mebee.coordinatorbehavior.fragment.dummy.TabEntity;
import com.mebee.coordinatorbehavior.widget.CircleImageView;
import com.mebee.coordinatorbehavior.widget.NoScrollViewPager;
import com.mebee.coordinatorbehavior.widget.RoundProgressBar;

import java.util.ArrayList;
import java.util.List;

public class ExampleActivity extends AppCompatActivity {

    private ImageView mZoomIv;
    private Toolbar mToolBar;
    private ViewGroup titleContainer;
    private AppBarLayout mAppBarLayout;
    private ViewGroup titleCenterLayout;
    private RoundProgressBar progressBar;
    private ImageView mSettingIv, mMsgIv;
    private CircleImageView mAvater;
    private CommonTabLayout mTablayout;
    private NoScrollViewPager mViewPager;
    //private ViewPager mViewPager;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;
    private int lastState = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        findId();
        initListener();
        initTab();
        initStatus();

    }

    /**
     * 初始化id
     */
    private void findId() {
        mZoomIv = (ImageView) findViewById(R.id.uc_zoomiv);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        titleContainer = (ViewGroup) findViewById(R.id.title_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        titleCenterLayout = (ViewGroup) findViewById(R.id.title_center_layout);
        progressBar = (RoundProgressBar) findViewById(R.id.uc_progressbar);
        mSettingIv = (ImageView) findViewById(R.id.uc_setting_iv);
        mMsgIv = (ImageView) findViewById(R.id.uc_msg_iv);
        mAvater = (CircleImageView) findViewById(R.id.uc_avater);
        mTablayout = (CommonTabLayout) findViewById(R.id.uc_tablayout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.uc_viewpager);
//        mViewPager = (ViewPager) findViewById(R.id.uc_viewpager);
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        fragments = getFragments();
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, getNames());

        mTablayout.setTabData(mTabEntities);
        mViewPager.setAdapter(myFragmentPagerAdapter);

    }

    /**
     * 绑定事件
     */
    private void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                Log.d("percent", "onOffsetChanged: " + percent);
                if (titleCenterLayout != null && mAvater != null && mSettingIv != null && mMsgIv != null) {
                    titleCenterLayout.setAlpha(percent);
                    StatusBarUtil.setTranslucentForImageView(ExampleActivity.this, (int) (255f * percent), null);
                    if (percent == 0) {
                        groupChange(1f, 1);
                    } else if (percent == 1) {
                        if (mAvater.getVisibility() != View.GONE) {
                            mAvater.setVisibility(View.GONE);
                        }
                        groupChange(1f, 2);
                    } else {
                        if (mAvater.getVisibility() != View.VISIBLE) {
                            mAvater.setVisibility(View.VISIBLE);
                        }
                        groupChange(percent, 0);
                    }

                }
            }
        });
        AppBarLayoutOverScrollViewBehavior myAppBarLayoutBehavoir = (AppBarLayoutOverScrollViewBehavior)
                ((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();
        myAppBarLayoutBehavoir.setOnProgressChangeListener(new AppBarLayoutOverScrollViewBehavior.onProgressChangeListener() {
            @Override
            public void onProgressChange(float progress, boolean isRelease) {
                progressBar.setProgress((int) (progress * 360));
                if (progress == 1 && !progressBar.isSpinning && isRelease) {
                    // 刷新viewpager里的fragment
                }
                if (mMsgIv != null) {
                    if (progress == 0 && !progressBar.isSpinning) {
                        mMsgIv.setVisibility(View.VISIBLE);
                    } else if (progress > 0 && mSettingIv.getVisibility() == View.VISIBLE) {
                        mMsgIv.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化状态栏位置
     */
    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以下不支持状态栏变色
            //注意了，这里使用了第三方库 StatusBarUtil，目的是改变状态栏的alpha
            StatusBarUtil.setTransparentForImageView(ExampleActivity.this, null);
            //这里是重设我们的title布局的topMargin，StatusBarUtil提供了重设的方法，但是我们这里有两个布局
            //TODO 关于为什么不把Toolbar和@layout/layout_uc_head_title放到一起，是因为需要Toolbar来占位，防止AppBarLayout折叠时将title顶出视野范围
            int statusBarHeight = getStatusBarHeight(ExampleActivity.this);
            CollapsingToolbarLayout.LayoutParams lp1 = (CollapsingToolbarLayout.LayoutParams) titleContainer.getLayoutParams();
            lp1.topMargin = statusBarHeight;
            titleContainer.setLayoutParams(lp1);
            CollapsingToolbarLayout.LayoutParams lp2 = (CollapsingToolbarLayout.LayoutParams) mToolBar.getLayoutParams();
            lp2.topMargin = statusBarHeight;
            mToolBar.setLayoutParams(lp2);
        }
    }

    /**
     * @param alpha
     * @param state 0-正在变化 1展开 2 关闭
     */
    public void groupChange(float alpha, int state) {
        lastState = state;

        mSettingIv.setAlpha(alpha);
        mMsgIv.setAlpha(alpha);

        switch (state) {
            case 1://完全展开 显示白色
                mMsgIv.setImageResource(R.mipmap.icon_msg);
                mSettingIv.setImageResource(R.mipmap.icon_setting);
                mViewPager.setNoScroll(false);
                break;
            case 2://完全关闭 显示黑色
                mMsgIv.setImageResource(R.mipmap.icon_msg_black);
                mSettingIv.setImageResource(R.mipmap.icon_setting_black);
                mViewPager.setNoScroll(false);
                break;
            case 0://介于两种临界值之间 显示黑色
                if (lastState != 0) {
                    mMsgIv.setImageResource(R.mipmap.icon_msg_black);
                    mSettingIv.setImageResource(R.mipmap.icon_setting_black);
                }
                mViewPager.setNoScroll(true);
                break;
        }
    }


    /**
     * 获取状态栏高度
     * ！！这个方法来自StatusBarUtil,因为作者将之设为private，所以直接copy出来
     *
     * @param context context
     * @return 状态栏高度
     */
    private int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 假数据
     *
     * @return
     */
    public String[] getNames() {
        String[] mNames = new String[]{"Weather", "Moon", "Like", "Fans"};
        for (String str : mNames) {
            mTabEntities.add(new TabEntity( str));
        }

        return mNames;
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ItemFragment1());
        fragments.add(new ItemFragment1());
        fragments.add(new ItemFragment1());
        fragments.add(new ItemFragment1());
        return fragments;
    }
}
