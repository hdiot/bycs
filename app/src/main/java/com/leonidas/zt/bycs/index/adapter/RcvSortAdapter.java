package com.leonidas.zt.bycs.index.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.activity.SortActivity;
import com.leonidas.zt.bycs.index.bean.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mebee on 2018/1/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvSortAdapter extends RecyclerView.Adapter<RcvSortAdapter.ViewHolder> {

    private List<Category> mCategories = new ArrayList<>();
    private Context mContext;

    public RcvSortAdapter(@Nullable List<Category> categories) {
        if (categories != null) {
            this.mCategories = categories;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.mebee_rcv_shops_sort_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txt.setText(mCategories.get(position).getCategoryName());
        Glide.with(mContext)
                .load(Constant.API.images + mCategories.get(position).getCategoryIcon())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SortActivity.class);
                intent.putExtra("categoryId",mCategories.get(position).getCategoryId());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCategories == null ? 0 : mCategories.size();
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


    public void refresh(List<Category> categories){
        if (categories != null){
            forbidNull();
            mCategories.clear();
            loadMore(categories);
        }
    }

    public void loadMore(List<Category> categories){
        if (categories != null){
            forbidNull();
            int position = mCategories.size();
            mCategories.addAll(position,categories);
            notifyDataSetChanged();
        }
    }

    private void forbidNull(){
        if (mCategories == null) {
            mCategories = new ArrayList<>();
        }
    }
}
