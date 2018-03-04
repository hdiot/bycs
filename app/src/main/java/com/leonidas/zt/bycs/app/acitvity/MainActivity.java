package com.leonidas.zt.bycs.app.acitvity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.adapter.MyPagerAdapter;
import com.leonidas.zt.bycs.basket.fragment.BasketFragment;
import com.leonidas.zt.bycs.group.fragment.GroupPurchaseFragment;
import com.leonidas.zt.bycs.index.fragment.IndexFragment;
import com.leonidas.zt.bycs.user.fragment.UserFragment;
import com.mebee.coordinatorbehavior.fragment.dummy.TabEntity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private CommonTabLayout mTab;
    private String[] mTabTitles = {"菜市", "拼购", "购物车", "我的"};
    private int[] mIconUnselectedIds = {R.mipmap.market_unselected, R.mipmap.group_unselected,
            R.mipmap.basket_unselected, R.mipmap.mine_unselected};
    private int[] mIconSelectIds = {R.mipmap.market_selected, R.mipmap.group_selected,
            R.mipmap.basket_selected, R.mipmap.mine_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private AlertDialog permissionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDialog();
        initView();
        initPermission();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setPositiveButton("去设置权限", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setMessage("程序需要内存读写权限");
        builder.setCancelable(false);
        permissionDialog = builder.create();
    }

    private void initPermission() {
        final RxPermissions permissions = new RxPermissions(this);
        permissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (!permission.granted){
                            permissionDialog.show();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            initPermission();
                        }
                    }
                });
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }


    /**
     * 初始化视图
     */
    @SuppressLint("ResourceAsColor")
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTab = (CommonTabLayout) findViewById(R.id.common_tab);
        initTab();
        initViewPager();
    }

    private void initTab() {
        for (int i = 0; i < mTabTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTabTitles[i], mIconSelectIds[i], mIconUnselectedIds[i]));
        }

        mTab.setTabData(mTabEntities);

        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new IndexFragment());
        fragments.add(new GroupPurchaseFragment());
        fragments.add(new BasketFragment());
        fragments.add(new UserFragment());

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTab.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
