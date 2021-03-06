package com.leonidas.zt.bycs.group.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.leonidas.zt.bycs.group.activity.ClassificationActivity;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsClassificationVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 华强 on 2018/1/24.
 * Version: V1.0
 * Description: 拼购分类RecyclerView的适配器
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

class ClassificationRvAdapter extends RecyclerView.Adapter {
    private static final String TAG = "ClassificationRvAdapter";

    //分类数据的数据集
    private List<GroupPurchaseGoodsClassificationVO.DataBean.ProductCategoriesBean.ListBean> ClassificationDataList;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ClassificationRvAdapter(Context mContext, List<GroupPurchaseGoodsClassificationVO.DataBean.ProductCategoriesBean.ListBean> ClassificationDataList) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.ClassificationDataList = ClassificationDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.leonidas_item_classification, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.setData(ClassificationDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return ClassificationDataList.size();
    }

    /**
     * 分类Item的ViewHolder
     */
    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivClassification; //分类图片
        TextView tvClassification; //分类名称

        public Holder(View itemView) {
            super(itemView);
            tvClassification = itemView.findViewById(R.id.txt_sort);
            ivClassification = itemView.findViewById(R.id.img_sort);
        }

        /**
         * 为ViewHolder添加数据
         * @param data
         */
        public void setData(GroupPurchaseGoodsClassificationVO.DataBean.ProductCategoriesBean.ListBean data) {
            tvClassification.setText(data.getCategoryName());
            GlideApp.with(mContext)
                    .load(Api.BaseImg + data.getCategoryIcon())
                    .error(R.mipmap.mebee_load_fail)
                    .transform(new RoundedCorners(20))
                    .transition(new DrawableTransitionOptions().crossFade(200))
                    .into(ivClassification);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //ClassificationDataList.get(getLayoutPosition());
            Intent intent = new Intent(mContext, ClassificationActivity.class);
            intent.putExtra("classifications", (Serializable) ClassificationDataList);
            intent.putExtra("position",getLayoutPosition());
            mContext.startActivity(intent);
        }
    }
}
