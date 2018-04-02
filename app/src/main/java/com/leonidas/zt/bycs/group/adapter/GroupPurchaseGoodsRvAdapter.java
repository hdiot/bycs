package com.leonidas.zt.bycs.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.group.activity.GroupPurchaseGoodsDetailActivity;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsListVO;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by 华强 on 2018/1/24.
 * Version: V1.0
 * Description: 拼购商品列表适配器
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

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, IOnAddDelListener{
        private ImageView ivGoods; //商品(默认)图片
        private TextView tvGoodsName; //商品名称
        private TextView tvGoodsStock; //商品数量
        private TextView tvGoodsLimit; //一份商品的分量
        private TextView tvGoodsPrice; //商品“拼购价”
        private TextView tvGoodsOrgPrice; //商品原价
        private AnimShopButton btAddCart; //加入购物车
        private GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean data;
        private boolean isModifyCount; //是否正在进行修改购物车中此商品的数量操作（网络请求）

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
            btAddCart.setOnAddDelListener(this);
            btAddCart.setMaxCount(99); //设置最大购买份数 --- 当前为最多购买99份
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

        /**
         * 判断当前是否还在进行网络操作（防止用户连续点击）
         * @return 是否正在进行网络操作
         */
        public boolean isModifyCount() {
            return isModifyCount;
        }

        @Override
        public void onClick(View view) {
            //进入商品详情页面
            GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean data
                    = (GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean) view.getTag();
            Intent intent = new Intent(mContext, GroupPurchaseGoodsDetailActivity.class);
            intent.putExtra(ProductId, data.getProductId());
            mContext.startActivity(intent);
        }

        /**
         * 添加商品数量成功
         * @param count 当前商品总数
         */
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
                btAddCart.setCount((count + 1));
                Toast.makeText(mContext, "正在消减，请稍后！", Toast.LENGTH_SHORT).show();
                return;
            } else if (count <= 0 ) {
                btAddCart.setCount(0);
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
        private void DelGoodsToCart(GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean groupProduct, int count) {
            //int UserId = 1;
            long UserId = 1516332510603L;
            JSONObject mJo = new JSONObject();
            mJo.put(ApiParamKey.UserId, UserId);
            mJo.put(ApiParamKey.ProductId, groupProduct);
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

}
