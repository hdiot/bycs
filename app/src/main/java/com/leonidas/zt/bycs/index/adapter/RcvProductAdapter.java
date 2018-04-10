package com.leonidas.zt.bycs.index.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.activity.ProductDetailActivity;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.Product;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;
import com.leonidas.zt.bycs.user.User;
import com.leonidas.zt.bycs.user.activity.LoginActivity;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by mebee on 2018/1/24.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvProductAdapter extends XRecyclerView.Adapter<RcvProductAdapter.ViewHolder> {
    private Context mContext;
    private List<Product> mProducts;
    private String mShopId;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    public RcvProductAdapter(List<Product> products, @NonNull String shopId){
        if (products == null) {
            mProducts = new LinkedList<>();
        }
        mProducts = products;
        mShopId = shopId;
    }

    public RcvProductAdapter(List<Product> products){
        if (products == null) {
            mProducts = new LinkedList<>();
        }
        mProducts = products;
        mShopId = "0";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.mebee_rcv_product_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Product product = mProducts.get(position);
        holder.productStockTxt.setText("剩余" + product.getProductStock() + "份");
        holder.productPriceTxt.setText("￥" + product.getProductPrice());
        holder.productNameTxt.setText( product.getProductName());
        holder.productLimitTxt.setText("每份" + product.getLimitNumber() + "克");
        holder.productPriceTxt.setText("￥" + product.getProductPrice());
        holder.animShopButton.setMaxCount((int) product.getProductStock());
        holder.animShopButton.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                doAdd2Cart(String.valueOf(product.getProductId()), i);
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                doAdd2Cart(String.valueOf(product.getProductId()), i);
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });
        GlideApp.with(mContext)
                .load(Constant.API.images + mProducts.get(position).getProductIcon())
                .error(R.mipmap.mebee_image_bg)
                .transform(new RoundedCorners(20))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(holder.productImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("shopId",mShopId);
                intent.putExtra("productInfo", (Serializable) mProducts.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }

    class ViewHolder extends XRecyclerView.ViewHolder{

        ImageView productImg;
        TextView productNameTxt;
        TextView productStockTxt;
        TextView productLimitTxt;
        TextView productPriceTxt;
        TextView productOrgPriceTxt;
        AnimShopButton animShopButton;

        public ViewHolder(View itemView) {
            super(itemView);
            productImg = (ImageView) itemView.findViewById(R.id.img_product);
            productLimitTxt = (TextView) itemView.findViewById(R.id.txt_product_limit);
            productNameTxt = (TextView) itemView.findViewById(R.id.txt_product_name);
            productPriceTxt = (TextView) itemView.findViewById(R.id.txt_product_price);
            productOrgPriceTxt = (TextView) itemView.findViewById(R.id.txt_product_org_price);
            productStockTxt = (TextView) itemView.findViewById(R.id.txt_product_stock);
            animShopButton = (AnimShopButton) itemView.findViewById(R.id.shop_button);
        }
    }

    public void refresh(List<Product> products) {
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
    public void loadMore(List<Product> products) {
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

    private void doAdd2Cart(String pId, int quantity){
        HashMap<String, Object> params = new HashMap<>();
        String mUserId = null;

        try {
            mUserId = User.getInstance().getUserInfo().getUserId();
        } catch (NullPointerException e){
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }

        params.put("userId", mUserId);
        params.put("productId", pId);
        params.put("itemCategory", 1);
        params.put("productQuantity", quantity);
        Log.e("params", "doAdd2Cart: " + JSON.toJSON(params).toString());
        okHttpHelper.doPost(Constant.API.cartItem, JSON.toJSON(params).toString(), new BaseCallback<ResMessage<Data<String>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<String>> dataResMessage) {
                Log.e("A2C", "OnSuccess: " + dataResMessage.getData().getShop());
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
                switch (code) {
                    case 403:
                        break;
                    case 406:
                        break;
                    case 410:
                        break;
                    case 444:
                        break;
                }
            }
        });
    }
}
