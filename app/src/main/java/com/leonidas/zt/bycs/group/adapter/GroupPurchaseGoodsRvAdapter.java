package com.leonidas.zt.bycs.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.group.activity.GroupPurchaseGoodsDetailActivity;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsListVO;
import com.mcxtzhang.lib.AnimShopButton;

import java.util.List;

/**
 * Created by 华强 on 2018/1/24.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseGoodsRvAdapter extends RecyclerView.Adapter {
    private static final String TAG = "GroupGoodsRvAdapter";
    public static final String ProductId = "productId";

    //分类数据的数据集
    private List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> DataList;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public GroupPurchaseGoodsRvAdapter(Context mContext, List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> DataList) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.DataList = DataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.leonidas_item_group_purchase_goods, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.setData(DataList.get(position));
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivGoods; //商品(默认)图片
        private TextView tvGoodsName; //商品名称
        private TextView tvGoodsStock; //商品数量
        private TextView tvGoodsLimit; //一份商品的分量
        private TextView tvGoodsPrice; //商品“拼购价”
        private TextView tvGoodsOrgPrice; //商品原价
        private AnimShopButton btAddCart; //加入购物车
        private GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean data;

        public Holder(View itemView) {
            super(itemView);
            ivGoods = itemView.findViewById(R.id.iv_goods);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsStock = itemView.findViewById(R.id.tv_goods_stock);
            tvGoodsLimit = itemView.findViewById(R.id.tv_goods_limit);
            tvGoodsPrice = itemView.findViewById(R.id.tv_goods_price);
            tvGoodsOrgPrice = itemView.findViewById(R.id.tv_goods_org_price);
            btAddCart = itemView.findViewById(R.id.bt_add_cart);
            tvGoodsOrgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        public void setData(GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean data) {
            this.data = data;
            Glide.with(mContext).load(Api.BaseImg + data.getProductPictures().get(0).getPicturePath()).into(ivGoods);
            tvGoodsName.setText(data.getProductName());
            tvGoodsStock.setText(data.getProductStock() + "");
            tvGoodsLimit.setText(data.getProductUnit() + "");
            tvGoodsPrice.setText(data.getProductNprice() + "");
            tvGoodsOrgPrice.setText(data.getProductOprice() + "");
            itemView.setTag(data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean data
                    = (GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean) view.getTag();
            Intent intent = new Intent(mContext, GroupPurchaseGoodsDetailActivity.class);
            intent.putExtra(ProductId, data.getProductId());
            mContext.startActivity(intent);
        }
    }

}
