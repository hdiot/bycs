package com.leonidas.zt.bycs.index.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;

/**
 * Created by mebee on 2018/1/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvShopAdapter extends RecyclerView.Adapter<RcvShopAdapter.ViewHolder> {
    private static final String TAG = "RcvShopAdapter";
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.mebee_rcv_rcm_shops_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shopName.setText("商家：" + position);
        holder.postFee.setText("配送：" + position + "起 | 免配送费");
        Log.d(TAG, "onBindViewHolder: " + position);
    }


    @Override
    public int getItemCount() {
        return 10;
    }
    
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shopImg;
        TextView shopName;
        TextView postFee;

        public ViewHolder(View itemView) {
            super(itemView);
            shopName = itemView.findViewById(R.id.txt_shop_name);
            postFee = itemView.findViewById(R.id.txt_postfee);
            shopImg = itemView.findViewById(R.id.img_shop);
        }
    }
}
