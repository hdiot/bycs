package com.leonidas.zt.bycs.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.vo.GoodsOfGroupPurchaseCartVO;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

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
    private List<GoodsOfGroupPurchaseCartVO.DataBean> GoodsOfGroupPurchaseCartList = new ArrayList<>();

    public CartGoodsListRvAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.leonidas_item_group_purchase_cart_goods, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.setData(GoodsOfGroupPurchaseCartList.get(position));//QueryGoodsListOfCart
    }

    @Override
    public int getItemCount() {
        return GoodsOfGroupPurchaseCartList.size();
    }

    private class Holder extends RecyclerView.ViewHolder {
        //private CheckBox cbIsSelect;
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
            //cbIsSelect = this.itemView.findViewById(R.id.cb_is_select);
            ivGoods = this.itemView.findViewById(R.id.iv_goods);
            tvGoodsName = this.itemView.findViewById(R.id.tv_goods_name);
            tvGoodsLimit = this.itemView.findViewById(R.id.tv_goods_limit);
            tvGoodsCopyCount = this.itemView.findViewById(R.id.tv_goods_copy_count);
            tvGroupPurchasePrice = this.itemView.findViewById(R.id.tv_group_purchase_price);
            tvGoodsOrgPrice = this.itemView.findViewById(R.id.tv_goods_org_price);
        }

        /**
         * 设置数据
         * @param data
         */
        public void setData(GoodsOfGroupPurchaseCartVO.DataBean data) {
            //cbIsSelect.setSelected(true);
            Glide.with(mContext).load(Api.BaseImg + data.getProductIcon()).into(ivGoods);
            tvGoodsName.setText(data.getProductName());
            tvGoodsLimit.setText("API有误，等API更改后再加");
            tvGoodsCopyCount.setText(data.getProductQuantity() + "");
            tvGroupPurchasePrice.setText("人民币" + data.getDiscountPrice());
            tvGoodsOrgPrice.setText("人民币" + data.getProductPrice() + "");
        }
    }

    /**
     * 加载拼购购物车内的商品数据
     * @param UserId 用户ID
     * @param Category 购物车分类
     */
    public void initGroupPurchaseCartGoods(int UserId,int Category) {
        OkHttpUtils.get()
                .url(Api.QueryUserCart)
                .addParams(ApiParamKey.UserId, UserId + "")
                .addParams(ApiParamKey.Category, Category + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                //解析数据
                GoodsOfGroupPurchaseCartVO data = JSON.parseObject(response, GoodsOfGroupPurchaseCartVO.class);

                if (data.getData() == null) {//数据出错
                    Toast.makeText(mContext, "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (data.getCode() != 1) {//数据获取失败
                    Toast.makeText(mContext, "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                    return;
                }

                GoodsOfGroupPurchaseCartList.addAll(data.getData());
                notifyDataSetChanged();

            }
        });

    }
}
