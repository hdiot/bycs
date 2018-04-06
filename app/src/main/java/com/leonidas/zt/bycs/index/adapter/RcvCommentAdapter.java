package com.leonidas.zt.bycs.index.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.app.utils.DateUtils;
import com.leonidas.zt.bycs.index.bean.Comment;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * Created by mebee on 2018/1/24.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvCommentAdapter extends XRecyclerView.Adapter<RcvCommentAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Comment> mComments;

    public RcvCommentAdapter(ArrayList<Comment> comments) {
        if (comments == null)
            this.mComments = new ArrayList<>();
        this.mComments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.mebee_rcv_comment_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.star.setProgress(mComments.get(position).getCommentGrade());
        holder.content.setText(mComments.get(position).getCommentContent());
        holder.userName.setText(mComments.get(position).getUserName());
        GlideApp.with(mContext)
                .load(Constant.API.images + mComments.get(position).getUserHead())
                .error(R.mipmap.mebee_bg_square)
                .transform(new RoundedCorners(20))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(holder.head);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mComments", "onClick: " + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComments == null ? 0 : mComments.size();
    }

    class ViewHolder extends XRecyclerView.ViewHolder{
        private ImageView head;
        private TextView userName;
        private TextView content;
        private MaterialRatingBar star;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            head = (ImageView) itemView.findViewById(R.id.comment_user_head);
            userName = (TextView) itemView.findViewById(R.id.comment_user_name);
            content = (TextView) itemView.findViewById(R.id.comment_content);
            star = (MaterialRatingBar) itemView.findViewById(R.id.comment_star_grade);
            time = (TextView) itemView.findViewById(R.id.comment_time);
        }
    }

    /**
     * 下拉刷新时调用，清空原有数据，重新加载
     * @param comments
     */
    public void refresh(List<Comment> comments) {
        if (comments != null) {
            forbidNull();
            mComments.clear();
            loadMore(comments);
        }
    }

    /**
     * 上拉加载时调用，add 加载数据
     * @param comments
     */
    public void loadMore(List<Comment> comments) {
        if (comments != null) {
            forbidNull();
            int position = mComments.size();
            mComments.addAll(position, comments);
            notifyDataSetChanged();
        }
    }

    private void forbidNull() {
        if (mComments == null) {
            mComments = new ArrayList<>();
        }
    }
}
