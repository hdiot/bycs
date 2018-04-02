package com.leonidas.zt.bycs.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.group.activity.GroupPurchaseGoodsDetailActivity;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsListVO;
import com.mcxtzhang.lib.AnimShopButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
            productImg = (ImageView) itemView.findViewById(R.id.img_product);
            productLimitTxt = (TextView) itemView.findViewById(R.id.txt_product_limit);
            productNameTxt = (TextView) itemView.findViewById(R.id.txt_product_name);
            productPriceTxt = (TextView) itemView.findViewById(R.id.txt_product_price);
            productOrgPriceTxt = (TextView) itemView.findViewById(R.id.txt_product_org_price);
            productStockTxt = (TextView) itemView.findViewById(R.id.txt_product_stock);
            animShopButton = (AnimShopButton) itemView.findViewById(R.id.shop_button);
            item = itemView;
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
