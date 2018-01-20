package com.leonidas.zt.bycs.index.bean;

/**
 * Created by mebee on 2018/1/12.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Comment {
    /**
     * commentId : 1515662984648
     * commentGrade : 2
     * commentContent : 商品评论6
     * commentPicture : 6.jpg
     * userName : 用户2
     * userHead : head2.jpg
     */

    private long commentId;
    private int commentGrade;
    private String commentContent;
    private String commentPicture;
    private String userName;
    private String userHead;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public int getCommentGrade() {
        return commentGrade;
    }

    public void setCommentGrade(int commentGrade) {
        this.commentGrade = commentGrade;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentPicture() {
        return commentPicture;
    }

    public void setCommentPicture(String commentPicture) {
        this.commentPicture = commentPicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
