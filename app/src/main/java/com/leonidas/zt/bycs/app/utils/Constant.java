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
        public static final String baseUrl = "http://120.78.87.169:8080/";
        public static final String getShops = baseUrl + "market/front/shops";
        public static final String getComment = baseUrl + "market/front/shop/comments";
        public static final String getProducts = baseUrl + "market/front/products";
        public static final String getProduct  = baseUrl + "market/front/product";
        public static final String images = baseUrl + "market/images/";
        public static final String getShop = baseUrl + "market/front/shop";
        public static final String getSorts = baseUrl + "market/front/product/categories";
        public static final String sendCode = baseUrl + "market/user/sendMessage";
        public static final String login = baseUrl + "market/user/login";
        public static final String getCartItem = baseUrl + "market/cartItem/page?";
        public static final String getOrders = baseUrl + "market/order/page";
        public static final String getAddrs = baseUrl + "market/user/oneUserAndAddress/";
        public static final String addToCart = baseUrl + "market/cartItem";
        public static final String changItem = baseUrl + "market/cartItem";
        public static final String clearCart = baseUrl + "market/cartItem/empty";
    }
}
