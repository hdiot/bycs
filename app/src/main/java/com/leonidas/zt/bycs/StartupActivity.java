package com.leonidas.zt.bycs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.leonidas.zt.bycs.app.App;
import com.leonidas.zt.bycs.app.acitvity.MainActivity;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;
import com.leonidas.zt.bycs.user.User;
import com.leonidas.zt.bycs.user.bean.Adderesses;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class StartupActivity extends FragmentActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        App.getsInstance();
        checkLogin();
        new Handler().postDelayed(new Runnable() {//启动页面 - - 2秒钟后进入主页面
            @Override
            public void run() {
                //在主线程中执行
                //启动主页面
                startActivity(new Intent(StartupActivity.this,MainActivity.class));
                //关闭当前页面
                finish();
            }
        },2000);
    }

   public void checkLogin(){

       if (User.getInstance().getUserInfo() != null) {

           final String uId = User.getInstance().getUserInfo().userId;
           OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

           okHttpHelper.doGet(Constant.API.getAddrs + uId,new BaseCallback<ResMessage<Adderesses>>() {

               @Override
               public void OnSuccess(Response response, ResMessage<Adderesses> adderessesResMessage) {
                   if (adderessesResMessage.getData() != null) {
                       User.getInstance().setLogin(true);
                   }
               }

               @Override
               public void onError(Response response, int errCode, Exception e) {

               }

               @Override
               public void onRequestBefore(Request request) {

               }

               @Override
               public void onFailure(Request request, IOException e) {

               }

               @Override
               public void onBzError(Response response, int code, String hint, String data) {

               }
           });
       } else {
           // TODO: 2018/3/15
           // 未登录处理

           return;
       }
   }

}
