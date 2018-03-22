package com.leonidas.zt.bycs.basket.normal.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

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

    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    @Override
    public void addToCart(String uId, String pId, int quantity, int category, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        params.put("productId", pId);
        params.put("productQuantity", quantity);
        params.put("itemCategory", category);
        okHttpHelper.doPost(Constant.API.addToCart, JSON.toJSON(params).toString(), callback);

    }

    @Override
    public void getCart(String uId, int pageNum, int pageSize, BaseCallback callback) {
        Log.e("getCart", "----------" );
        String params = "userId=" + uId + "&pageNum=" + pageNum + "&pageSzie=" + pageSize;
        okHttpHelper.doGet(Constant.API.getCartItem + params, callback);
    }

    @Override
    public void changeItem(String uId, String itemId, int quantity, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        params.put("cartItemId", itemId);
        params.put("quantity", quantity);
        okHttpHelper.doPut(Constant.API.changItem, JSON.toJSON(params).toString(), callback);
    }

    @Override
    public void deleteItem(String uId, String itemId, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        params.put("cartItemId", itemId);
        okHttpHelper.doDelete(Constant.API.changItem, JSON.toJSON(params).toString(), callback);
    }

    @Override
    public void clearCart(String uId, BaseCallback callback) {
        Map<String, Object> params = new WeakHashMap<>();
        params.put("userId", uId);
        okHttpHelper.doDelete(Constant.API.clearCart, JSON.toJSON(params).toString(), callback);
    }
}
