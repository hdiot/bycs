package com.leonidas.zt.bycs.basket.normal.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mebee on 2018/3/19.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class ShopItem implements Serializable {

    /**
     * cartId : 1519658923482163738
     * userId : 1516332510603
     * shopId : 1515567078469
     * shopName : 商店名称9
     * shopState : 1
     * limitPrice : 0
     * sendPrice : 9
     * cartItemDTOList : [{"itemId":"1519658923438145696","userId":1516332510603,"shopId":1515567078469,"shopName":"商店名称9","productId":1515575722308,"productQuantity":110,"productName":"商品名称5","productPrice":5,"discountPrice":0,"discountState":0,"limitNumber":"5单位","productIcon":"p.png","productDesc":"商品描述5","productState":"1"}]
     */

    private String cartId;
    private String userId;
    private String shopId;
    private String shopName;
    private int shopState;
    private double limitPrice;
    private double sendPrice;
    private List<ProductItem> cartItemDTOList;

    private int isSelected;

    public int isSelected() {
        return isSelected;
    }

    public void setSelected(int selected) {
        isSelected = selected;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public int getShopState() {
        return shopState;
    }

    public void setShopState(int shopState) {
        this.shopState = shopState;
    }

    public double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public double getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(double sendPrice) {
        this.sendPrice = sendPrice;
    }

    public List<ProductItem> getCartItemDTOList() {
        return cartItemDTOList;
    }

    public void setCartItemDTOList(List<ProductItem> cartItemDTOList) {
        this.cartItemDTOList = cartItemDTOList;
    }

}
