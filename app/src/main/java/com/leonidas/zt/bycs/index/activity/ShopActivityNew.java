package com.leonidas.zt.bycs.index.activity;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaeger.library.StatusBarUtil;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shop;
import com.leonidas.zt.bycs.index.fragment.CommentsFragment;
import com.leonidas.zt.bycs.index.fragment.ProductsFragment;
import com.leonidas.zt.bycs.index.fragment.ShopInfoFragment;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;
import com.mebee.coordinatorbehavior.behavior.AppBarLayoutOverScrollViewBehavior;
import com.mebee.coordinatorbehavior.fragment.MyFragmentPagerAdapter;
import com.mebee.coordinatorbehavior.fragment.dummy.TabEntity;
import com.mebee.coordinatorbehavior.widget.CircleImageView;
import com.mebee.coordinatorbehavior.widget.NoScrollViewPager;
import com.mebee.coordinatorbehavior.widget.RoundProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Request;
import okhttp3.Response;

public class ShopActivityNew extends AppCompatActivity {

    private String mShopId;
    private ImageView mZoomIv;
    private Toolbar mToolBar;
    private ViewGroup titleContainer;
    private AppBarLayout mAppBarLayout;
    private ViewGroup titleCenterLayout;
    private RoundProgressBar progressBar;
    private ImageView mMsgIv;
    private CircleImageView mAvater;
    private CircleImageView mTitleAvater;
    private CommonTabLayout mTablayout;
    private NoScrollViewPager mViewPager;
    private TextView mLimit;
    private TextView mPostFee;
    private TextView mDescription;
    private TextView mScore;
    private TextView mSole;
    private TextView mShopName;
    private TextView mTitleName;
    private TextView mAnnounce;


    private MaterialRatingBar mStar;

