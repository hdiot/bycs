package com.leonidas.zt.bycs.basket.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 华强 on 2018/3/23.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class UserAllReceiveAddressVO implements Serializable{

    /**
     * code : 1
     * hint : 成功！
     * data : [{"addressId":1516369022659,"userAddress":"广东海洋大学科技楼1","phone":"18934043756","userId":1516332510603,"houseNumber":"科技楼418","isDefault":true,"consignee":"小豪1"},{"addressId":1516369139742,"userAddress":"广东海洋大学科技楼2","phone":"18934043757","userId":1516332510603,"houseNumber":"科技楼418","isDefault":false,"consignee":"小豪2"},{"addressId":1516369205213,"userAddress":"广东海洋大学科技楼","phone":"18934043755","userId":1516332510603,"houseNumber":"科技楼418","isDefault":false,"consignee":"小豪"},{"addressId":1516369380410,"userAddress":"广东海洋大学科技楼6","phone":"18934043758","userId":1516332510603,"houseNumber":"科技楼418","isDefault":false,"consignee":"小志8"}]
     */

    private int code;
    private String hint;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * addressId : 1516369022659
         * userAddress : 广东海洋大学科技楼1
         * phone : 18934043756
         * userId : 1516332510603
         * houseNumber : 科技楼418
         * isDefault : true
         * consignee : 小豪1
         */

        private long addressId;
        private String userAddress;
        private String phone;
        private long userId;
        private String houseNumber;
        private boolean isDefault;
        private String consignee;

        public long getAddressId() {
            return addressId;
        }

        public void setAddressId(long addressId) {
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

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
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
}
