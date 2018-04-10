package com.leonidas.zt.bycs.basket.normal.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by mebee on 2018/3/17.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class CartModelImp implements CartModel {
    private static final String TAG = "CartModelImp";
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    public void addToCart(String uId, String pId, int quantity, int category, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        params.put("productId", pId);
        params.put("productQuantity", quantity);
        params.put("itemCategory", category);
        okHttpHelper.doPost(Constant.API.cartItem, JSON.toJSON(params).toString(), callback);
    }

    @Override
    public void getCart(String uId, BaseCallback callback) {
        StringBuffer params = new StringBuffer();
        params.append("?userId=");
        params.append(uId);
        Log.e("getCart", params.toString());
        okHttpHelper.doGet(Constant.API.cartItem + params.toString(), callback);
    }

    @Override
    public void changeItem(String uId, String itemId, int quantity, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        params.put("cartItemId", itemId);
        params.put("quantity", quantity);
        okHttpHelper.doPut(Constant.API.cartItem, JSON.toJSON(params).toString(), callback);
    }

    @Override
    public void deleteItem(String uId, String itemId, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        params.put("cartItemId", itemId);
        okHttpHelper.doDelete(Constant.API.cartItem, JSON.toJSON(params).toString(), callback);
    }

    @Override
    public void clearCart(String uId, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        okHttpHelper.doDelete(Constant.API.clearCart, JSON.toJSON(params).toString(), callback);
    }



    @Override
    public void selectShop(String mUid, String id, boolean select, BaseCallback baseCallback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", mUid);
        params.put("cartId", id);
        if (select) {
            params.put("isSelected", 1);
        } else {
            params.put("isSelected", 0);
        }
        Log.e(TAG, "selectShop: " + JSON.toJSON(params).toString());
        okHttpHelper.doPut(Constant.API.selectShop, JSON.toJSON(params).toString(), baseCallback);
    }

    @Override
    public void selectProduct(String uid, List<String> pid, boolean select, BaseCallback baseCallback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uid);
        if (select) {
            params.put("isSelected", 1);
        } else {
            params.put("isSelected", 0);
        }

        params.put("cartItemIdList", pid);
        Log.e(TAG, "selectProduct: " + JSON.toJSON(params).toString());
        okHttpHelper.doPut(Constant.API.selectProduct, JSON.toJSON(params).toString(), baseCallback);
    }
}
