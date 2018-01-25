package com.leonidas.zt.bycs.app.utils;

/**
 * Created by mebee on 2018/1/13.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class Constant {
    public static class API{
        public static String baseUrl = "http://120.78.87.169:8080/";
        public static String getShops = baseUrl + "market/front/shops";
        public static String getComment = baseUrl + "market/front/shop/comments";
        public static String getProducts = baseUrl + "market/front/products";
        public static String getProduct  = baseUrl + "market/front/product";
        public static String images = baseUrl + "market/images/";
        public static String getShop = baseUrl + "market/front/shop";
        public static String getSorts = baseUrl + "market/front/product/categories";
    }
}
