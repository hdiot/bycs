package com.leonidas.zt.bycs.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.user.bean.OrderItem;

import java.util.List;

/**
 * Created by mebee on 2018/3/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class OrderChildAdapter extends RecyclerView.Adapter<OrderChildAdapter.ViewHolder> {

    private View view;
    private Context context;
    private List<OrderItem> orderItems;

    public OrderChildAdapter(@NonNull List<OrderItem> o) {
        this.orderItems = o;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(context).inflate(R.layout.mebee_order_item_child_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.num.setText("x" + orderItem.getProductQuantity() + orderItem.getProductUnit());
        holder.name.setText(orderItem.getProductName());
        holder.price.setText("￥" + orderItem.getProductPrice());
        GlideApp.with(context)
                .load(Constant.API.images + orderItem.getProductIcon())
                .transform(new RoundedCorners(20))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.orderItems == null ? 0 : orderItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView price;
        TextView name;
        TextView num;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.order_item_child_product_img);
            price = (TextView) itemView.findViewById(R.id.order_item_child_product_price);
            name = (TextView) itemView.findViewById(R.id.order_item_child_product_name);
            num = (TextView) itemView.findViewById(R.id.order_item_child_product_quantity);
        }
    }
}
