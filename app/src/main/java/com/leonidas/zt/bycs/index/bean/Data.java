package com.leonidas.zt.bycs.index.bean;

/**
 * Created by mebee on 2018/1/13.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Data<T> {
    T shops;
    T products;
    T shop;
    T productCategories;
    T shopComments;

    public T getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(T productCategories) {
        this.productCategories = productCategories;
    }

    public T getShop() {
        return shop;
    }

    public void setShop(T shop) {
        this.shop = shop;
    }

    public T getProducts() {
        return products;
    }

    public void setProducts(T products) {
        this.products = products;
    }

    public T getShops() {
        return shops;
    }

    public void setShops(T shops) {
        this.shops = shops;
    }
    public T getShopComments() {
        return shopComments;
    }

    public void setShopComments(T shopComments) {
        this.shopComments = shopComments;
    }

}
