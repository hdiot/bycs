package com.leonidas.zt.bycs.index.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.activity.ShopActivity;
import com.leonidas.zt.bycs.index.activity.ShopsActivity;
import com.leonidas.zt.bycs.index.bean.Shop;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by mebee on 2018/1/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvShopAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private static final String TAG = "RcvShopAdapter";
    private Context mContext;
    private List<Shop> mShops;
    private OnItemClickListener mMnItemClickListener;
    private boolean mIsDisplayMoreTip = true;

    private enum ItemType {
        FIRST,
        OTHERS
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setItemOnclickListener(OnItemClickListener listener) {
        this.mMnItemClickListener = listener;
    }

    public RcvShopAdapter(List<Shop> shops) {
        this.mShops = shops;
    }

    /**
     * 设置是否显示 加载更过Item
     * @param isDisplay
     */
    public void setDisplayMoreTipEnable(boolean isDisplay) {
        mIsDisplayMoreTip = isDisplay;
    }

    /**
     * 获取是否加载更多状态
     * @return
     */
    public boolean ismIsDisplayMoreTip() {
        return mIsDisplayMoreTip;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ItemType.FIRST.ordinal() : ItemType.OTHERS.ordinal();
    }

    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view;
        mContext = parent.getContext();
        /* 判断 ItemView 类型 */
        if (viewType == ItemType.FIRST.ordinal() && mIsDisplayMoreTip) {
            view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.mebee_rcv_more_view, parent, false);
            return new FistViewHolder(view);
        }

        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.mebee_rcv_rcm_shops_view, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        /* 根据ItemView 类型绑定数据 */
        if (holder instanceof FistViewHolder) {
            ((FistViewHolder) holder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(mContext, new Intent(mContext, ShopsActivity.class), null);
                }
            });
        } else if (holder instanceof ViewHolder) {
            if (((ViewHolder) holder).imgShopImg == null) {
                Log.d(TAG, "onBindViewHolder: null");
            }
            if (mIsDisplayMoreTip) {
                position -= 1;
            }
            ((ViewHolder) holder).txtPostMinCons.setText("￥" + mShops.get(position).getLimitPrice() + "起送");
            ((ViewHolder) holder).txtSoleNum.setText("销量" + mShops.get(position).getShopSale() + "单");
            ((ViewHolder) holder).txtScore.setText("" + mShops.get(position).getShopGrade());
            ((ViewHolder) holder).ratingScore.setProgress(mShops.get(position).getShopGrade() * 100);
            ((ViewHolder) holder).txtPostFee.setText("配送费￥" + mShops.get(position).getSendPrice());
            ((ViewHolder) holder).txtShopName.setText(mShops.get(position).getShopName());
            Glide.with(mContext)
                    .load(Constant.API.images + mShops.get(position).getShopPictures().get(0).getPicturePath())
                    .into(((ViewHolder) holder).imgShopImg);
            //((ViewHolder) holder).imgShopImg.setText(mShops.get(position).getShopSale());

            final int finalPosition = position;
            /* 设置 ItemVIew 点击监听 */
            ((ViewHolder) holder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,ShopActivity.class);
                    intent.putExtra("shopId",
                            String.valueOf(mShops
                                    .get(finalPosition)
                                    .getShopId()));
                    /* 启动 商家详情 Activity */
                    startActivity(mContext,
                            intent,null);
                    if (mMnItemClickListener != null) {
                        mMnItemClickListener.onItemClickListener(view, finalPosition);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mShops == null ?
                0 : ismIsDisplayMoreTip() ?
                mShops.size() + 1 : mShops.size();
    }

    class ViewHolder extends XRecyclerView.ViewHolder {
        ImageView imgShopImg;
        TextView txtShopName;
        MaterialRatingBar ratingScore;
        TextView txtScore;
        TextView txtSoleNum;
        TextView txtPostMinCons;
        TextView txtPostFee;
        View item;

        public ViewHolder(View itemView) {
            super(itemView);
            imgShopImg = itemView.findViewById(R.id.img_shop);
            txtShopName = itemView.findViewById(R.id.txt_shop_name);
            txtPostFee = itemView.findViewById(R.id.txt_postfee);
            ratingScore = itemView.findViewById(R.id.ratingbar_score);
            txtScore = itemView.findViewById(R.id.txt_score);
            txtSoleNum = itemView.findViewById(R.id.txt_sole_num);
            txtPostMinCons = itemView.findViewById(R.id.txt_post_min_consume);
            item = itemView;
        }
    }

    /**
     * 第一个 Item View ，显示: 商家     加载更多 >
     */
    class FistViewHolder extends XRecyclerView.ViewHolder {
        View item;

        public FistViewHolder(View itemView) {
            super(itemView);
            item = itemView;
        }
    }

    /**
     * 下拉刷新时调用，清空原有数据，重新加载
     * @param shops
     */
    public void refresh(List<Shop> shops) {
        if (shops != null) {
            forbidNull();
            mShops.clear();
            loadMore(shops);
        }
    }

    /**
     * 上拉加载时调用，add 加载数据
     * @param shops
     */
    public void loadMore(List<Shop> shops) {
        if (shops != null) {
            forbidNull();
            int position = mShops.size();
            mShops.addAll(position, shops);
            notifyDataSetChanged();
        }
    }

    private void forbidNull() {
        if (mShops == null) {
            mShops = new ArrayList<>();
        }
    }
}
