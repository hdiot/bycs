package com.leonidas.zt.bycs.basket.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.activity.SelectTakeDeliveryAddressActivity;
import com.leonidas.zt.bycs.basket.vo.UserAllReceiveAddressVO;

import java.io.Serializable;

/**
 * Created by 华强 on 2018/3/23.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class ReceiveAddressRvAdapter extends RecyclerView.Adapter{

    private final UserAllReceiveAddressVO data;
    private final Activity mActivity;
    private final LayoutInflater mLayoutInflater;

    public ReceiveAddressRvAdapter(Activity activity, UserAllReceiveAddressVO data) {
        this.mActivity = activity;
        this.mLayoutInflater = LayoutInflater.from(mActivity);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.leonidas_item_receive_address, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.setData(data.getData().get(position));
    }

    @Override
    public int getItemCount() {
        return data.getData().size();
    }

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llDeliveryAddress;
        private TextView tvTakeDeliveryName;
        private TextView tvTakeDeliveryPhoneNum;
        private TextView tvTakeDeliveryAddress;
        private UserAllReceiveAddressVO.DataBean data;

        public Holder(View itemView) {
            super(itemView);
            llDeliveryAddress = itemView.findViewById(R.id.ll_delivery_address);
            tvTakeDeliveryName = itemView.findViewById(R.id.tv_take_delivery_name);
            tvTakeDeliveryPhoneNum = itemView.findViewById(R.id.tv_take_delivery_phone_num);
            tvTakeDeliveryAddress = itemView.findViewById(R.id.tv_take_delivery_address);
        }


        public void setData(UserAllReceiveAddressVO.DataBean data) {
            this.data = data;
            tvTakeDeliveryName.setText(data.getConsignee());
            tvTakeDeliveryPhoneNum.setText(data.getPhone());
            tvTakeDeliveryAddress.setText(data.getUserAddress());
            llDeliveryAddress.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //将数据返回给ConfirmOrderActivity

            Bundle bundle = new Bundle();
            bundle.putSerializable(SelectTakeDeliveryAddressActivity.Tag_ReceiveAddress, (Serializable) data);

            Intent intent = new Intent();
            intent.putExtra(SelectTakeDeliveryAddressActivity.Tag_ReceiveAddress, bundle);
            mActivity.setResult(SelectTakeDeliveryAddressActivity.ResultCode_SelectedAddress,intent);
            mActivity.finish();
        }

    }
}
