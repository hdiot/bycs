package com.leonidas.zt.bycs.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.basket.vo.GroupPurchaseGroupVO;
import com.leonidas.zt.bycs.group.utils.Api;

import java.util.List;

/**
 * Created by 华强 on 2018/4/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseGroupRvAdapter extends RecyclerView.Adapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<GroupPurchaseGroupVO.DataBean>  mGroupPurchaseGroupList;
    private final LinearLayout llCurrentGroup;
    private final ImageView ivSelectedImg;
    private final TextView tvSelectedGroupId;
    private final TextView tvSelectedPgStartTimeAndLakePeople;
    private GroupPurchaseGroupVO.DataBean Group;

    public GroupPurchaseGroupRvAdapter(Context context, List<GroupPurchaseGroupVO.DataBean> groupPurchaseGroupList, GroupPurchaseGroupVO.DataBean group,
                                       LinearLayout llCurrentGroup, ImageView ivSelectedImg, TextView tvSelectedPgStartTimeAndLakePeople, TextView tvSelectedGroupId) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mGroupPurchaseGroupList = groupPurchaseGroupList;
        this.Group = group;
        this.llCurrentGroup = llCurrentGroup;
        this.ivSelectedImg = ivSelectedImg;
        this.tvSelectedPgStartTimeAndLakePeople = tvSelectedPgStartTimeAndLakePeople;
        this.tvSelectedGroupId = tvSelectedGroupId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.leonidas_item_group_purchase_group, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.setData(mGroupPurchaseGroupList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroupPurchaseGroupList.size();
    }

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivImg;
        private TextView tvPgStartTimeAndLakePeople;
        private TextView tvJoinGpg;
        private TextView tvGroupId;

        public Holder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvPgStartTimeAndLakePeople = itemView.findViewById(R.id.tv_pg_start_time_and_lake_people);
            tvGroupId = itemView.findViewById(R.id.tv_group_id);
            tvJoinGpg = itemView.findViewById(R.id.tv_join_gpg);
        }

        public void setData(GroupPurchaseGroupVO.DataBean dataBean) {
            tvPgStartTimeAndLakePeople.setText(dataBean.getCreateTime() + "开团" + "，还差"
                    + (dataBean.getGroupNum() - dataBean.getGroupCount()) + "人。");
            tvGroupId.setText("拼购组ID：" + dataBean.getGroupId());
            tvJoinGpg.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.e("position", getLayoutPosition() + "");
            GroupPurchaseGroupVO.DataBean tmpGroup = mGroupPurchaseGroupList.get(Holder.this.getLayoutPosition() - 1);
            llCurrentGroup.setVisibility(View.VISIBLE);
            GlideApp.with(mContext)
                    .load(Api.BaseImg
                            + tmpGroup.getGroupMemberList().get(0).getUserHeadPath())
                    .error(R.mipmap.mebee_image_bg)
                    .transform(new RoundedCorners(20))
                    .transition(new DrawableTransitionOptions().crossFade(200))
                    .into(ivImg);
            tvPgStartTimeAndLakePeople.setText(tmpGroup.getCreateTime() + "开团" + "，还差"
                    + (tmpGroup.getGroupNum() - tmpGroup.getGroupCount()) + "人。");
            tvSelectedGroupId.setText("拼购组ID：" + tmpGroup.getGroupId());
            Group.setGroupId(tmpGroup.getGroupId());
        }

    }
}
