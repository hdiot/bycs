package com.leonidas.zt.bycs.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;
import com.leonidas.zt.bycs.user.bean.UserInfo;
import com.leonidas.zt.bycs.user.ui.LoginBar;
import com.leonidas.zt.bycs.user.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Toolbar mToolbar;
    private LoginBar mLoginBar;
    private Button m2LoginBtn;
    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_login);
        initView();

    }


    private void initView() {
        mToolbar = findViewById(R.id.toolbar_login);
        mLoginBar = findViewById(R.id.login_bar);
        m2LoginBtn = findViewById(R.id.btn_2login);

        initToolbar();
        initLoginBar();
        init2LoginBtn();
    }

    private void init2LoginBtn() {
        m2LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String code = mLoginBar.getCodeTxt();
        if (code.equals("")) {
            Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        /*WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("userPhone", mLoginBar.getTelTxt());
        params.put("smsCode", code);
        params.put("chose", 0);*/

        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("userName", "lisi");
        params.put("userPassword", "a123456");
        params.put("chose", 1);

        mOkHttpHelper.doPost(Constant.API.login,
                JSON.toJSON(params).toString(),
                new BaseCallback<ResMessage<UserInfo>>() {


                    @Override
                    public void OnSuccess(Response response, ResMessage<UserInfo> userResMessage) {
                        Log.d(TAG, "OnSuccess: " + userResMessage.getData().getUserName());
                        User.getInstance().setmUserInfo(userResMessage.getData());
                        test();
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
    }

    private void test() {

        Map<String, String> params = new HashMap<>();
        params.put("user", "1516332510603");

        mOkHttpHelper.doGet(Constant.API.baseUrl + "market/cartItem?userId=1516332510603",
                new BaseCallback<String>() {

                    @Override
                    public void OnSuccess(Response response, String s) {
                        Log.d(TAG, "OnSuccess: " + s);
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
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initLoginBar() {
        mLoginBar.setGetCodeListener(new LoginBar.GetCodeListener() {
            @Override
            public void getCode() {
                doGetCode();
            }
        });
    }

    private void doGetCode() {
        WeakHashMap<String, String> params = new WeakHashMap<>();
        params.put("userPhone", mLoginBar.getTelTxt());
        mOkHttpHelper.doPost(Constant.API.sendCode,
                JSON.toJSON(params).toString(),
                new BaseCallback<ResMessage>() {

                    @Override
                    public void OnSuccess(Response response, ResMessage resMessage) {
                        Log.d(TAG, "OnSuccess: " + resMessage.getCode() + resMessage.getHint());
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
    }
}
