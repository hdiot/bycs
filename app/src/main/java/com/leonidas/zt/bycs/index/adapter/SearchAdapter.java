package com.leonidas.zt.bycs.index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.normal.helper.MultipleTypeDataHelper;
import com.leonidas.zt.bycs.index.bean.Product;
import com.leonidas.zt.bycs.index.bean.SearchShop;
import com.leonidas.zt.bycs.index.bean.ShopInfo;
import com.mcxtzhang.lib.AnimShopButton;

import java.util.ArrayList;
import java.util.List;

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
        public HolderShop(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.header_shop_img);
            grade =  (TextView) itemView.findViewById(R.id.header_shop_grade);
            sale =  (TextView) itemView.findViewById(R.id.header_shop_sale);
            limitPrice =  (TextView) itemView.findViewById(R.id.header_shop_limit_price);
            sendPrice =  (TextView) itemView.findViewById(R.id.header_shop_send_price);
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
                ShopInfo shopInfo = new ShopInfo();
                shopInfo.setLimitPrice(shop.getLimitPrice());
                shopInfo.setSendPrice(shop.getSendPrice());
                shopInfo.setShopAddress(shop.getShopAddress());
                shopInfo.setShopDesc(shop.getShopDesc());
                shopInfo.setShopIcon(shop.getShopIcon());
                shopInfo.setShopGrade(shop.getShopGrade());
                shopInfo.setShopId(shop.getShopId());
                shopInfo.setShopPhone(shop.getShopPhone());
                shopInfo.setShopSale(shop.getShopSale());
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
}
