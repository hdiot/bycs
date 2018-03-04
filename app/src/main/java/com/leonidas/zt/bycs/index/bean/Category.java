package com.leonidas.zt.bycs.index.bean;

import java.io.Serializable;

/**
 * Created by mebee on 2018/1/17.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Category implements Serializable{

    /**
     * categoryId : 1515557064589
     * categoryName : 蔬菜
     * categoryIcon : pc1.png
     */

    private String categoryId;
    private String categoryName;
    private String categoryIcon;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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
