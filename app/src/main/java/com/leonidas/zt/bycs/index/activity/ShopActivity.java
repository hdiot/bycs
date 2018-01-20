package com.leonidas.zt.bycs.index.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;

public class ShopActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mShopImg;
    private TextView mLimitTxt;
    private TextView mPostFeeTxt;
    private TextView mDescriptionNoteTxt;
    private TextView mScoreTxt;
    private TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
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

    private void initView() {
        findView();
        initToolbar();
        initTab();
    }

    private void initTab() {
        mTab.addTab(mTab.newTab().setText("全部"));
        mTab.addTab(mTab.newTab().setText("肉类"));
        mTab.addTab(mTab.newTab().setText("海鲜"));
        mTab.addTab(mTab.newTab().setText("包点"));
        mTab.addTab(mTab.newTab().setText("干活"));
        mTab.addTab(mTab.newTab().setText("牛奶"));
        mTab.addTab(mTab.newTab().setText("好评优先"));
        mTab.addTab(mTab.newTab().setText("销量高优先"));
    }

    private void findView(){
        setContentView(R.layout.mebee_activity_shop);
        mToolbar = findViewById(R.id.toolbar_shop);
        mShopImg = findViewById(R.id.img_shop);
        mLimitTxt = findViewById(R.id.txt_limit);
        mPostFeeTxt = findViewById(R.id.txt_post_fee);
        mDescriptionNoteTxt = findViewById(R.id.txt_description_note);
        mScoreTxt = findViewById(R.id.txt_score);
        mTab = findViewById(R.id.tab_shop);
    }

    private void initShopDatas(String img,
                               String limit,
                               String post,
                               String description,
                               String score) {
        mDescriptionNoteTxt.setText(description);
        mLimitTxt.setText(limit);
        mScoreTxt.setText(score);
        mPostFeeTxt.setText(post);
    }

    private void initToolbar() {
        mToolbar.setPadding(0,getStatusBarHeight(),0,0);
        mToolbar.inflateMenu(R.menu.product_detail_toolbar);
    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
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

}
