package com.leonidas.zt.bycs.index.bean;

import java.util.List;

/**
 * Created by mebee on 2018/1/12.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class ShopComments {
    /**
     * pageNum : 1
     * pageSize : 10
     * totalPages : 1
     * isFirstPage : true
     * isLastPage : true
     * list : [{"commentId":1515662984648,"commentGrade":2,"commentContent":"商品评论6","commentPicture":"6.jpg","userName":"用户2","userHead":"head2.jpg"}]
     */

    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private List<Comment> list;

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

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }


}
