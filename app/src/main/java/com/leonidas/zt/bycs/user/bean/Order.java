package com.leonidas.zt.bycs.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mebee on 2018/3/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Order implements Serializable {

    /**
     * orderId : 1519659867740363605
     * shopId : 1515567078469
     * shopName : 商店名称9
     * userId : 1516332510603
     * userName : aaa
     * userPhone : 18934043755
     * userAddress : 广东海洋大学科技楼
     * productAmount : 20
     * sendPrice : 9
     * orderAmount : 29
     * orderStatus : 0
     * payStatus : 0
     * deliveryStatus : 0
     * commentStatus : 0
     * payWay : null
     * payAcount : null
     * deliveryTime : null
     * arriveTime : null
     * receiveTime : null
     * commentId : null
     * createTime : 1519659867000
     * updateTime : 1519659867000
     * orderRemark : null
     * orderItemList : [{"itemId":"1519659867811764078","orderId":"1519659867740363605","productId":1515575722308,"productName":"商品名称5","productPrice":5,"productQuantity":4,"productUnit":"5单位","productDesc":"商品描述5","productIcon":"p.png"}]
     */

    private String orderId;
    private long shopId;
    private String shopName;
    private long userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private double productAmount;
    private double sendPrice;
    private double orderAmount;
    private int orderStatus;
    private int payStatus;
    private int deliveryStatus;
    private int commentStatus;
    private int payWay;
    private String payAcount;
    private String deliveryTime;
    private String arriveTime;
    private String receiveTime;
    private int commentId;
    private long createTime;
    private long updateTime;
    private String orderRemark;

    private List<OrderItem> orderItemList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

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

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public double getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(double sendPrice) {
        this.sendPrice = sendPrice;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public String getPayAcount() {
        return payAcount;
    }

    public void setPayAcount(String payAcount) {
        this.payAcount = payAcount;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String toString(){
        return
                "orderId:" + orderId+
                "|--|shopId:" + shopId+
                "|--|shopName:" + shopName+
                "|--|userId:" + userId+
                "|--|userName:" + userName+
                "|--|userPhone:" + userPhone+
                "|--|userAddress:" + userAddress+
                "|--|productAmount:" + productAmount+
                "|--|sendPrice:" + sendPrice+
                "|--|orderAmount:" + orderAmount+
                "|--|orderStatus:" + orderStatus+
                "|--|payStatus:" + payStatus+
                "|--|deliveryStatus:" + deliveryStatus+
                "|--|commentStatus:" + commentStatus+
                "|--|payWay:" + payWay+
                "|--|payAcount:" + payAcount+
                "|--|deliveryTime:" + deliveryTime+
                "|--|arriveTime:" + arriveTime+
                "|--|receiveTime:" + receiveTime+
                "|--|commentId:" + commentId+
                "|--|createTime:" + createTime+
                "|--|updateTime:" + updateTime+
                "|--|orderRemark:" + orderRemark;
    }
}
