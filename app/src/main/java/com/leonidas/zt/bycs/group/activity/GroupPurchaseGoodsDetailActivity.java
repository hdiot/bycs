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
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.group.adapter.GroupPurchaseGoodsRvAdapter;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsVO;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by 华强 on 2018/1/27.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseGoodsDetailActivity extends AppCompatActivity implements IOnAddDelListener {

    private static final String TAG = "GroupPurchaseGoodsDetai";
    private Context mContext = this;
    private GroupPurchaseGoodsVO data; //商品
    private Banner bannerGoods; //商品(默认)banner
    private TextView tvGoodsName; //商品名称
    private TextView tvGoodsStock; //商品数量
    private TextView tvGoodsLimit; //一份商品的分量
    private TextView tvGoodsPrice; //商品“拼购价”
    private TextView tvGoodsOrgPrice; //商品原价
    private AnimShopButton btAddCart; //加入购物车
    private RecyclerView rvExtraInfo; //额外的商品信息
    private boolean isModifyCount; //是否正在进行修改购物车中此商品的数量操作（网络请求）

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
        //添加监听
        initListener();
    }

    /**
     * 添加监听
     */
    private void initListener() {
        btAddCart.setOnAddDelListener(this);
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
                                GlideApp.with(mContext)
                                        .load(Api.BaseImg + PathBean.getPicturePath())
                                        .error(R.mipmap.mebee_image_bg)
                                        .transform(new RoundedCorners(20))
                                        .transition(new DrawableTransitionOptions().crossFade(200))
                                        .into(imageView);
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

    /**
     * 判断当前是否还在进行网络操作（防止用户连续点击）
     * @return 是否正在进行网络操作
     */
    public boolean isModifyCount() {
        return isModifyCount;
    }

    @Override
    public void onAddSuccess(int count) {
        if (isModifyCount()) { //正在进行网络操作
            btAddCart.setCount((count - 1));
            Toast.makeText(mContext, "正在添加，请稍后！", Toast.LENGTH_SHORT).show();
            return;
        }else if (count <= 0 ) {
            btAddCart.setCount(0);
            return;
        }
        AddGoodsToCart(data.getData().getGroupProduct() ,count);
    }

    @Override
    public void onAddFailed(int i, FailType failType) {
        Toast.makeText(mContext, "添加失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelSuccess(int count) {
        if (isModifyCount()) { //正在进行网络操作
            btAddCart.setCount((count + 1));
            Toast.makeText(mContext, "正在消减，请稍后！", Toast.LENGTH_SHORT).show();
            return;
        } else if (count <= 0 ) {
            btAddCart.setCount(0);
            return;
        }
        DelGoodsToCart(data.getData().getGroupProduct() ,count);
    }

    @Override
    public void onDelFaild(int i, FailType failType) {
        Toast.makeText(mContext, "减少失败！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 添加商品到购物车
     * @param groupProduct 商品对象
     * @param count 购买数量
     */
    private void AddGoodsToCart(GroupPurchaseGoodsVO.DataBean.GroupProductBean groupProduct, int count) {
        //int UserId = 1;
        long UserId = 1516332510603L;
        JSONObject mJo = new JSONObject();
        mJo.put(ApiParamKey.UserId, UserId);
        mJo.put(ApiParamKey.ProductId, groupProduct.getProductId());
        mJo.put(ApiParamKey.ProductQuantity, count);

        OkHttpUtils.postString().url(Api.ModifyPgGoodsToCart).content(mJo.toJSONString())
                .mediaType(MediaType.parse("application/json; charset=utf-8")).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, response);
                        JSONObject mJo = JSONObject.parseObject(response);

                        if (mJo == null) {//返回数据出错
                            Toast.makeText(mContext, "服务器错误！", Toast.LENGTH_SHORT).show();
                            btAddCart.setCount((btAddCart.getCount() - 1)); //添加失败则数量恢复
                            isModifyCount = false;
                            return;
                        }

                        Integer ResultCoud = mJo.getInteger(ApiParamKey.ResultCode);

                        if (ResultCoud == 1) {//添加成功
                            isModifyCount = false;
                            return;
                        }

                        //添加出错
                        btAddCart.setCount((btAddCart.getCount() - 1));
                        btAddCart.onCountAddSuccess();
                        switch (ResultCoud) {
                            case 403:
                                Toast.makeText(mContext, mJo.getString(ApiParamKey.Hint), Toast.LENGTH_SHORT).show();
                                break;
                            case 408:
                                Toast.makeText(mContext, mJo.getString(ApiParamKey.Hint), Toast.LENGTH_SHORT).show();
                                break;
                            case 410:
                                Toast.makeText(mContext, mJo.getString(ApiParamKey.Hint), Toast.LENGTH_SHORT).show();
                                break;
                            case 444:
                                Toast.makeText(mContext, mJo.getString(ApiParamKey.Hint), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(mContext, "未知错误！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        isModifyCount = false;
                    }
                });
    }

    /**
     * 减少购物车中商品的数量
     * @param groupProduct 商品对象
     * @param count 减少后的商品数量
     */
    private void DelGoodsToCart(GroupPurchaseGoodsVO.DataBean.GroupProductBean groupProduct, int count) {
        //int UserId = 1;
        long UserId = 1516332510603L;
        JSONObject mJo = new JSONObject();
        mJo.put(ApiParamKey.UserId, UserId);
        mJo.put(ApiParamKey.ProductId, groupProduct.getProductId());
        mJo.put(ApiParamKey.ProductQuantity, count);

        OkHttpUtils.postString().url(Api.ModifyPgGoodsToCart).content(mJo.toJSONString())
                .mediaType(MediaType.parse("application/json; charset=utf-8")).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, response);
                        JSONObject mJo = JSONObject.parseObject(response);

                        if (mJo == null) {//返回数据出错
                            Toast.makeText(mContext, "服务器错误！", Toast.LENGTH_SHORT).show();
                            btAddCart.setCount((btAddCart.getCount() - 1)); //添加失败则数量恢复
                            isModifyCount = false;
                            return;
                        }

                        Integer ResultCoud = mJo.getInteger(ApiParamKey.ResultCode);

                        if (ResultCoud == 1) {//添加成功
                            isModifyCount = false;
                            return;
                        }

                        //添加出错
                        btAddCart.setCount((btAddCart.getCount() - 1));
                        switch (ResultCoud) {
                            case 403:
                                Toast.makeText(mContext, mJo.getString(ApiParamKey.Hint), Toast.LENGTH_SHORT).show();
                                break;
                            case 408:
                                Toast.makeText(mContext, mJo.getString(ApiParamKey.Hint), Toast.LENGTH_SHORT).show();
                                break;
                            case 444:
                                Toast.makeText(mContext, mJo.getString(ApiParamKey.Hint), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(mContext, "未知错误！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        isModifyCount = false;
                    }
                });
    }

}
