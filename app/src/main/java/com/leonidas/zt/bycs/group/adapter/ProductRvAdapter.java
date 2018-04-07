package com.leonidas.zt.bycs.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.group.activity.GroupPurchaseGoodsDetailActivity;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsListVO;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by 华强 on 2018/4/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class ProductRvAdapter extends XRecyclerView.Adapter{

    private Context mContext;
    private List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> mProducts;
    private String mShopId;
    private boolean isModifyCount; //是否正在进行修改购物车中此商品的数量操作（网络请求）
    private String TAG = "ProductRvAdapter";

    /**
     * 判断当前是否还在进行网络操作（防止用户连续点击）
     * @return 是否正在进行网络操作
     */
    public boolean isModifyCount() {
        return isModifyCount;
    }

    public ProductRvAdapter(List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> products, @NonNull String shopId){
        if (products == null) {
            mProducts = new LinkedList<>();
        }
        mProducts = products;
        mShopId = shopId;
    }

    public ProductRvAdapter(List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> products){
        if (products == null) {
            mProducts = new LinkedList<>();
        }
        mProducts = products;
        mShopId = "0";
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.mebee_rcv_product_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.data = mProducts.get(position);
        mHolder.productStockTxt.setText("剩余" + mProducts.get(position).getProductStock() + "份");
        mHolder.productPriceTxt.setText("￥" + mProducts.get(position).getProductNprice());
        mHolder.productNameTxt.setText( mProducts.get(position).getProductName());
        mHolder.productLimitTxt.setText("每份" + mProducts.get(position).getProductUnit() + "克");
        mHolder.productPriceTxt.setText("￥" + mProducts.get(position).getProductNprice());
        GlideApp.with(mContext)
                .load(Constant.API.images + mProducts.get(position).getProductPictures().get(0).getPicturePath())
                .error(R.mipmap.mebee_image_bg)
                .transform(new RoundedCorners(20))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(mHolder.productImg);
        mHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean temProduct = mProducts.get(position);
                Intent intent = new Intent(mContext, GroupPurchaseGoodsDetailActivity.class);
                intent.putExtra(GroupPurchaseGoodsRvAdapter.ProductId, temProduct.getProductId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }

    class ViewHolder extends XRecyclerView.ViewHolder implements IOnAddDelListener {
        GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean data;
        ImageView productImg;
        TextView productNameTxt;
        TextView productStockTxt;
        TextView productLimitTxt;
        TextView productPriceTxt;
        TextView productOrgPriceTxt;
        AnimShopButton animShopButton;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.img_product);
            productLimitTxt = itemView.findViewById(R.id.txt_product_limit);
            productNameTxt = itemView.findViewById(R.id.txt_product_name);
            productPriceTxt = itemView.findViewById(R.id.txt_product_price);
            productOrgPriceTxt = itemView.findViewById(R.id.txt_product_org_price);
            productStockTxt = itemView.findViewById(R.id.txt_product_stock);
            animShopButton = itemView.findViewById(R.id.shop_button);
            item = itemView;
            animShopButton.setOnAddDelListener(this);
        }

        /**
         * 添加商品数量成功
         * @param count 当前商品总数
         */
        @Override
        public void onAddSuccess(int count) {
            if (isModifyCount()) { //正在进行网络操作
                animShopButton.setCount((count - 1));
                Toast.makeText(mContext, "正在添加，请稍后！", Toast.LENGTH_SHORT).show();
                return;
            }else if (count <= 0 ) {
                animShopButton.setCount(0);
                return;
            }
            Log.e("productedid", data.getProductId() + "");
            AddGoodsToCart(data ,count);
        }

        /**
         * 添加商品数量失败
         * @param count 当前商品总数
         * @param failType 失败类型
         */
        @Override
        public void onAddFailed(int count, FailType failType) {
            Log.e("ft", failType.name());
            Toast.makeText(mContext, "已达到限制购买数量！", Toast.LENGTH_SHORT).show();
        }

        /**
         * 删除商品数量成功
         * @param count 当前商品总数
         */
        @Override
        public void onDelSuccess(int count) {
            if (isModifyCount()) { //正在进行网络操作
                animShopButton.setCount((count + 1));
                Toast.makeText(mContext, "正在消减，请稍后！", Toast.LENGTH_SHORT).show();
                return;
            } else if (count <= 0 ) {
                animShopButton.setCount(0);
                return;
            }
            Log.e("productedid", data.getProductId() + "");
            DelGoodsToCart(data ,count);
        }

        @Override
        public void onDelFaild(int i, FailType failType) {
            Toast.makeText(mContext, "低于最低购买数量！", Toast.LENGTH_SHORT).show();
        }

        /**
         * 添加商品到购物车
         * @param groupProduct 商品对象
         * @param count 购买数量
         */
        private void AddGoodsToCart(GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean groupProduct, int count) {
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
                                animShopButton.setCount((animShopButton.getCount() - 1)); //添加失败则数量恢复
                                isModifyCount = false;
                                return;
                            }

                            Integer ResultCoud = mJo.getInteger(ApiParamKey.ResultCode);

                            if (ResultCoud == 1) {//添加成功
                                isModifyCount = false;
                                return;
                            }

                            //添加出错
                            animShopButton.setCount((animShopButton.getCount() - 1));
                            animShopButton.onCountAddSuccess();
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
        private void DelGoodsToCart(GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean groupProduct, int count) {
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
                                animShopButton.setCount((animShopButton.getCount() - 1)); //添加失败则数量恢复
                                isModifyCount = false;
                                return;
                            }

                            Integer ResultCoud = mJo.getInteger(ApiParamKey.ResultCode);

                            if (ResultCoud == 1) {//添加成功
                                isModifyCount = false;
                                return;
                            }

                            //添加出错
                            animShopButton.setCount((animShopButton.getCount() - 1));
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

    public void refresh(List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> products) {
        if (products != null) {
            forbidNull();
            mProducts.clear();
            loadMore(products);
        }
    }

    /**
     * 上拉加载时调用，add 加载数据
     * @param products
     */
    public void loadMore(List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> products) {
        if (products != null) {
            forbidNull();
            int position = mProducts.size();
            mProducts.addAll(position, products);
            notifyDataSetChanged();
        }
    }

    private void forbidNull() {
        if (mProducts == null) {
            mProducts = new ArrayList<>();
        }
    }

}
