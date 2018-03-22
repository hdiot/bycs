package com.leonidas.zt.bycs.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mebee on 2018/3/15.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Adderesses implements Serializable {

    /**
     * userId : 1516332510603
     * userName : lisi
     * userPhone : 18934043766
     * userAddresses : [{"addressId":1516369022659,"userAddress":"广东海洋大学科技楼1","phone":"18934043756","houseNumber":"科技楼418","isDefault":true,"consignee":"小豪1"},{"addressId":1516369139742,"userAddress":"广东海洋大学科技楼2","phone":"18934043757","houseNumber":"科技楼418","isDefault":false,"consignee":"小豪2"},{"addressId":1516369205213,"userAddress":"广东海洋大学科技楼","phone":"18934043755","houseNumber":"科技楼418","isDefault":false,"consignee":"小豪"},{"addressId":1516369380410,"userAddress":"广东海洋大学科技楼6","phone":"18934043758","houseNumber":"科技楼418","isDefault":false,"consignee":"小志8"}]
     */

    private String userId;
    private String userName;
    private String userPhone;
    private List<Address> userAddresses;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public List<Address> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<Address> userAddresses) {
        this.userAddresses = userAddresses;
    }

}
