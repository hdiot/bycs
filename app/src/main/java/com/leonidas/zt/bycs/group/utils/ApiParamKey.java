package com.leonidas.zt.bycs.group.utils;

/**
 * Created by 华强 on 2018/1/24.
 * Version: V1.0
 * Description:Api参数的键常量类
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class ApiParamKey {
    //返回码
    public static final String ResultCode = "code";
    //当前页码
    public static final String PageNum = "pageNum";
    //页面大小（每页数据条数）
    public static final String PageSize = "pageSize";
    //分类ID
    public static final String CategoryId = "categoryId";
    //商品Id
    public static final String ProductId = "productId";
    //用户ID
    public static final String UserId = "userId";
    //分类
    public static final String Category = "category";
    //商品数量
    public static final String ProductQuantity = "productQuantity";
    //提示
    public static final String Hint = "hint";
    //购物车ItemId
    public static final String CartItemId = "cartItemId";
    //数据
    public static final String Data = "data";
    //拼购组限制人数
    public static String GroupPurchaseTotalPeople = "groupNum";
    //组状态  0 未完成   1已完成
    public static String GroupStatus;
    //组活动状态 0 组有效   1 组无效
    public static String ActiveStatus;
}

