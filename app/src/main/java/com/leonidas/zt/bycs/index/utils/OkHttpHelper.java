package com.leonidas.zt.bycs.index.utils;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by mebee on 2018/1/13.
 * Version: V1.0
 * Description:
 * Others: &#x6682;&#x65e0;
 * ReviseHistory(Author&#x3001;Date&#x3001;RevisePart)&#xff1a; &#x6682;&#x65e0;
 */
public class OkHttpHelper {
    private static final String TAG = "OkHttpHelper";
    private OkHttpClient mClient;
    private Handler mHandler;

    // private Gson mGson;
    public enum HttpMethodType {
        POST,
        GET
    }


    private OkHttpHelper() {
        mClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
        // mGson = new Gson();
    }

    public static OkHttpHelper getInstance() {
        return new OkHttpHelper();
    }

    public void doPost(String url, String json, BaseCallback callback) {
        Request request = buildRequest(url, json, HttpMethodType.POST);
        doRequest(request, callback);
    }

    public void doPost(String url, Map<String, String> param, BaseCallback callback) {
        Request request = buildRequest(url, param);
        doRequest(request, callback);
    }

    public void doGet(String url, BaseCallback callback) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        doRequest(request, callback);
    }

    private Request buildRequest(String url, String json, HttpMethodType methodType) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            MediaType JSON = MediaType.parse("application/json;charset=utf-8");
            RequestBody body = RequestBody.create(JSON, json);

            builder.post(body);
        }
        return builder.build();
    }

    private Request buildRequest(String url, Map<String, String> map) {
        Request.Builder rbuilder = new Request.Builder();
        FormBody.Builder fbuilder = new FormBody.Builder();
        rbuilder.url(url);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            fbuilder.add(entry.getKey(), entry.getValue());
        }
        rbuilder.post(fbuilder.build());
        return rbuilder.build();
    }

    private void doRequest(final Request request, final BaseCallback callback) {
        callbackBeforeRequest(callback, request);
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                e.printStackTrace();
                callbackFailure(callback, request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
                if (response.isSuccessful()) {
                    String resultStr = response.body().string();
                    Log.d(TAG, "onResponse: " + resultStr);

                    Integer code = 0;
                    JSONObject jsonObject = JSONObject.parseObject(resultStr);
                    code = jsonObject.getInteger("code");
                    if (code != 1) {
                        String hint = jsonObject.getString("hint");
                        String data = jsonObject.getString("data");
                        callbackBzError(callback, response, code, hint, data);
                    }

                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        // Object object = mGson.fromJson(resultStr, callback.mType);
                        Object object = JSON.parseObject(resultStr, callback.mType);
                        callbackSuccess(callback, response, object);
                    }
                } else {
                    callbackError(callback, response, response.code(), null);
                    Log.d(TAG, "onResponse: " + response.code() + response.body().string());
                }
            }
        });
    }

    private void callbackBzError(final BaseCallback callback,
                                 final Response response,
                                 final Integer code,
                                 final String hint,
                                 final String data) {
        if (callback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onBzError(response, code, hint, data);
                }
            });
        }

    }

    private void callbackSuccess(final BaseCallback callback,
                                 final Response response,
                                 final Object object) {
        if (callback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.OnSuccess(response, object);
                }
            });
        }

    }

    private void callbackError(final BaseCallback callback,
                               final Response response,
                               final int errCode,
                               final Exception e) {
        if (callback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onError(response, errCode, e);
                }
            });
        }
    }

    private void callbackBeforeRequest(final BaseCallback callback,
                                       final Request request) {
        if (callback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onRequestBefore(request);
                }
            });
        }
    }

    private void callbackFailure(final BaseCallback callback,
                                 final Request request,
                                 final Exception e) {
        if (callback != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(request, (IOException) e);
                }
            });
        }
    }
}
