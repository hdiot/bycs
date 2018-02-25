package com.leonidas.zt.bycs.app.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华强 on 2017/9/2.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class User implements Serializable {

    private static User sInstance;

    //public static final String LOGIN = "Login"; //登录状态
    //public final String LOGOUT = "logout"; //注销状态
    private String userID = "0"; //用户ID
    private String accountNum = "0"; //账号
    private String passWord; //密码
    private String headIconUrl = "http://119.23.33.62:8090/mall/commonHeadImage/user_icon.png"; //用户头像
    private String phoneNum = "0"; //手机号
    private boolean isLogin = false; //用户是否登录(是登录状态，还是注销状态)
    private List<takeDeliveryAddress> takeDeliveryAddressList = new ArrayList<>(); //存储用户收货地址信息

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    /**
     * 获取用户信息单例类
     *
     * @return UserInfo
     */
    public static User getInstance() {
        if (sInstance == null) {
            sInstance = new User();
        }
        return sInstance;
    }

    private User() {

    }

    /**
     * 收货地址
     */
    public class takeDeliveryAddress {

    }

}
