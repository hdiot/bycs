package com.leonidas.zt.bycs.index.bean;

import java.util.List;

/**
 * Created by mebee on 2018/1/12.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Products {
    /**
     * pageNum : 1
     * pageSize : 10
     * totalPages : 1
     * isFirstPage : true
     * isLastPage : true
     * list : [{"productId":1515575599578,"productName":"商品名称9","productPrice":9,"productStock":9,"productIcon":"9.jpg","productDesc":"商品描述9","productNote":"商品备注9","limitNumber":"9单位"}]
     */

    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private List<Product> list;

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

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }
}
