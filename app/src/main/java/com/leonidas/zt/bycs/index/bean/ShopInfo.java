package com.leonidas.zt.bycs.index.bean;

import java.io.Serializable;

/**
 * Author: mebee.
 * Time: 2018/4/1 19:51
 * Description: Null
 */
public class ShopInfo implements Serializable {

    /**
     * shopId : 1515567033782
     * shopName : 商店名称10
     * shopAddress : 商店地址10
     * shopPhone : 商店电话10
     * shopDesc : 商店描述10
     * shopNote : 商店备注10
     * shopIcon : s.png
     * shopSale : 10
     * shopGrade : 2
     * limitPrice : 0.0
     * sendPrice : 10.0
     * workTime : 24531
     */

    private String shopId;
    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String shopDesc;
    private String shopNote;
    private String shopIcon;
    private int shopSale;
    private int shopGrade;
    private double limitPrice;
    private double sendPrice;
    private int workTime;

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

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopNote() {
        return shopNote;
    }

    public void setShopNote(String shopNote) {
        this.shopNote = shopNote;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public int getShopSale() {
        return shopSale;
    }

    public void setShopSale(int shopSale) {
        this.shopSale = shopSale;
    }

    public int getShopGrade() {
        return shopGrade;
    }

    public void setShopGrade(int shopGrade) {
        this.shopGrade = shopGrade;
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

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }
}
