package com.leonidas.zt.bycs.group.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.group.adapter.GroupPurchaseGoodsRvAdapter;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsVO;
import com.mcxtzhang.lib.AnimShopButton;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 华强 on 2018/1/27.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseGoodsDetailActivity extends AppCompatActivity {
    private static final String TAG = "GroupPurchaseGoodsDetai";

    private GroupPurchaseGoodsVO data; //商品

    private Banner bannerGoods; //商品(默认)banner
    private TextView tvGoodsName; //商品名称
    private TextView tvGoodsStock; //商品数量
    private TextView tvGoodsLimit; //一份商品的分量
    private TextView tvGoodsPrice; //商品“拼购价”
    private TextView tvGoodsOrgPrice; //商品原价
    private AnimShopButton btAddCart; //加入购物车
    private RecyclerView rvExtraInfo; //额外的商品信息
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leonidas_activity_group_purchase_goods_detail);
        initView();
        Intent intent = getIntent();
        long ProductId = intent.getLongExtra(GroupPurchaseGoodsRvAdapter.ProductId, -1);
        Log.e("productId", ProductId + "");
        if (ProductId == -1) {
            Toast.makeText(getBaseContext(), "错误！", Toast.LENGTH_SHORT).show();
        } else {
            initData(ProductId);
        }

    }

    /**
     * 初始化视图
     */
    private void initView() {
        bannerGoods = findViewById(R.id.banner_goods);
        tvGoodsName = findViewById(R.id.tv_goods_name);
        tvGoodsStock = findViewById(R.id.tv_goods_stock);
        tvGoodsLimit = findViewById(R.id.tv_goods_limit);
        tvGoodsPrice = findViewById(R.id.tv_goods_price);
        tvGoodsOrgPrice = findViewById(R.id.tv_goods_org_price);
        btAddCart = findViewById(R.id.bt_add_cart);
        rvExtraInfo = findViewById(R.id.rv_extra_info);
        tvGoodsOrgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //给原价添加删除线
    }

    /**
     * 初始化数据
     * @param ProductId 商品Id
     */
    private void initData(long ProductId) {
        OkHttpUtils.get()
                .url(Api.QueryGroupPurchaseGoodsDetail)
                .addParams(ApiParamKey.ProductId, ProductId + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getBaseContext(), "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, response);
                        data = JSON.parseObject(response, GroupPurchaseGoodsVO.class);
                        if (data.getData() == null || data.getCode() != 1) {//数据出错
                            Toast.makeText(getBaseContext(), "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //设置Banner
                        bannerGoods.setBannerStyle(BannerConfig.CIRCLE_INDICATOR); //设置Banner样式
                        bannerGoods.setBannerAnimation(Transformer.BackgroundToForeground); //设置Banner转场动画
                        GroupPurchaseGoodsVO.DataBean.GroupProductBean Goods = data.getData().getGroupProduct();
                        bannerGoods.setImages(Goods.getProductPictures()).setImageLoader(new ImageLoader() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                GroupPurchaseGoodsVO.DataBean.GroupProductBean.ProductPicturesBean PathBean
                                        = (GroupPurchaseGoodsVO.DataBean.GroupProductBean.ProductPicturesBean)path;
                                Glide.with(getBaseContext()).load(Api.BaseImg + PathBean.getPicturePath()).into(imageView);
                            }
                        }).start();
                        //商品名称
                        tvGoodsName.setText(Goods.getProductName());
                        //商品数量
                        tvGoodsStock.setText("剩余" + Goods.getProductStock() + "份");
                        //一份商品的分量
                        tvGoodsLimit.setText("每份" + Goods.getProductUnit() + "克");
                        //商品“拼购价”
                        tvGoodsPrice.setText("¥"+Goods.getProductNprice());
                        //商品原价
                        tvGoodsOrgPrice.setText("¥" + Goods.getProductOprice());
                        //加入购物车
                        //btAddCart;
                        //额外的商品信息
                        //rvExtraInfo;
                    }
                });
    }


}