    private Shop mShop;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragments;
    private int lastState = 1;
    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_shop_new);
        getShopId();
        findId();
        initListener();
        initTab();
        initStatus();
        setData(mShop);
        //requsetData();
    }


    /**
     * 初始化id
     */
    private void findId() {
        mZoomIv = (ImageView) findViewById(com.mebee.coordinatorbehavior.R.id.uc_zoomiv);
        mToolBar = (Toolbar) findViewById(com.mebee.coordinatorbehavior.R.id.toolbar);
        titleContainer = (ViewGroup) findViewById(com.mebee.coordinatorbehavior.R.id.title_layout);
        mAppBarLayout = (AppBarLayout) findViewById(com.mebee.coordinatorbehavior.R.id.appbar_layout);
        titleCenterLayout = (ViewGroup) findViewById(com.mebee.coordinatorbehavior.R.id.title_center_layout);
        progressBar = (RoundProgressBar) findViewById(com.mebee.coordinatorbehavior.R.id.uc_progressbar);
        mMsgIv = (ImageView) findViewById(com.mebee.coordinatorbehavior.R.id.uc_msg_iv);
        mAvater = (CircleImageView) findViewById(com.mebee.coordinatorbehavior.R.id.uc_avater);
        mTablayout = (CommonTabLayout) findViewById(com.mebee.coordinatorbehavior.R.id.uc_tablayout);
        mViewPager = (NoScrollViewPager) findViewById(com.mebee.coordinatorbehavior.R.id.uc_viewpager);
        mTitleAvater = (CircleImageView) findViewById(R.id.title_uc_avater);
        mTitleName = (TextView) findViewById(R.id.title_uc_title);



        mLimit = findViewById(R.id.frag_uc_limit_tv);
        mPostFee = findViewById(R.id.frag_uc_post_fee_tv);
        mDescription = findViewById(R.id.frag_uc_interest_tv);
        mScore = findViewById(R.id.flag_uc_score_tv);
        mShopName = findViewById(R.id.frag_uc_nickname_tv);
        mStar = findViewById(R.id.flag_uc_star);
        mAnnounce = findViewById(R.id.flag_uc_announce_tv);
        mSole =  (TextView) findViewById(R.id.flag_uc_sole_tv);
    }


    private void setData(Shop shop) {
        if (shop == null)
            return;
        Log.e("head", "setData: "+ shop.getShopPictures().get(0).getPicturePath() );
        mZoomIv.setBackgroundColor(R.drawable.mebee_transfrom_bg);
        if (shop.getShopPictures().size()>1) {
            Glide.with(this)
                    .load(Constant.API.images + shop.getShopPictures().get(1).getPicturePath())
                    .into(mZoomIv);
        } else {
            mZoomIv.setBackgroundColor(R.drawable.mebee_transfrom_bg);
        }
        Glide.with(this)
                .load(Constant.API.images + shop.getShopPictures().get(0).getPicturePath())
                .into(mAvater);
        GlideApp.with(this)
                .load(Constant.API.images + shop.getShopPictures().get(0).getPicturePath())
                .into(mTitleAvater);
        mLimit.setText("￥" + shop.getLimitPrice() + "起送");
        mPostFee.setText("配送费￥" + shop.getSendPrice());
        mDescription.setText(shop.getShopDesc());
        mScore.setText(shop.getShopGrade() + "");
        mShopName.setText(shop.getShopName());
        mStar.setRating(shop.getShopGrade());
        mAnnounce.setText(shop.getShopNote());
        mTitleName.setText(shop.getShopName());
        mSole.setText(shop.getShopSale() + "单");
    }

    private void requsetData() {
        Map<String, String> params = new WeakHashMap<>(1);
        params.put("shopId", mShopId);
        mOkHttpHelper.doGet(Constant.API.getShop, params, new BaseCallback<ResMessage<Data<Shop>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<Shop>> dataResMessage) {
                mShop = dataResMessage.getData().getShop();
                setData(mShop);
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

    /**
     * 初始化tab
     */
    private void initTab() {
        fragments = getFragments();
        MyFragmentPagerAdapter myFragmentPagerAdapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, getNames());

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
                if (titleCenterLayout != null && mAvater != null && mMsgIv != null) {
                    titleCenterLayout.setAlpha(percent);
                    StatusBarUtil.setTranslucentForImageView(ShopActivityNew.this, (int) (255f * percent), null);
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
                    } else if (progress > 0 ) {
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
            StatusBarUtil.setTransparentForImageView(ShopActivityNew.this, null);
            //这里是重设我们的title布局的topMargin，StatusBarUtil提供了重设的方法，但是我们这里有两个布局
            //TODO 关于为什么不把Toolbar和@layout/layout_uc_head_title放到一起，是因为需要Toolbar来占位，防止AppBarLayout折叠时将title顶出视野范围
            int statusBarHeight = getStatusBarHeight(ShopActivityNew.this);
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

        mMsgIv.setAlpha(alpha);

        switch (state) {
            case 1://完全展开 显示白色
                mMsgIv.setImageResource(com.mebee.coordinatorbehavior.R.mipmap.icon_msg);
                mViewPager.setNoScroll(false);
                break;
            case 2://完全关闭 显示黑色
                mMsgIv.setImageResource(com.mebee.coordinatorbehavior.R.mipmap.icon_msg_black);

                mViewPager.setNoScroll(false);
                break;
            case 0://介于两种临界值之间 显示黑色
                if (lastState != 0) {
                    mMsgIv.setImageResource(com.mebee.coordinatorbehavior.R.mipmap.icon_msg_black);
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
        String[] mNames = new String[]{"商品", "评论", "商家信息"};
        for (String str : mNames) {
            mTabEntities.add(new TabEntity(str));
        }

        return mNames;
    }

    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ProductsFragment(mShopId));
        fragments.add(new CommentsFragment(mShopId));
        fragments.add(new ShopInfoFragment(mShopId));
        return fragments;
    }

    public void getShopId() {

        mShop = (Shop) getIntent().getSerializableExtra("shop");
        mShopId = mShop.getShopId();
        if (mShop == null) {
            throw new RuntimeException("shopId cannot be null");
        }

    }
}
