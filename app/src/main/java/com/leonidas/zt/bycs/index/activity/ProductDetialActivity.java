package com.leonidas.zt.bycs.index.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.index.adapter.RcvCommentAdapter;
import com.mcxtzhang.lib.AnimShopButton;

public class ProductDetialActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mProductImg;
    private TextView mProductNameTxt;
    private TextView mProductPriceTxt;
    private TextView mProductStockTxt;
    private TextView mProductLimitTxt;
    private AnimShopButton mAnimShopButton;
    private RecyclerView mCommentsRCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_product_detial);
        initView();
    }

    private void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d("CompatToolbar", "状态栏高度：" + px2dp(statusBarHeight) + "dp");
        return statusBarHeight;
    }

    public float px2dp(float pxVal) {
        final float scale = getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_product_detail);
        mProductImg = findViewById(R.id.img_product);
        mProductNameTxt = findViewById(R.id.txt_product_name);
        mProductPriceTxt = findViewById(R.id.txt_product_price);
        mProductStockTxt = findViewById(R.id.txt_product_stock);
        mProductLimitTxt = findViewById(R.id.txt_product_limit);
        mCommentsRCV = findViewById(R.id.rcv_comments);
        initToolbar();
        initProductData();
        initComments();
    }

    private void initComments() {
        mCommentsRCV.setNestedScrollingEnabled(false);
        mCommentsRCV.setAdapter(new RcvCommentAdapter());
        mCommentsRCV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initProductData() {

    }

    private void initToolbar() {
        mToolbar.setPadding(0,getStatusBarHeight(),0,0);
        mToolbar.inflateMenu(R.menu.product_detail_toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
