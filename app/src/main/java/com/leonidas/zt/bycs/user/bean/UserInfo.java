package com.leonidas.zt.bycs.user.bean;

/**
 * Created by mebee on 2018/2/9.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class UserInfo {

    /**
     * userId : 1516332510603
     * userName : lisi
     * userPassword : dc483e80a7a0bd9ef71d8cf973673924
     * userPhone : 18934043766
     * userHeadPath : head4.jpg
     */

    public long userId;
    private String userName;
    private String userPhone;
    private String userPassword;
    private String userHeadPath;

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
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserHeadPath() {
        return userHeadPath;
    }

    public void setUserHeadPath(String userHeadPath) {
        this.userHeadPath = userHeadPath;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "userId：" + userId +
                "，userName：" + userName +
                "，userPassword：" + userPassword +
                "，userPhone：" + userPhone +
                "，userHeadPath：" + userHeadPath;
    }
}
