package com.leonidas.zt.bycs.user.bean;

/**
 * Created by mebee on 2018/2/9.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class User {

    /**
     * userId : 1516331501268
     * userName : qjrBem128895
     * userPhone : 18934043766
     */

    private long userId;
    private String userName;
    private String userPhone;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
