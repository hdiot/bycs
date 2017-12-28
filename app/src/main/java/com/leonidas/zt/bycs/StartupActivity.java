package com.leonidas.zt.bycs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

public class StartupActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        new Handler().postDelayed(new Runnable() {//启动页面 - - 2秒钟后进入主页面
            @Override
            public void run() {
                //在主线程中执行
                //启动主页面
                startActivity(new Intent(StartupActivity.this,StartupActivity.class));
                //关闭当前页面
                finish();
            }
        },2000);
    }
}
