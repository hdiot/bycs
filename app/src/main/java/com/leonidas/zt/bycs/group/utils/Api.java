package com.leonidas.zt.bycs.group.utils;

/**
 * Created by 华强 on 2018/1/24.
 * Version: V1.0
 * Description: 放置Api的常量类
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class Api {
    //服务器的BaseUrl
    public static final String Base = "http://120.78.87.169:8080/";
    //图片的途径的BaseUrl
    public static final String BaseImg = Base + "market/images/";
    //查询批量拼购商品分类
    public static final String QueryGroupPurchaseGoodsClassification = Base + "market/front/groupProduct/categories";
    //查询批量拼购商品
    public static final String QueryGroupMultiPurchaseGoods = Base + "market/front/groupProducts";
    //查询拼购商品的详情
    public static final String QueryGroupPurchaseGoodsDetail = Base + "market/front/groupProduct";
    //获取用户购物车数据
    public static final String QueryUserCart = Base + "market/cartItem";
    //添加（拼购）商品到购物车中
    public static final String AddPgGoodsToCart = Base + "market/pgCartItem";
}
