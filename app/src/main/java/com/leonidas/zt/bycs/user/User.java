package com.leonidas.zt.bycs.user;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.leonidas.zt.bycs.app.App;
import com.leonidas.zt.bycs.user.bean.UserInfo;

/**
 * Created by mebee on 2018/2/25.
 * Version: V1.0
 * Description: 用户信息(单列类)
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class User {

    private static final String TAG = "UserInfo";
    private static User mInstance;
    private UserInfo mUserInfo;
    private SharedPreferences mSP;

    private User(){
        mSP = PreferenceManager.getDefaultSharedPreferences(App.getContext());

        getData();
    }

    public static final User getInstance(){
        if (mInstance == null) {
            synchronized (User.class){
                if (mInstance == null) {
                    mInstance = new User();
                }
            }
        }
        return mInstance;
    }

    public UserInfo getmUserInfo() {
        Log.d(TAG, "getmUserInfo: " + mUserInfo.toString());
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo userInfo) {
        Log.d(TAG, "setmUserInfo: " + userInfo.toString());
        this.mUserInfo = userInfo;
        saveData();
    }


    private void saveData(){
        mSP.edit().putString("userinfo", JSON.toJSONString(mUserInfo)).commit();
    }

    private void clearData(){
        mSP.edit().clear();
    }

    private void getData(){
        String data = mSP.getString("userinfo", "");
        Log.d(TAG, "getData: " + data);
        if (data == null) {
            mUserInfo = JSON.parseObject(data, UserInfo.class);
        }
    }
}
