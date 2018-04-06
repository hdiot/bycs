package com.leonidas.zt.bycs.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.basket.vo.GoodsOfGroupPurchaseCartVO;
import com.leonidas.zt.bycs.group.utils.Api;

import java.util.List;

/**
 * Created by 华强 on 2018/3/24.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class OrderContentRecyclerViewAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final List<GoodsOfGroupPurchaseCartVO.DataBean> datas;
    private final LayoutInflater mLayoutInflater;

    public OrderContentRecyclerViewAdapter(Context context, List<GoodsOfGroupPurchaseCartVO.DataBean> orderOfGoodsList) {
        this.mContext = context;
        this.datas = orderOfGoodsList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.leonidas_item_order_content, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class Holder extends RecyclerView.ViewHolder {
        private ImageView ivGoods;
        private TextView tvGoodsName;
        private TextView tvGoodsLimit;
        private TextView tvGoodsCopyCount;
        private TextView tvGroupPurchasePrice;
        private TextView tvGoodsOrgPrice;

        public Holder(View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            ivGoods = itemView.findViewById(R.id.iv_goods);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsLimit = itemView.findViewById(R.id.tv_goods_limit);
            tvGoodsCopyCount = itemView.findViewById(R.id.tv_goods_copy_count);
            tvGroupPurchasePrice = itemView.findViewById(R.id.tv_group_purchase_price);
            tvGoodsOrgPrice = itemView.findViewById(R.id.tv_goods_org_price);
        }

        /**
         * 设置数据
         * @param data
         */
        public void setData(GoodsOfGroupPurchaseCartVO.DataBean data) {
            GlideApp.with(mContext)
                    .load(Api.BaseImg + data.getPicturePath())
                    .error(R.mipmap.mebee_image_bg)
                    .transform(new RoundedCorners(20))
                    .transition(new DrawableTransitionOptions().crossFade(200))
                    .into(ivGoods);
            tvGoodsName.setText(data.getProductName());
            tvGoodsLimit.setText(data.getProductUnit());
            tvGoodsCopyCount.setText(data.getProductQuantity() + "");
            tvGroupPurchasePrice.setText("¥" + data.getProductNprice());
            tvGoodsOrgPrice.setText("¥" + data.getProductOprice() + "");
        }

    }
}
