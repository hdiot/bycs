package com.leonidas.zt.bycs.index.adapter;

import android.support.v7.widget.RecyclerView;
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
public class RcvSortAdapter extends RecyclerView.Adapter<RcvSortAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.mebee_rcv_shops_sort_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt.setText("分类：" + position);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView txt;
        public ViewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt_sort);
            image = itemView.findViewById(R.id.img_sort);
        }
    }
}
