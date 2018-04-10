package com.leonidas.zt.bycs.basket.normal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.normal.bean.ProductItem;
import com.leonidas.zt.bycs.basket.normal.bean.ShopItem;
import com.leonidas.zt.bycs.basket.normal.callback.ProductCheckedCallBack;
import com.leonidas.zt.bycs.basket.normal.callback.ShopCheckedCallBack;
import com.leonidas.zt.bycs.index.activity.ShopActivityNew;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: mebee.
 * Time: 2018/4/9 13:24
 * Description: Null
 */
public class BasketShopAdapter extends RecyclerView.Adapter<BasketShopAdapter.VH> {

    private Context mContext;
    private final ArrayList<ShopItem> mShopsCart = new ArrayList<>();

    private ShopCheckedCallBack mShopCheckedCallBack;

    public void setShopCheckedCallBack(ShopCheckedCallBack shopCheckedCallBack) {
        this.mShopCheckedCallBack = shopCheckedCallBack;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater
                .from(mContext).inflate(R.layout.mebee_item_cart_item, parent, false);

        return new VH(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        checkAll();
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {

        final ShopItem shopItem = mShopsCart.get(position);
        final VH vh = holder;
        vh.name.setText(shopItem.getShopName());
        vh.checkBox.setChecked(shopItem.isSelected()==0? false: true);
        vh.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, shopItem.getShopName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ShopActivityNew.class);
                intent.putExtra("shopId", shopItem.getShopId());
                mContext.startActivity(intent);
            }
        });

        final BasketProductAdapter adapter = new BasketProductAdapter();

        adapter.setData(shopItem.getCartItemDTOList());
        adapter.setCheckedChangeCallBack(new ProductCheckedCallBack() {
            @Override
            public void onChecked(int p) {
                Log.e("check", "onChecked: " + p);
                if (mShopCheckedCallBack != null) {
                    List<String> ids = new ArrayList<>();
                    ids.add(shopItem.getCartItemDTOList().get(p).getItemId());
                    mShopCheckedCallBack.onItemCheckedChange(ids, true);
                }

                shopItem.getCartItemDTOList().get(p).setSelected(true);
                int check = 1;
                for (ProductItem productItem : shopItem.getCartItemDTOList()) {
                    Log.e("check", p+"productItem: " + productItem.isSelected());
                    if (!productItem.isSelected()) {
                        check = 0;
                        break;
                    }

                }

                adapter.setData(shopItem.getCartItemDTOList());
                shopItem.setSelected(check);
                notifyDataSetChanged();
            }

            @Override
            public void onUnchecked(int p) {
                Log.e("check", "onUnchecked: " + p);
                if (mShopCheckedCallBack != null) {
                    List<String> ids = new ArrayList<>();
                    ids.add(shopItem.getCartItemDTOList().get(p).getItemId());
                    mShopCheckedCallBack.onItemCheckedChange(ids, false);
                }
                shopItem.getCartItemDTOList().get(p).setSelected(false);
                shopItem.setSelected(0);
                if (vh.checkBox.isChecked()) {
                    vh.checkBox.setChecked(false);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        vh.prouctsRv.setLayoutManager(layoutManager);
        vh.prouctsRv.setAdapter(adapter);

        vh.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick", "onClick: " + vh.checkBox.isChecked());
                if (vh.checkBox.isChecked()) {
                    if (mShopCheckedCallBack != null) {
                        mShopCheckedCallBack.onChecked(shopItem.getCartId(), position);
                    }

                    for (int i = 0; i < shopItem.getCartItemDTOList().size(); i++) {
                        shopItem.getCartItemDTOList().get(i).setSelected(true);
                    }

                    adapter.setData(shopItem.getCartItemDTOList());
                    shopItem.setSelected(1);

                } else {
                    if (mShopCheckedCallBack != null) {
                        mShopCheckedCallBack.onUnchecked(shopItem.getCartId(), position);
                    }
                    for (int i = 0; i < shopItem.getCartItemDTOList().size(); i++) {
                        shopItem.getCartItemDTOList().get(i).setSelected(false);
                    }
                    shopItem.setSelected(0);
                }

                adapter.setData(shopItem.getCartItemDTOList());
                checkAll();

            }

        });

        vh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("CheckedChange", "onCheckedChanged: " + isChecked );
                checkAll();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mShopsCart != null ? mShopsCart.size() : 0;
    }

    public void loadMore(List<ShopItem> list) {
        add(list);
    }

    public void refresh(List<ShopItem> list) {
        mShopsCart.clear();
        add(list);
    }


    private void add(List<ShopItem> shopItems) {
        if (shopItems != null) {
            mShopsCart.addAll(shopItems);
        }
        notifyDataSetChanged();
    }

    private void checkAll(){
        boolean allCheck = true;
        for (ShopItem shopItem : mShopsCart) {
            if (shopItem.isSelected() == 0) {
                allCheck = false;
                break;
            }
        }

        if (mShopCheckedCallBack != null) {
            mShopCheckedCallBack.onAllCheck(allCheck);
        }
    }

    public void selectProductFail() {

    }

    public void selectProductSucceed() {

    }

    public void selectShopSucceed() {

    }

    public void selectShopFail() {

    }

    public void selectAll(boolean checked) {
        List<String> ids = new ArrayList<>();
        for (ShopItem shopItem : mShopsCart) {
            shopItem.setSelected(checked? 1: 0);
            for (ProductItem productItem : shopItem.getCartItemDTOList()) {
                productItem.setSelected(checked);
                ids.add(productItem.getItemId());
            }
        }
        if (mShopCheckedCallBack != null) {
            mShopCheckedCallBack.onItemCheckedChange(ids, checked);
        }
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView name;
        private ImageView delete;
        private TextView lest2send;
        private RecyclerView prouctsRv;

        public VH(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.normal_cart_Item_head_check);
            name = itemView.findViewById(R.id.normal_cart_Item_head_name);
            delete = itemView.findViewById(R.id.normal_cart_head_delete_img);
            lest2send = itemView.findViewById(R.id.limit_cart_item);
            prouctsRv = itemView.findViewById(R.id.rv_cart_product_item);
        }


    }
}
