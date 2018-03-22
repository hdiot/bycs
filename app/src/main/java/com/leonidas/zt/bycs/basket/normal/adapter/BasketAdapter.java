package com.leonidas.zt.bycs.basket.normal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.basket.normal.bean.MultipleTypeBean;
import com.leonidas.zt.bycs.basket.normal.bean.ProductItem;
import com.leonidas.zt.bycs.basket.normal.bean.ShopItem;
import com.leonidas.zt.bycs.basket.normal.helper.MultipleTypeDataHelper;

import java.util.List;

/**
 * Created by mebee on 2018/3/18.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class BasketAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MultipleTypeDataHelper mDatas = new MultipleTypeDataHelper();
    private Context mContext;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view;
        ViewHolder viewHolder = null;

        switch (viewType) {
            case 1:
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.mebee_item_cart_head, parent, false);
                viewHolder = new VHHead(view);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mebee_item_cart_content, parent, false);
                viewHolder = new VHItem(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.getDatas().get(position).getmType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MultipleTypeBean data = mDatas.getDatas().get(position);

        switch (data.getmType()) {
            case 1:
                ShopItem shopItem = (ShopItem) data.getmData();
                ((VHHead) holder).name.setText(shopItem.getShopName());
                ((VHHead) holder).checkBox.setOnCheckedChangeListener(new CompoundButton
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });

                ((VHHead) holder).delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case 2:
                ProductItem productItem = (ProductItem) data.getmData();
                ((VHItem) holder).name.setText(productItem.getProductName());
                ((VHItem) holder).limit.setText(productItem.getLimitNumber());
                ((VHItem) holder).cost.setText(productItem.getProductQuantity()*productItem.getProductPrice()+"");
                ((VHItem) holder).quantity.setText(productItem.getProductQuantity()+"");
                ((VHItem) holder).amount.setText(productItem.getProductQuantity()+"");
                ((VHItem) holder).checkBox.setOnCheckedChangeListener(new CompoundButton
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });
                GlideApp.with(mContext)
                        .load(Constant.API.images + productItem.getProductIcon())
                        .error(R.mipmap.mebee_image_bg)
                        .transform(new RoundedCorners(20))
                        .transition(new DrawableTransitionOptions().crossFade(200))
                        .into(((VHItem)holder).imageView);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.getDatas().size();
    }

    public void loadMore(List<ShopItem> list) {
        add(list);
    }

    public void refresh(List<ShopItem> list) {
        mDatas.clear();
        add(list);
    }

    class VHHead extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView name;
        private ImageView delete;

        public VHHead(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.normal_cart_Item_head_check);
            name = itemView.findViewById(R.id.normal_cart_Item_head_name);
            delete = itemView.findViewById(R.id.normal_cart_head_delete_img);
        }
    }

    class VHItem extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView imageView;
        private TextView name;
        private TextView limit;
        private TextView amount;
        private TextView quantity;
        private TextView cost;

        public VHItem(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.normal_basket_content_check);
            imageView = itemView.findViewById(R.id.normal_basket_content_img);
            name = itemView.findViewById(R.id.normal_basket_content_name);
            limit = itemView.findViewById(R.id.normal_basket_content_limit);
            quantity = itemView.findViewById(R.id.normal_basket_content_quantity);
            amount = itemView.findViewById(R.id.normal_basket_content_all);
            cost = itemView.findViewById(R.id.normal_basket_content_payment);
        }
    }

    private void add(List<ShopItem> shopItems) {
        if (shopItems != null) {
            for (ShopItem shopItem : shopItems) {
                ShopItem shopInfo = new ShopItem();
                shopInfo.setCartId(shopItem.getCartId());
                shopInfo.setLimitPrice(shopItem.getLimitPrice());
                shopInfo.setSendPrice(shopItem.getSendPrice());
                shopInfo.setShopId(shopItem.getShopId());
                shopInfo.setUserId(shopItem.getUserId());
                shopInfo.setShopState(shopItem.getShopState());
                shopInfo.setShopName(shopItem.getShopName());
                shopInfo.setCartItemDTOList(null);
                mDatas.add(1, shopInfo);
                for (ProductItem productItem : shopItem.getCartItemDTOList()) {
                    mDatas.add(2, productItem);
                }
            }
        }
        notifyDataSetChanged();
    }

}
