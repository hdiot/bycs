package com.leonidas.zt.bycs.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
 * Created by 华强 on 2018/1/30.
 * Version: V1.0
 * Description: （拼购）购物车RecyclerView的适配器
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class CartGoodsListRvAdapter extends RecyclerView.Adapter {

    private static final String TAG = "CartGoodsListRvAdapter";
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<GoodsOfGroupPurchaseCartVO.DataBean> mGoodsOfGroupPurchaseCartList;
    private CheckBox cbAllSelect; //全选CheckBox
    private TextView tvTotalPrice; //总价

    public CartGoodsListRvAdapter(Context context, List<GoodsOfGroupPurchaseCartVO.DataBean> GoodsOfGroupPurchaseCartList, CheckBox cbAllSelect, TextView tvTotalPrice) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mGoodsOfGroupPurchaseCartList = GoodsOfGroupPurchaseCartList;
        this.cbAllSelect = cbAllSelect;
        this.tvTotalPrice = tvTotalPrice;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.leonidas_item_group_purchase_cart_goods, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.setData(mGoodsOfGroupPurchaseCartList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mGoodsOfGroupPurchaseCartList.size();
    }

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox cbIsSelect;
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
            cbIsSelect = itemView.findViewById(R.id.cb_is_select);
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
         * @param position
         */
        public void setData(GoodsOfGroupPurchaseCartVO.DataBean data, int position) {
            cbIsSelect.setChecked(data.isSelected());
            cbIsSelect.setTag(cbIsSelect.getId(), position);
            cbIsSelect.setOnClickListener(this);
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

        @Override
        public void onClick(View view) {
            if (view == cbIsSelect) {
                int position = (int) view.getTag(cbIsSelect.getId()); //获取Item位置
                //获取对应位置的商品
                GoodsOfGroupPurchaseCartVO.DataBean goods = mGoodsOfGroupPurchaseCartList.get(position);
                //设置状态
                //goods.setSelect(!goods.isSelect());
                goods.setSelected(cbIsSelect.isChecked());
                //校验当前是否“全选”
                if (isCheckAll()) {
                    cbAllSelect.setChecked(true);
                } else {
                    cbAllSelect.setChecked(false);
                }
                tvTotalPrice.setText(calculateTotalPrice() + ""); //计算总价
            }
        }
    }

    /**
     * 计算购物车商品总价
     */
    public double calculateTotalPrice() {
        double TotalPrice = 0.0; //默认为0.0元

        if (mGoodsOfGroupPurchaseCartList != null && mGoodsOfGroupPurchaseCartList.size() > 0) {//购物车中要有商品才计算
            //取出被选中的商品进行总价的计算
            for (int i = 0; i < mGoodsOfGroupPurchaseCartList.size(); i++) {
                GoodsOfGroupPurchaseCartVO.DataBean goods = mGoodsOfGroupPurchaseCartList.get(i);
                if (goods.isSelected()) {//商品是被选中的才进行计算
                    //单价 * 数量
                    TotalPrice += Double.valueOf(goods.getProductNprice()) * Double.valueOf(goods.getProductQuantity());
                }
            }
        }
        return TotalPrice;
    }

    /**
     * 校验是否全选
     *
     * @return 是否全选
     */
    public boolean isCheckAll() {
        boolean isAllSelect = true;
        if (mGoodsOfGroupPurchaseCartList != null && mGoodsOfGroupPurchaseCartList.size() > 0) {
            int count = 0; //记录被选中的item的个数
            for (int i = 0; i < mGoodsOfGroupPurchaseCartList.size(); i++) {
                GoodsOfGroupPurchaseCartVO.DataBean goods = mGoodsOfGroupPurchaseCartList.get(i);
                if (!goods.isSelected()) {//只要有一个商品没被选中则为非全选
                    isAllSelect = false;
                } else {
                    count++;
                }
            }
        } else {//没有数据的情况
            isAllSelect = false;
        }
        return isAllSelect;
    }

    /**
     * 修改所有Item的“选择”状态
     * @param isChecked 是否选中
     */
    public void modifyAllItemSelectStatus(boolean isChecked) {
        if (mGoodsOfGroupPurchaseCartList.size() > 0) {
            for (int i = 0; i < mGoodsOfGroupPurchaseCartList.size(); i++) {
                mGoodsOfGroupPurchaseCartList.get(i).setSelected(isChecked); //修改内存
                //notifyItemChanged(i); //通知Item内容变更
                notifyDataSetChanged();
            }
        }
    }

}
