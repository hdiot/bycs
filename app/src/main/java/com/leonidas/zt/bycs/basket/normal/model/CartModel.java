package com.leonidas.zt.bycs.basket.normal.model;

import com.leonidas.zt.bycs.index.utils.BaseCallback;

import java.util.List;

/**
 * Created by mebee on 2018/3/17.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public interface CartModel {
    void addToCart(String uId, String pId, int quantity, int category, BaseCallback callback);
    void getCart(String uId, BaseCallback callback);
    void changeItem(String uId, String itemId, int quantity, BaseCallback callback);
    void deleteItem(String uId, String itemId, BaseCallback callback);
    void clearCart(String uId, BaseCallback callback);
    void selectShop(String mUid, String sid, boolean select, BaseCallback baseCallback);
    void selectProduct(String mUid, List<String> pid, boolean select, BaseCallback baseCallback);
}
