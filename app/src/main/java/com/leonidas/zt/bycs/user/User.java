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
    private boolean isLogin = false;

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

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        Log.d(TAG, "setUserInfo: " + userInfo.toString());
        this.mUserInfo = userInfo;
        saveData();
    }


    private void saveData(){
        mSP.edit().putString("userinfo", JSON.toJSONString(mUserInfo)).commit();
    }

    public void clearData(){
        mSP.edit().clear();
        mUserInfo = null;
        isLogin = false;
    }

    private void getData(){
        String data = mSP.getString("userinfo", "");
        Log.d(TAG, "getData: " + data);
        if (data != null) {
            mUserInfo = JSON.parseObject(data, UserInfo.class);
        }
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

}
