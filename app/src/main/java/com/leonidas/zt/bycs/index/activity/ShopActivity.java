package com.leonidas.zt.bycs.index.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
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

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvProcductAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.Products;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shop;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class ShopActivity extends AppCompatActivity {
    private static final String TAG = "ShopActivity";

    private Toolbar mToolbar;
    private ImageView mShopImg;
    private TextView mLimitTxt;
    private TextView mPostFeeTxt;
    private TextView mDescriptionNoteTxt;
    private TextView mScoreTxt;
    private TabLayout mTab;
    private XRecyclerView mProductsXR;
    private String mShopId;
    private OkHttpHelper mHttpHelper;
    private RcvProcductAdapter mRcvProductAdapter = new RcvProcductAdapter(null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
        initView();
        getShopId();
        initData();
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
        initProductsXR();
    }

    private void initProductsXR() {
        mProductsXR.setAdapter(mRcvProductAdapter);
        mProductsXR.setLayoutManager(new LinearLayoutManager(this));
        mProductsXR.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
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
        mProductsXR = findViewById(R.id.xrv_products);
    }

    private void initData(){
        mHttpHelper = OkHttpHelper.getInstance();
        getShopData();
        getShopProducts("");
    }

    private void getShopData(){
        String url = "/?shopId=" + mShopId;
        mHttpHelper.doGet(Constant.API.getShop+url, new BaseCallback<ResMessage<Data<Shop>>>() {
            @Override
            public void OnSuccess(Response response, ResMessage<Data<Shop>> dataResMessage) {
                initShopDatas(dataResMessage.getData().getShop().getShopPictures().get(0).getPicturePath(),
                        dataResMessage.getData().getShop().getLimitPrice(),
                        dataResMessage.getData().getShop().getSendPrice(),
                        dataResMessage.getData().getShop().getShopDesc(),
                        dataResMessage.getData().getShop().getShopGrade());
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

    private void initShopDatas(String img,
                               double limit,
                               double post,
                               String description,
                               double score) {
        Glide.with(this)
                .load(Constant.API.images + img)
                .into(mShopImg);
        mDescriptionNoteTxt.setText(description);
        mLimitTxt.setText(String.valueOf(limit));
        mScoreTxt.setText(String.valueOf(score));
        mPostFeeTxt.setText(String.valueOf(post));
    }

    private void getShopProducts(@NonNull String type){
        Map<String,String> map = new LinkedHashMap<String,String>();
        map.put("shopId",mShopId);
        if (!type.equals(""))
            map.put("categoryId",type);
        mHttpHelper.doGet(Constant.API.getProducts, map, new BaseCallback<ResMessage<Data<Products>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<Products>> dataResMessage) {
                mRcvProductAdapter.loadMore(dataResMessage.getData().getProducts().getList());
                Log.d(TAG, "OnSuccess: " + dataResMessage.getData().getProducts().getList().get(0).getProductName());
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



    private void initToolbar() {
        mToolbar.setPadding(0,getStatusBarHeight(),0,0);
        mToolbar.inflateMenu(R.menu.product_detail_toolbar);
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

    public void getShopId() {
        mShopId = getIntent().getStringExtra("shopId");

        if (mShopId == null){
            throw new RuntimeException("shopId cannot be null");
        }

        Log.d(TAG, "getShopId: " + mShopId);
    }
}
