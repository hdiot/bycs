package com.leonidas.zt.bycs.index.bean;

/**
 * Created by mebee on 2018/1/12.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class ResMessage<T> {
    /**
     * code : 1
     * hint : 成功！
     * data : {}
     */

    private int code;
    private String hint;
    private T data;

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

    public T getData() {
        if (data == null) {
            throw new RuntimeException("data is null");
        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
