package com.leonidas.zt.bycs.basket.normal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.glide.GlideApp;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.basket.normal.bean.MultipleTypeBean;
import com.leonidas.zt.bycs.basket.normal.bean.ProductItem;
import com.leonidas.zt.bycs.basket.normal.bean.ShopItem;
import com.leonidas.zt.bycs.basket.normal.helper.MultipleTypeDataHelper;
import com.leonidas.zt.bycs.index.activity.ProductDetailActivity;
import com.leonidas.zt.bycs.index.activity.ShopActivityNew;
import com.leonidas.zt.bycs.index.bean.Product;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mebee on 2018/3/18.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class BasketAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MultipleTypeDataHelper mDataHelper = new MultipleTypeDataHelper();
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
            case 3:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mebee_item_cart_foot, parent, false);
                viewHolder = new VHFoot(view);
                break;

        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataHelper.getDatas().get(position).getmType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MultipleTypeBean data = mDataHelper.getDatas().get(position);

        switch (data.getmType()) {
            case 1:
                final ShopItem shopItem = (ShopItem) data.getmData();
                ((VHHead) holder).name.setText(shopItem.getShopName());
                ((VHHead) holder).checkBox.setChecked(shopItem.isSelected()==0? false: true);
                ((VHHead) holder).name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, shopItem.getShopName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, ShopActivityNew.class);
                        intent.putExtra("shopId", shopItem.getShopId());
                        mContext.startActivity(intent);
                    }
                });
                ((VHHead) holder).checkBox.setOnCheckedChangeListener(new CompoundButton
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            submitChecked(position, 1);
                        } else {
                            submitUnchecked(position, 1);
                        }
                    }
                });

                ((VHHead) holder).delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2018/4/8
                        deleteItem(position);
                        Toast.makeText(mContext, shopItem.getShopName() +" Delete", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                final ProductItem productItem = (ProductItem) data.getmData();
                ((VHItem) holder).name.setText(productItem.getProductName());
                ((VHItem) holder).limit.setText(productItem.getLimitNumber());
                ((VHItem) holder).cost.setText("￥" + productItem.getProductQuantity()*productItem.getProductPrice());
                ((VHItem) holder).quantity.setText(productItem.getProductQuantity()+"");
                ((VHItem) holder).checkBox.setChecked(productItem.isSelected());
                String []  un = unitSpit(productItem.getLimitNumber());
                ((VHItem) holder).amount.setText("共计：" + productItem.getProductQuantity()*Double.valueOf(un[0]) + un[1]);
                ((VHItem) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            submitChecked(position, 2);
                        } else {
                            submitUnchecked(position, 2);
                        }
                    }
                });
                GlideApp.with(mContext)
                        .load(Constant.API.images + productItem.getProductIcon())
                        .error(R.mipmap.mebee_image_bg)
                        .transform(new RoundedCorners(20))
                        .transition(new DrawableTransitionOptions().crossFade(200))
                        .into(((VHItem)holder).imageView);
                ((VHItem)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra("productInfo", (Serializable) productItem2Product(productItem));
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 3:
                //((VHFoot)holder).lest2Send.setText("");
        }
    }

    private void deleteItem(int position) {

    }

    private void submitUnchecked(int position, int type) {

    }

    private void submitChecked(int position, int type) {

    }

    @Override
    public int getItemCount() {
        return mDataHelper.getDatas().size();
    }

    public void loadMore(List<ShopItem> list) {
        add(list);
    }

    public void refresh(List<ShopItem> list) {
        mDataHelper.clear();
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

    private class VHFoot extends RecyclerView.ViewHolder {
        private TextView lest2Send;
        public VHFoot(View itemView) {
            super(itemView);
            lest2Send = itemView.findViewById(R.id.limit_cart_item);
        }
    }

    private void add(List<ShopItem> shopItems) {
        if (shopItems != null) {
            for (ShopItem shopItem : shopItems) {
                mDataHelper.add(1, shopItem);
                for (ProductItem productItem : shopItem.getCartItemDTOList()) {
                    mDataHelper.add(2, productItem);
                }
                mDataHelper.add(3, shopItem);
            }
        }
        notifyDataSetChanged();
    }

    private  String[] unitSpit(String s){
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(s);
        String [] sa = {"", ""};
        if (matcher.find()) {
            sa[0] = matcher.group();
            sa[1] = matcher.replaceAll("");
        }

        return sa;
    }


    private Product productItem2Product(ProductItem item) {
        Product product = new Product();

        product.setCategoryId("");
        product.setLimitNumber(item.getLimitNumber());
        product.setProductDesc(item.getProductDesc());
        product.setProductIcon(item.getProductIcon());
        product.setProductId(Long.parseLong(item.getProductId()));
        product.setProductNote("");
        product.setShopId(item.getShopId());
        product.setProductPrice(item.getProductPrice());
        product.setProductStock(item.getProductPrice());
        product.setProductName(item.getProductName());
        Log.e("product", "productItem2Product: " + product.getShopId() + "--" + item.getShopId() );
        return product;
    }


}
