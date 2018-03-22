package com.leonidas.zt.bycs.basket.normal.bean;

import java.io.Serializable;

/**
 * Created by mebee on 2018/3/19.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class ProductItem implements Serializable {

    /**
     * itemId : 1516362852681795985
     * userId : 2
     * shopId : 1515567033782
     * shopName : 商店名称10
     * productId : 1515575680655
     * productQuantity : 10
     * productName : 商品名称6
     * productPrice : 6
     * discountPrice : 0
     * discountState : 1
     * limitNumber : 6单位
     * productIcon : p.png
     * productDesc : 商品描述6
     * productState : 1
     */

    private String itemId;
    private String userId;
    private String shopId;
    private String shopName;
    private String productId;
    private double productQuantity;
    private String productName;
    private double productPrice;
    private double discountPrice;
    private double discountState;
    private String limitNumber;
    private String productIcon;
    private String productDesc;
    private String productState;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getDiscountState() {
        return discountState;
    }

    public void setDiscountState(double discountState) {
        this.discountState = discountState;
    }

    public String getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(String limitNumber) {
        this.limitNumber = limitNumber;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }
}
