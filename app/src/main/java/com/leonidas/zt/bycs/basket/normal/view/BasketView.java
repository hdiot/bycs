package com.leonidas.zt.bycs.basket.normal.view;

import com.leonidas.zt.bycs.basket.normal.bean.ShopItem;

import java.util.List;

/**
 * Created by mebee on 2018/3/17.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public interface BasketView {
    void showProgress();
    void hideProgress();
    void loadMore(List<ShopItem> list);
    void refresh(List<ShopItem> list);
    void pullOrder();
    void deleteBasketItem();
    void showNoMoreData();
    void refreshFinish();
    void loadMoreFinish();
    void onError(CharSequence s, int t);
    void selectShopFail();
    void selectShopSucceed();
    void selectProductFail();
    void selectProductSucceed();
}
