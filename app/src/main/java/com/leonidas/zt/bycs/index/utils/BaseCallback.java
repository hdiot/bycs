package com.leonidas.zt.bycs.index.utils;



import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mebee on 2018/1/13.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public abstract class BaseCallback<T> {
    public Type mType;
    public BaseCallback() {
        this.mType = getSuperClassTypeParameter(getClass());
    }

    static Type getSuperClassTypeParameter(Class<?> subclass) {
        // 得到带有泛型类
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        // 取当前类的泛型
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        Log.e("T", "getSuperClassTypeParameter: " +parameterizedType.getActualTypeArguments()[0].toString() );
        // return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        return parameterizedType.getActualTypeArguments()[0];
    }

    public abstract void OnSuccess(Response response, T t);

    public abstract void onError(Response response, int errCode, Exception e);

    public abstract void onRequestBefore(Request request);

    public abstract void onFailure(Request request, IOException e);

    public abstract void onBzError(Response response,int code, String hint, String data);
}
