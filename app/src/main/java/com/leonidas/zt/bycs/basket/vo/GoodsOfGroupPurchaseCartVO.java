package com.leonidas.zt.bycs.basket.vo;

import java.util.List;

/**
 * Created by 华强 on 2018/1/31.
 * Version: V1.0
 * Description:拼购购物车内商品的VO类
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GoodsOfGroupPurchaseCartVO {

    /**
     * code : 1
     * hint : 成功！
     * data : [{"itemId":"1516410114867757821","userId":2,"shopId":null,"shopName":null,"productId":1515575798274,"productQuantity":5,"itemCategory":1,"productName":"商品名称7","productPrice":7,"discountPrice":0,"productIcon":"p.png","productDesc":"商品描述7","productState":"0"}]
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

    public static class DataBean {
        /**
         * itemId : 1516410114867757821
         * userId : 2
         * shopId : null
         * shopName : null
         * productId : 1515575798274
         * productQuantity : 5
         * itemCategory : 1
         * productName : 商品名称7
         * productPrice : 7
         * discountPrice : 0
         * productIcon : p.png
         * productDesc : 商品描述7
         * productState : 0
         */

        private String itemId;
        private int userId;
        private Object shopId;
        private Object shopName;
        private long productId;
        private int productQuantity;
        private int itemCategory;
        private String productName;
        private int productPrice;
        private int discountPrice;
        private String productIcon;
        private String productDesc;
        private String productState;

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

        public Object getShopId() {
            return shopId;
        }

        public void setShopId(Object shopId) {
            this.shopId = shopId;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }

        public int getItemCategory() {
            return itemCategory;
        }

        public void setItemCategory(int itemCategory) {
            this.itemCategory = itemCategory;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public int getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(int discountPrice) {
            this.discountPrice = discountPrice;
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
}
