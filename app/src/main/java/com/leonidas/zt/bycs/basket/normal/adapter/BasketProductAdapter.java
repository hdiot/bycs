package com.leonidas.zt.bycs.basket.normal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.leonidas.zt.bycs.basket.normal.bean.ProductItem;
import com.leonidas.zt.bycs.basket.normal.callback.ProductCheckedCallBack;
import com.leonidas.zt.bycs.index.activity.ProductDetailActivity;
import com.leonidas.zt.bycs.index.bean.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: mebee.
 * Time: 2018/4/9 13:36
 * Description: Null
 */
public class BasketProductAdapter extends RecyclerView.Adapter<BasketProductAdapter.VH> {

    private Context mContext;
    private List<ProductItem> mProductItems = new ArrayList<>();
    private ProductItem mProductItemTmp;
    private ProductCheckedCallBack mCheckedChangeCallBack;

    public void setCheckedChangeCallBack(ProductCheckedCallBack productCheckedCallBack) {
        this.mCheckedChangeCallBack = productCheckedCallBack;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mebee_item_cart_content, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        final ProductItem productItem = mProductItems.get(position);
        holder.name.setText(productItem.getProductName());
        holder.limit.setText(productItem.getLimitNumber());
        holder.cost.setText("￥" + productItem.getProductQuantity()*productItem.getProductPrice());
        holder.quantity.setText(productItem.getProductQuantity()+"");
        holder.checkBox.setChecked(productItem.isSelected());
        String []  un = unitSpit(productItem.getLimitNumber());
        holder.amount.setText("共计：" + productItem.getProductQuantity()*Double.valueOf(un[0]) + un[1]);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    if (mCheckedChangeCallBack != null)
                        mCheckedChangeCallBack.onChecked(position);
                } else {
                    if (mCheckedChangeCallBack != null)
                        mCheckedChangeCallBack.onUnchecked(position);
                }

                try {
                    mProductItemTmp = (ProductItem) productItem.clone();
                    mProductItemTmp.setSelected(!mProductItemTmp.isSelected());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });
        GlideApp.with(mContext)
                .load(Constant.API.images + productItem.getProductIcon())
                .error(R.mipmap.mebee_image_bg)
                .transform(new RoundedCorners(20))
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("productInfo", (Serializable) productItem2Product(productItem));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductItems != null ? mProductItems.size() : 0;
    }

    public void setData(List<ProductItem> items) {

        if (items != null) {
            mProductItems.clear();
            mProductItems.addAll(items);
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

    class VH extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private ImageView imageView;
        private TextView name;
        private TextView limit;
        private TextView amount;
        private TextView quantity;
        private TextView cost;
        public VH(View itemView) {
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

    public double getSelectAmount(){
        double selectAmount = 0.0;
        for (ProductItem mProductItem : mProductItems) {
            if (mProductItem.isSelected()) {
                selectAmount += mProductItem.getProductQuantity() * mProductItem.getProductPrice();
            }
        }
        return selectAmount;
    }
}
