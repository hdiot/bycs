package com.leonidas.zt.bycs.index.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.leonidas.zt.bycs.R;

public class ShopInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private boolean isNeedPading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
        findView();
        initView();
    }

    private void findView() {
        setContentView(R.layout.mebee_activity_shop_info);
        mToolbar = findViewById(R.id.toolbar_shop_info);
    }

    private void initView() {
        initToolbar();
    }

    /**
     * 
     */
    @SuppressLint("NewApi")
    private void initToolbar() {
        if (isNeedPading) {
            mToolbar.setPadding(0,getStatusBarHeight(),0,0);
        }
        mToolbar.inflateMenu(R.menu.toolbar_basket_only);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopInfoActivity.this, "navigation clicked", Toast.LENGTH_SHORT).show();
            }
        });
        
        mToolbar.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(ShopInfoActivity.this, "Item clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isNeedPading = true;
        }
    }

    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d("CompatToolbar", "状态栏高度：" + px2dp(statusBarHeight) + "dp");
        return statusBarHeight;
    }

    private float px2dp(float pxVal) {
        final float scale = getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
}
