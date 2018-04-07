package com.leonidas.zt.bycs.index.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.leonidas.zt.bycs.basket.normal.bean.MultipleTypeBean;
import com.leonidas.zt.bycs.basket.normal.helper.MultipleTypeDataHelper;
import com.leonidas.zt.bycs.index.activity.ProductDetialActivity;
import com.leonidas.zt.bycs.index.activity.ShopActivityNew;
import com.leonidas.zt.bycs.index.bean.Product;
import com.leonidas.zt.bycs.index.bean.SearchShop;
import com.leonidas.zt.bycs.index.bean.Shop;
import com.leonidas.zt.bycs.index.bean.ShopInfo;
import com.mcxtzhang.lib.AnimShopButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Author: mebee.
 * Time: 2018/4/1 19:16
 * Description: Null
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MultipleTypeDataHelper mMultipleTypeDataHelper = new MultipleTypeDataHelper();
    private static final int TYPE_SHOP = 1;
    private static final int TYPE_PRODUCT = 2;
    private static final int TYPE_MORE = 3;
    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_MORE:
                Log.e("searchHolder", "TYPE_MORE: ");
                view = LayoutInflater.from(mContext).inflate(R.layout.mebee_search_more_foot, parent, false);
                holder = new HolderMore(view);
                break;
            case TYPE_PRODUCT:
                Log.e("searchHolder", "TYPE_PRODUCT: ");
                view = LayoutInflater.from(mContext).inflate(R.layout.mebee_rcv_product_view, parent, false);
                holder = new HolderProduct(view);
                break;
            case TYPE_SHOP:
                Log.e("searchHolder", "TYPE_SHOP: ");
                view = LayoutInflater.from(mContext).inflate(R.layout.mebee_search_shop_header, parent, false);
                holder =new HolderShop(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MultipleTypeBean bean = mMultipleTypeDataHelper.getDatas().get(position);
        if (holder instanceof HolderMore) {

        } else if (holder instanceof HolderProduct){
            final Product product = (Product) bean.getmData();
            ((HolderProduct)holder).productPriceTxt.setText("￥" + product.getProductPrice());
            ((HolderProduct)holder).productStockTxt.setText("剩余" + product.getProductStock() + "份");
            ((HolderProduct)holder).productNameTxt.setText(product.getProductName());
            ((HolderProduct)holder).productLimitTxt.setText("每份" + product.getLimitNumber());
            GlideApp.with(mContext)
                    .load(Constant.API.images+product.getProductIcon())
                    .error(R.mipmap.mebee_image_bg)
                    .transform(new RoundedCorners(20))
                    .transition(new DrawableTransitionOptions().crossFade(200))
                    .into(((HolderProduct)holder).productImg);
            ((HolderProduct)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ProductDetialActivity.class);
                    intent.putExtra("shopId",product.getShopId());
                    intent.putExtra("productInfo", (Serializable) product);
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof HolderShop){
            final ShopInfo shopInfo = (ShopInfo) bean.getmData();
            ((HolderShop)holder).name.setText(shopInfo.getShopName());
            ((HolderShop)holder).grade.setText("评分：" + shopInfo.getShopGrade());
            ((HolderShop)holder).sale.setText("销量" + shopInfo.getShopSale() + "单");
            ((HolderShop)holder).limitPrice.setText("￥" + shopInfo.getLimitPrice() + "起送");
            ((HolderShop)holder).sendPrice.setText("配送费￥" + shopInfo.getSendPrice());
            GlideApp.with(mContext)
                    .load(Constant.API.images + shopInfo.getShopIcon())
                    .error(R.mipmap.mebee_image_bg)
                    .transform(new RoundedCorners(20))
                    .transition(new DrawableTransitionOptions().crossFade(200))
                    .into(((HolderShop)holder).image);

            ((HolderShop)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,ShopActivityNew.class);
                    intent.putExtra("shopId", shopInfo.getShopId());
                    intent.putExtra("shop", shopInfoToShop(shopInfo));
                     /*启动 商家详情 Activity */
                    startActivity(mContext,
                            intent, null);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mMultipleTypeDataHelper.getDatas().get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return mMultipleTypeDataHelper.getDatas() == null ? 0 : mMultipleTypeDataHelper.getDatas().size();
    }

    /**
     * 商店 Item ViewHolder
     */
    class HolderShop extends RecyclerView.ViewHolder{
        ImageView image;
        TextView grade;
        TextView sale;
        TextView limitPrice;
        TextView sendPrice;
        TextView name;
        public HolderShop(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.header_shop_img);
            grade =  (TextView) itemView.findViewById(R.id.header_shop_grade);
            sale =  (TextView) itemView.findViewById(R.id.header_shop_sale);
            limitPrice =  (TextView) itemView.findViewById(R.id.header_shop_limit_price);
            sendPrice =  (TextView) itemView.findViewById(R.id.header_shop_send_price);
            name = (TextView) itemView.findViewById(R.id.header_shop_name);
        }
    }

    /**
     * 商品 Item ViewHolder
     */
    class HolderProduct extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productNameTxt;
        TextView productStockTxt;
        TextView productLimitTxt;
        TextView productPriceTxt;
        TextView productOrgPriceTxt;
        AnimShopButton animShopButton;

        public HolderProduct(View itemView) {
            super(itemView);
            productImg = (ImageView) itemView.findViewById(R.id.img_product);
            productLimitTxt = (TextView) itemView.findViewById(R.id.txt_product_limit);
            productNameTxt = (TextView) itemView.findViewById(R.id.txt_product_name);
            productPriceTxt = (TextView) itemView.findViewById(R.id.txt_product_price);
            productOrgPriceTxt = (TextView) itemView.findViewById(R.id.txt_product_org_price);
            productStockTxt = (TextView) itemView.findViewById(R.id.txt_product_stock);
            animShopButton = (AnimShopButton) itemView.findViewById(R.id.shop_button);
        }
    }

    class HolderMore extends RecyclerView.ViewHolder{
        TextView more;
        public HolderMore(View itemView) {
            super(itemView);
            more = (TextView) itemView.findViewById(R.id.more_foot_txt);
        }
    }

    private class MoreProduct{

        List<Product> mHideProducts = new ArrayList<>();

        public List<Product> getHideProducts() {
            return mHideProducts;
        }

        public void setHideProducts(List<Product> hideProducts) {
            mHideProducts = hideProducts;
        }

        public void addProduct(Product product){
            mHideProducts.add(product);
        }
        public int getSize(){
            return mHideProducts.size();
        }
    }

    public void add(List<SearchShop> shops) {

        if (shops!=null){
            for (SearchShop shop : shops) {
                Log.e("SearchShopID", shop.getShopId() );
                ShopInfo shopInfo = searchShopToShopInfo(shop);
                mMultipleTypeDataHelper.add(TYPE_SHOP, shopInfo);
                for (int i = 0; i < shop.getShopProducts().size(); i++) {
                    if (i < 2){
                        mMultipleTypeDataHelper.add(TYPE_PRODUCT, shop.getShopProducts().get(i));
                    } else {
                        MoreProduct moreProduct = new MoreProduct();
                        moreProduct.setHideProducts(shop.getShopProducts().subList(2, shop.getShopProducts().size()-1));
                        mMultipleTypeDataHelper.add(TYPE_MORE,moreProduct);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    public Shop shopInfoToShop(ShopInfo shopInfo){
        Shop shop = new Shop();
        shop.setLimitPrice(shopInfo.getLimitPrice());
        shop.setShopName(shopInfo.getShopName());
        shop.setShopAddress(shopInfo.getShopAddress());
        shop.setShopPhone(shopInfo.getShopPhone());
        shop.setShopDesc(shopInfo.getShopDesc());
        shop.setShopNote(shopInfo.getShopNote());
        shop.setShopSale(shopInfo.getShopSale());
        shop.setShopGrade(shopInfo.getShopGrade());
        shop.setSendPrice(shopInfo.getSendPrice());
        shop.setWorkTime(shopInfo.getWorkTime());
        shop.setShopId(shopInfo.getShopId());
        List<Shop.ShopPicturesBean> picturesBeans = new LinkedList<>();
        Shop.ShopPicturesBean picturesBean = new Shop.ShopPicturesBean();
        picturesBean.setPicturePath(shopInfo.getShopIcon());
        picturesBeans.add(picturesBean);
        shop.setShopPictures(picturesBeans);
        return shop;
    }

    public ShopInfo searchShopToShopInfo(SearchShop shop){
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setLimitPrice(shop.getLimitPrice());
        shopInfo.setShopName(shop.getShopName());
        shopInfo.setShopAddress(shop.getShopAddress());
        shopInfo.setShopPhone(shop.getShopPhone());
        shopInfo.setShopDesc(shop.getShopDesc());
        shopInfo.setShopNote(shop.getShopNote());
        shopInfo.setShopSale(shop.getShopSale());
        shopInfo.setShopGrade(shop.getShopGrade());
        shopInfo.setSendPrice(shop.getSendPrice());
        shopInfo.setWorkTime(shop.getWorkTime());
        shopInfo.setShopIcon(shop.getShopIcon());
        shopInfo.setShopId(shop.getShopId());
        return shopInfo;
    }
}
