package com.leonidas.zt.bycs.app.acitvity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.adapter.MyPagerAdapter;
import com.leonidas.zt.bycs.app.utils.BottomNavigationViewHelper;
import com.leonidas.zt.bycs.index.fragment.IndexFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private MenuItem mMenuItem;
    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);

        initNavigation();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new IndexFragment("haha"));
        fragments.add(new IndexFragment("hehe"));
        fragments.add(new IndexFragment("hihi"));
        fragments.add(new IndexFragment("xixi"));

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    /**
     *初始化BottomNavigationView
     */
    private void initNavigation() {

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_index:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_group:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_basket:
                        mViewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_user:
                        mViewPager.setCurrentItem(3);
                        return true;
                }
                return false;
            }
        };
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mMenuItem != null) {
            mMenuItem.setChecked(false);
        } else {
            mNavigation.getMenu().getItem(0).setChecked(false);
        }
        mMenuItem = mNavigation.getMenu().getItem(position);
        mMenuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
