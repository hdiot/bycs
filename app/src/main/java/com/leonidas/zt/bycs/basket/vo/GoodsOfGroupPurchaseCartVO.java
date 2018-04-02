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
     * data : [{"itemId":"1517641851681884708","userId":1,"productQuantity":1,"productId":25473687528098376,"productName":"商品名称6","productOprice":6.5,"productNprice":6,"productStock":2,"productUnit":"6商品单位","productDesc":"商品描述6"},{"itemId":"1517794894272564603","userId":1,"productQuantity":2,"productId":25473687528098376,"productName":"商品名称6","productOprice":6.5,"productNprice":6,"productStock":2,"productUnit":"6商品单位","productDesc":"商品描述6"},{"itemId":"1517808825290791988","userId":1,"productQuantity":1,"productId":190544316423948380,"productName":"商品名称16","productOprice":16.5,"productNprice":16,"productStock":16,"productUnit":"16商品单位","productDesc":"商品描述16"},{"itemId":"1517808831958574956","userId":1,"productQuantity":1,"productId":293591630214219900,"productName":"商品名称8","productOprice":8.5,"productNprice":8,"productStock":5,"productUnit":"8商品单位","productDesc":"商品描述8"},{"itemId":"1517808842537675266","userId":1,"productQuantity":1,"productId":296852422888279740,"productName":"商品名称21","productOprice":21.5,"productNprice":21,"productStock":19,"productUnit":"21商品单位","productDesc":"商品描述21"},{"itemId":"1519870190452181397","userId":1,"productQuantity":1,"productId":1764160889292670700,"productName":"商品名称5","productOprice":5.5,"productNprice":5,"productStock":5,"productUnit":"5商品单位","productDesc":"商品描述5"},{"itemId":"1519889656357889825","userId":1,"productQuantity":1,"productId":711628765302901200,"productName":"商品名称26","productOprice":26.5,"productNprice":26,"productStock":26,"productUnit":"26商品单位","productDesc":"商品描述26"}]
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
         * itemId : 1517641851681884708
         * userId : 1
         * productQuantity : 1
         * productId : 25473687528098376
         * productName : 商品名称6
         * productOprice : 6.5
         * productNprice : 6
         * productStock : 2
         * productUnit : 6商品单位
         * productDesc : 商品描述6
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
        private boolean isSelect = true; //是否被选中

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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
    }
}
