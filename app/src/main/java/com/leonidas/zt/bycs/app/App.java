package com.leonidas.zt.bycs.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by mebee on 2018/2/9.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class App extends Application {
    private static App sInstance;
    private static Context mContext;

    public static final App getsInstance(){
        if (sInstance == null) {
            synchronized (App.class){
                if (sInstance == null) {
                    sInstance = new App();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
