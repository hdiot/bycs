package com.leonidas.zt.bycs.index.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mebee on 2018/1/12.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Shop implements Serializable {
    /**
     * shopId : 1515567851722
     * shopName : 商店名称22
     * shopAddress : 商店地址22
     * shopPhone : 商店电话22
     * shopDesc : 商店描述22
     * shopNote : 商店备注
     * shopSale : 22
     * shopGrade : 1
     * limitPrice : 22
     * sendPrice : 22
     * workTime : 24532
     * shopPictures : [{"pictureId":1515567175160,"picturePath":2}]
     */

    private String shopId;
    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String shopDesc;
    private String shopNote;



    private int shopSale;
    private int shopGrade;
    private double limitPrice;
    private double sendPrice;
    private int workTime;
    private String sellerName;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    private List<ShopPicturesBean> shopPictures;

    public String toString(){
        return "shopId:" + shopId +
                "\nshopName:" + shopName+
                "\nshopPhone:" + shopPhone+
                "\nshopDesc:" + shopDesc+
                "\nshopNode:" + shopNote +
                "\nshopSale:" + shopSale+
                "\nshopGrade:" + shopGrade+
                "\nlimitPrice:" + limitPrice+
                "\nsendPrice:" + sendPrice+
                "\nworkTime:" + workTime;
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

    public List<ShopPicturesBean> getShopPictures() {
        return shopPictures;
    }

    public void setShopPictures(List<ShopPicturesBean> shopPictures) {
        this.shopPictures = shopPictures;
    }


    public static class ShopPicturesBean implements Serializable {
        /**
         * pictureId : 1515567175160
         * picturePath : 2
         */

        private long pictureId;
        private String picturePath;

        public long getPictureId() {
            return pictureId;
        }

        public void setPictureId(long pictureId) {
            this.pictureId = pictureId;
        }

        public String getPicturePath() {
            return picturePath;
        }

        public void setPicturePath(String picturePath) {
            this.picturePath = picturePath;
        }
    }
}

