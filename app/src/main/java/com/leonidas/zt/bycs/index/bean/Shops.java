package com.leonidas.zt.bycs.index.bean;

import java.util.List;

/**
 * Created by mebee on 2018/1/13.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Shops {
    /**
     * pageNum : 1
     * pageSize : 10
     * totalPages : 9
     * isFirstPage : true
     * isLastPage : false
     * list : [{"shopId":1515567851722,"shopName":"商店名称22","shopAddress":"商店地址22","shopPhone":"商店电话22","shopDesc":"商店描述22","shopSale":22,"shopGrade":1,"limitPrice":22,"sendPrice":22,"workTime":24532,"shopPictures":[{"pictureId":1515567175160,"picturePath":2}]}]
     */

    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private List<Shop> list;

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

    public List<Shop> getList() {
        return list;
    }

    public void setList(List<Shop> list) {
        this.list = list;
    }


}
