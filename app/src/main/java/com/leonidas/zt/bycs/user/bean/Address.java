package com.leonidas.zt.bycs.user.bean;

import java.io.Serializable;

/**
 * Created by mebee on 2018/3/15.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Address implements Serializable {

    /**
     * addressId : 1516369449435
     * userAddress : 广东海洋大学科技楼4
     * phone : 18934043759
     * userId : 1516332510603
     * houseNumber : 科技楼418
     * isDefault : false
     * consignee : 小豪4
     */

    private String addressId;
    private String userAddress;
    private String phone;
    private String userId;
    private String houseNumber;
    private boolean isDefault;
    private String consignee;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
}
