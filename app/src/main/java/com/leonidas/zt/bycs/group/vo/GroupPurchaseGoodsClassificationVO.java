package com.leonidas.zt.bycs.group.vo;

import java.util.List;

/**
 * Created by 华强 on 2018/1/24.
 * Version: V1.0
 * Description:拼购商品分类视图类
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseGoodsClassificationVO {

    /**
     * code : 1
     * hint : 成功！
     * data : {"productCategories":{"pageNum":1,"pageSize":8,"totalPages":1,"isFirstPage":true,"isLastPage":true,"list":[{"categoryId":1515557064589,"categoryName":"蔬菜","categoryIcon":"pc1.png"},{"categoryId":1515557258011,"categoryName":"肉类","categoryIcon":"pc2.png"},{"categoryId":1515557309728,"categoryName":"其他","categoryIcon":"pc3.png"},{"categoryId":1515557489486,"categoryName":"海鲜","categoryIcon":"pc4.png"},{"categoryId":1515557684767,"categoryName":"干货","categoryIcon":"pc5.png"},{"categoryId":1515557723921,"categoryName":"包点牛奶","categoryIcon":"pc6.png"},{"categoryId":1515557740606,"categoryName":"调料","categoryIcon":"pc7.png"},{"categoryId":1515557954147,"categoryName":"水果","categoryIcon":"pc8.png"}]}}
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
         * productCategories : {"pageNum":1,"pageSize":8,"totalPages":1,"isFirstPage":true,"isLastPage":true,"list":[{"categoryId":1515557064589,"categoryName":"蔬菜","categoryIcon":"pc1.png"},{"categoryId":1515557258011,"categoryName":"肉类","categoryIcon":"pc2.png"},{"categoryId":1515557309728,"categoryName":"其他","categoryIcon":"pc3.png"},{"categoryId":1515557489486,"categoryName":"海鲜","categoryIcon":"pc4.png"},{"categoryId":1515557684767,"categoryName":"干货","categoryIcon":"pc5.png"},{"categoryId":1515557723921,"categoryName":"包点牛奶","categoryIcon":"pc6.png"},{"categoryId":1515557740606,"categoryName":"调料","categoryIcon":"pc7.png"},{"categoryId":1515557954147,"categoryName":"水果","categoryIcon":"pc8.png"}]}
         */

        private ProductCategoriesBean productCategories;

        public ProductCategoriesBean getProductCategories() {
            return productCategories;
        }

        public void setProductCategories(ProductCategoriesBean productCategories) {
            this.productCategories = productCategories;
        }

        public static class ProductCategoriesBean {
            /**
             * pageNum : 1
             * pageSize : 8
             * totalPages : 1
             * isFirstPage : true
             * isLastPage : true
             * list : [{"categoryId":1515557064589,"categoryName":"蔬菜","categoryIcon":"pc1.png"},{"categoryId":1515557258011,"categoryName":"肉类","categoryIcon":"pc2.png"},{"categoryId":1515557309728,"categoryName":"其他","categoryIcon":"pc3.png"},{"categoryId":1515557489486,"categoryName":"海鲜","categoryIcon":"pc4.png"},{"categoryId":1515557684767,"categoryName":"干货","categoryIcon":"pc5.png"},{"categoryId":1515557723921,"categoryName":"包点牛奶","categoryIcon":"pc6.png"},{"categoryId":1515557740606,"categoryName":"调料","categoryIcon":"pc7.png"},{"categoryId":1515557954147,"categoryName":"水果","categoryIcon":"pc8.png"}]
             */

            private int pageNum;
            private int pageSize;
            private int totalPages;
            private boolean isFirstPage;
            private boolean isLastPage;
            private List<ListBean> list;

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public boolean isIsFirstPage() {
                return isFirstPage;
            }

            public void setIsFirstPage(boolean isFirstPage) {
                this.isFirstPage = isFirstPage;
            }

            public boolean isIsLastPage() {
                return isLastPage;
            }

            public void setIsLastPage(boolean isLastPage) {
                this.isLastPage = isLastPage;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * categoryId : 1515557064589
                 * categoryName : 蔬菜
                 * categoryIcon : pc1.png
                 */

                private long categoryId;
                private String categoryName;
                private String categoryIcon;

                public long getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(long categoryId) {
                    this.categoryId = categoryId;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public String getCategoryIcon() {
                    return categoryIcon;
                }

                public void setCategoryIcon(String categoryIcon) {
                    this.categoryIcon = categoryIcon;
                }
            }
        }
    }
}
