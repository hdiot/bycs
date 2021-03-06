package com.leonidas.zt.bycs.index.utils;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leonidas.zt.bycs.app.App;
import com.leonidas.zt.bycs.app.ui.loader.MebeeLoader;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private static final OkHttpHelper sInstance = null;
    private OkHttpClient mClient;
    private Handler mHandler;

    // private Gson mGson;
    public enum HttpMethodType {
        POST,
        PUT,
        DELETE,
        GET
    }




    private OkHttpHelper() {
        mClient = new OkHttpClient.Builder()
                .cookieJar(CookieManager.getInstance(App.getContext()))
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
        // mGson = new Gson();
    }

    public static OkHttpHelper getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpHelper.class) {
                if (sInstance == null) {
                    return new OkHttpHelper();
                }
            }
        }
        return sInstance;
    }

    public void doPost(String url, String json, BaseCallback callback) {
        Request request = buildRequest(url, json, HttpMethodType.POST);
        doRequest(request, callback);
    }

    public void doPut(String url, String json, BaseCallback callback){
        Request request = buildRequest(url, json, HttpMethodType.PUT);
        doRequest(request, callback);
    }

    public void doPost(String url, Map<String, String> param, BaseCallback callback) {
        Request request = buildRequest(url, param, HttpMethodType.POST);
        doRequest(request, callback);
    }

    public void doGet(String url, BaseCallback callback) {
        Request request = buildRequest(url, "", HttpMethodType.GET);
        doRequest(request, callback);
    }

    public void doGet(String url, Map<String, String> params, BaseCallback callback) {
        Request request = buildRequest(url, params, HttpMethodType.GET);
        doRequest(request, callback);
    }


    public void doDelete(String url, String json, BaseCallback callback) {
        Request request = buildRequest(url, json, HttpMethodType.DELETE);
        doRequest(request, callback);
    }


    private Request buildRequest(String url, String json, HttpMethodType methodType) {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        switch (methodType) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(body);
                break;
            case DELETE:
                builder.delete(body);
                break;
            case PUT:
                builder.put(body);
                break;
        }

        return builder.build();
    }

    private Request buildRequest(String url, Map<String, String> map, HttpMethodType methodType) {
        Request.Builder rbuilder = new Request.Builder();
        rbuilder.url(url);
        FormBody.Builder fbuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.e(TAG, "buildRequest: " + entry.getKey() + entry.getValue());
            fbuilder.add(entry.getKey(), entry.getValue());
        }

        switch (methodType) {
            case POST:
                rbuilder.post(fbuilder.build());
                break;
            case GET:
                rbuilder.put(fbuilder.build())
                        .get();
                break;
            case PUT:
                rbuilder.put(fbuilder.build());
                break;
            case DELETE:
                break;
            default:

        }

        return rbuilder.build();
    }

    private void doRequest(final Request request, final BaseCallback callback) {

        Call call = mClient.newCall(request);

        callbackBeforeRequest(callback, request);
        // callbackBeforeRequest(callback, request, Call call);

        call.enqueue(new Callback() {
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
                        return;
                    }

                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, resultStr);
                    } else {
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
