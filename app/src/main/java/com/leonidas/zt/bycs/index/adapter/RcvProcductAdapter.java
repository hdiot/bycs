package com.leonidas.zt.bycs.index.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.activity.ProductDetialActivity;
import com.leonidas.zt.bycs.index.bean.Product;
import com.mcxtzhang.lib.AnimShopButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by mebee on 2018/1/24.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvProcductAdapter extends XRecyclerView.Adapter<RcvProcductAdapter.ViewHolder> {
    private Context mContext;
    private List<Product> mProducts;
    private String mShopId;

    public RcvProcductAdapter(List<Product> products,@NonNull String shopId){
        if (products == null) {
            mProducts = new LinkedList<>();
        }
        mProducts = products;
        mShopId = shopId;
    }

    public RcvProcductAdapter(List<Product> products){
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.productStockTxt.setText("剩余" + mProducts.get(position).getProductStock() + "份");
        holder.productPriceTxt.setText("￥" + mProducts.get(position).getProductPrice());
        holder.productNameTxt.setText( mProducts.get(position).getProductName());
        holder.productLimitTxt.setText("每份" + mProducts.get(position).getLimitNumber() + "克");
        holder.productPriceTxt.setText("￥" + mProducts.get(position).getProductPrice());
        GlideApp.with(mContext)
                .load(Constant.API.images + mProducts.get(position).getProductIcon())
                .error(R.mipmap.mebee_iamge_bg)
                .transform(new RoundedCorners(20))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(holder.productImg);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetialActivity.class);
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
        }
    }

    class NoneViewHolder extends XRecyclerView.ViewHolder{

        public NoneViewHolder(View itemView) {
            super(itemView);
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
}
