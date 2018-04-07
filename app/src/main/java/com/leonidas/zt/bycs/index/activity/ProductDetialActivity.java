package com.leonidas.zt.bycs.index.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvCommentAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.Product;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.ShopComments;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;
import com.mcxtzhang.lib.AnimShopButton;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Request;
import okhttp3.Response;

public class ProductDetialActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetialActivity";
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollToolbarLay;
    private ImageView mProductImg;
    private TextView mProductNameTxt;
    private TextView mProductPriceTxt;
    private TextView mProductStockTxt;
    private TextView mProductLimitTxt;
    private AnimShopButton mAnimShopButton;
    private RecyclerView mCommentsRCV;
    private Product mProduct;
    private boolean mIsNeedPadding = false;
    private RcvCommentAdapter mCommentAdapter;
    private String mShopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_product_detial);
        getInfoFromStart();
        initView();
    }

    private void getInfoFromStart() {
        mProduct = (Product) getIntent().getSerializableExtra("productInfo");
        mShopId = getIntent().getStringExtra("shopId");
        if (mProduct == null) {
            throw new RuntimeException("Product can not be null");
        }
    }

    private void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            mIsNeedPadding = true;
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
        mAnimShopButton = findViewById(R.id.shop_button);
        mCollToolbarLay = findViewById(R.id.coll_toolbar_layout);
        initToolbar();
        initProductData();
        initComments();
    }

    private void initComments() {
        mCommentAdapter = new RcvCommentAdapter(null);
        mCommentsRCV.setNestedScrollingEnabled(false);
        mCommentsRCV.setAdapter(mCommentAdapter);
        mCommentsRCV.setLayoutManager(new LinearLayoutManager(this));
        getCommentsData();
    }

    private void getCommentsData(){
        Map<String, String> params = new WeakHashMap<>(1);
        params.put("shopId",mShopId);
        OkHttpHelper.getInstance()
                .doGet(Constant.API.getComment, params, new BaseCallback<ResMessage<Data<ShopComments>>>() {

                    @Override
                    public void OnSuccess(Response response, ResMessage<Data<ShopComments>> dataResMessage) {
                        mCommentAdapter.loadMore(dataResMessage.getData().getShopComments().getList());
                    }

                    @Override
                    public void onError(Response response, int errCode, Exception e) {

                    }

                    @Override
                    public void onRequestBefore(Request request) {

                    }

                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onBzError(Response response, int code, String hint, String data) {

                    }
                });
    }

    private void initProductData() {
        if (mProduct == null)
            return;
        Glide.with(this)
                .load(Constant.API.images + mProduct.getProductIcon())
                .into(mProductImg);
        mProductNameTxt.setText(mProduct.getProductName());
        mProductPriceTxt.setText(String.valueOf(mProduct.getProductPrice()));
        mProductStockTxt.setText(String.valueOf(mProduct.getProductStock()));
        mProductLimitTxt.setText(String.valueOf(mProduct.getLimitNumber()));

    }

    private void initToolbar() {
        /*if (mIsNeedPadding) {
            //mToolbar.setPadding(0,getStatusBarHeight(),0,0);
        }*/
        /*setSupportActionBar(mToolbar);//设置toolbar
        mCollToolbarLay.setTitleEnabled(true);
        mCollToolbarLay.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mCollToolbarLay.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置
        mCollToolbarLay.setTitle("标题");//设置标题的名字
        mCollToolbarLay.setExpandedTitleColor(Color.WHITE);//设置展开后标题的颜色
        mCollToolbarLay.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色*/
        mToolbar.inflateMenu(R.menu.toolbar_basket_only);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
