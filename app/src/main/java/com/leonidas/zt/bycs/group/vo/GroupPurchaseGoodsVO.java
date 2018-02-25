package com.leonidas.zt.bycs.group.vo;

import java.util.List;

/**
 * Created by 华强 on 2018/1/28.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseGoodsVO {

    /**
     * code : 1
     * hint : 成功！
     * data : {"groupProduct":{"productId":25473687528098376,"productName":"商品名称6","productOprice":6.5,"productNprice":6,"productStock":6,"productUnit":"6商品单位","productDesc":"商品描述6","sendPrice":6,"productPictures":[{"picturePath":"p.png"}]}}
     */

    private int code;
    private String hint;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * groupProduct : {"productId":25473687528098376,"productName":"商品名称6","productOprice":6.5,"productNprice":6,"productStock":6,"productUnit":"6商品单位","productDesc":"商品描述6","sendPrice":6,"productPictures":[{"picturePath":"p.png"}]}
         */

        private GroupProductBean groupProduct;

        public GroupProductBean getGroupProduct() {
            return groupProduct;
        }

        public void setGroupProduct(GroupProductBean groupProduct) {
            this.groupProduct = groupProduct;
        }

        public static class GroupProductBean {
            /**
             * productId : 25473687528098376
             * productName : 商品名称6
             * productOprice : 6.5
             * productNprice : 6
             * productStock : 6
             * productUnit : 6商品单位
             * productDesc : 商品描述6
             * sendPrice : 6
             * productPictures : [{"picturePath":"p.png"}]
             */

            private long productId;
            private String productName;
            private double productOprice;
            private int productNprice;
            private int productStock;
            private String productUnit;
            private String productDesc;
            private int sendPrice;
            private List<ProductPicturesBean> productPictures;

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

            public int getSendPrice() {
                return sendPrice;
            }

            public void setSendPrice(int sendPrice) {
                this.sendPrice = sendPrice;
            }

            public List<ProductPicturesBean> getProductPictures() {
                return productPictures;
            }

            public void setProductPictures(List<ProductPicturesBean> productPictures) {
                this.productPictures = productPictures;
            }

            public static class ProductPicturesBean {
                /**
                 * picturePath : p.png
                 */

                private String picturePath;

                public String getPicturePath() {
                    return picturePath;
                }

                public void setPicturePath(String picturePath) {
                    this.picturePath = picturePath;
                }
            }
        }
    }
}
