package com.leonidas.zt.bycs.user.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.DateUtils;
import com.leonidas.zt.bycs.user.activity.OrderDetailActivity;
import com.leonidas.zt.bycs.user.bean.Order;
import com.leonidas.zt.bycs.user.fragment.OrdersFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mebee on 2018/3/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class OrderAdapter extends XRecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private View view;
    private Context context;
    private List<Order> orders;
    private OrdersFragment.ORDER_CLASSIFICATIONS classifications;


    public OrderAdapter(List<Order> orders, OrdersFragment.ORDER_CLASSIFICATIONS orderClass) {
        this.orders = orders;
        this.classifications = orderClass;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        view = LayoutInflater.from(context).inflate(R.layout.mebee_order_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = orders.get(position);
        holder.time.setText(DateUtils.Date2ms(order.getCreateTime()) + "");
        holder.quantity.setText("共" + order.getOrderItemList().size() + "种商品");
        holder.payment.setText("实付款￥" + order.getOrderAmount());
        switch (classifications) {
            case ALL:
                switch (order.getOrderStatus()) {
                    case 0:
                        if (order.getPayStatus() == 0) {
                            holder.state.setText("待付款");
                        } else if (order.getDeliveryStatus() == 0){
                            holder.state.setText("待配送");
                        } else if (order.getDeliveryStatus() == 1){
                            holder.state.setText("待收货");
                        } else if (order.getDeliveryStatus() == 2){
                            holder.state.setText("待评价");
                        } else if (order.getCommentStatus() == 1){
                            holder.state.setText("已完成");
                        }
                        break;
                    case 1:
                        holder.state.setText("已完成");
                        break;
                    case 2:
                        holder.state.setText("正在取消");
                        break;
                    case 3:
                        holder.state.setText("已取消");
                        break;
                }
                break;
            case WAIT4RECEIVE:
                holder.state.setText("待收货");
                break;
            case WAIT4DELIVER:
                holder.state.setText("待配送");
                break;
            case WAIT4GROUP:
                holder.state.setText("待成团");
                break;
            case WAIT4PAY:
                holder.state.setText("待支付");
                break;
            case WAIT4EVALUATE:
                holder.state.setText("待评价");
                break;
        }
        holder.itemChild.setLayoutManager(new LinearLayoutManager(context));
        holder.itemChild.setAdapter(new OrderChildAdapter(order.getOrderItemList()));
        holder.itemChild.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("order", order);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.orders == null ? 0 : orders.size();
    }

    class ViewHolder extends XRecyclerView.ViewHolder {

        TextView time;
        TextView state;
        TextView quantity;
        TextView payment;
        RecyclerView itemChild;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.order_item_order_create_time);
            state = (TextView) itemView.findViewById(R.id.order_item_order_state);
            quantity = (TextView) itemView.findViewById(R.id.order_item_child_product_quantity);
            payment = (TextView) itemView.findViewById(R.id.order_item_actual_payment);
            itemChild = (RecyclerView) itemView.findViewById(R.id.rv_order_item_child);
        }
    }

    public void refresh(@NonNull List<Order> os) {
        if (os != null) {
            orders.clear();
            loadMore(os);
        }
    }

    public void loadMore(@NonNull List<Order> os) {
        if (os != null) {
            Log.d("loadMore", "loadMore: " + os.size());
            forbidNull();
            int position = this.orders.size();
            this.orders.addAll(position, os);
            notifyDataSetChanged();
        }
    }

    private void forbidNull() {
        Log.d("forbidNull", "forbidNull: ");
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
    }
}
