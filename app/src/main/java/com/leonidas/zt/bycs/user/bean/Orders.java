package com.leonidas.zt.bycs.user.bean;

import android.util.Log;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mebee on 2018/3/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Orders implements Serializable {
    /**
     * pageNum : 1
     * pageSize : 2
     * size : 1
     * startRow : 1
     * endRow : 1
     * total : 1
     * pages : 1
     * list : [{"orderId":"1517752959089720356","shopId":1515567140661,"shopName":"商店名称6","userId":1,"userName":"aaa","userPhone":"13420111111","userAddress":"广东省","productAmount":4,"sendPrice":6,"orderAmount":10,"orderStatus":0,"payStatus":0,"deliveryStatus":0,"commentStatus":0,"payWay":null,"payAcount":null,"deliveryTime":null,"arriveTime":null,"receiveTime":null,"commentId":null,"createTime":1517752959000,"updateTime":1517752959000,"orderRemark":null,"orderItemList":[{"itemId":"1517752959135483238","orderId":"1517752959089720356","productId":1515575600631,"productName":"商品名称2","productPrice":2,"productQuantity":2,"productUnit":"2单位","productDesc":"商品描述2","productIcon":"p.png"}]}]
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 8
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     * firstPage : 1
     * lastPage : 1
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int firstPage;
    private int lastPage;
    @JSONField(alternateNames = "list")
    private List<Order> list;
    private List<Integer> navigatepageNums;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    @JSONField(alternateNames = "firstPage")
    public int getFirstPage() {
        return firstPage;
    }
    @JSONField(alternateNames = "firstPage")
    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }
    @JSONField(alternateNames = "lastPage")
    public int getLastPage() {
        return lastPage;
    }
    @JSONField(alternateNames = "lastPage")
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<Order> getList() {
        for (Order order : list) {
            Log.e("getList", "order: " + order.toString());
        }
        return list;
    }

    public void setList(List<Order> list) {

        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }


}
