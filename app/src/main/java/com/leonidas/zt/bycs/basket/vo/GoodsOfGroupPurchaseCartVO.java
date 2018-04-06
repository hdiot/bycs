package com.leonidas.zt.bycs.basket.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 华强 on 2018/1/31.
 * Version: V1.0
 * Description:拼购购物车内商品的VO类
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GoodsOfGroupPurchaseCartVO implements Serializable{


    /**
     * code : 1
     * hint : 成功！
     * data : [{"itemId":"1517638893278342503","userId":1,"productQuantity":3,"productId":25473687528098376,"productName":"商品名称6","productOprice":6.5,"productNprice":6,"productStock":6,"productUnit":"6商品单位","productDesc":"商品描述6","picturePath":"1.jpg"},{"itemId":"1517639760557385656","userId":1,"productQuantity":1,"productId":47279305344041790,"productName":"商品名称10","productOprice":10.5,"productNprice":10,"productStock":10,"productUnit":"10商品单位","productDesc":"商品描述10","picturePath":"1.jpg"}]
     */

    private int code;
    private String hint;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * itemId : 1517638893278342503
         * userId : 1
         * productQuantity : 3
         * productId : 25473687528098376
         * productName : 商品名称6
         * productOprice : 6.5
         * productNprice : 6
         * productStock : 6
         * productUnit : 6商品单位
         * productDesc : 商品描述6
         * picturePath : 1.jpg
         */

        private String itemId;
        private int userId;
        private int productQuantity;
        private long productId;
        private String productName;
        private double productOprice;
        private int productNprice;
        private int productStock;
        private String productUnit;
        private String productDesc;
        private String picturePath;
        private boolean isSelected = true;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getProductOprice() {
            return productOprice;
        }

        public void setProductOprice(double productOprice) {
            this.productOprice = productOprice;
        }

        public int getProductNprice() {
            return productNprice;
        }

        public void setProductNprice(int productNprice) {
            this.productNprice = productNprice;
        }

        public int getProductStock() {
            return productStock;
        }

        public void setProductStock(int productStock) {
            this.productStock = productStock;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }

        public String getPicturePath() {
            return picturePath;
        }

        public void setPicturePath(String picturePath) {
            this.picturePath = picturePath;
        }
    }
}
