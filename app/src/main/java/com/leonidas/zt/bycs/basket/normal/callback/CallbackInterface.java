package com.leonidas.zt.bycs.basket.normal.callback;

/**
 * Created by mebee on 2018/3/17.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public interface CallbackInterface<T> {
    void onsucceed(T bean);
    void onfail();
}
