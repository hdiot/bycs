package com.leonidas.zt.bycs.basket.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.adapter.ReceiveAddressRvAdapter;
import com.leonidas.zt.bycs.basket.vo.UserAllReceiveAddressVO;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class SelectTakeDeliveryAddressActivity extends AppCompatActivity {

    public static String Tag_ReceiveAddress = "ReceiveAddress"; //用于让ConfirmOrderActivity获取收货地址对象的Key值
    public static int ResultCode_SelectedAddress = 1; //选择收货地址的返回码
    private RecyclerView rvAddress; //收货地址
    //UserAllReceiveAddressVO.DataBean SelectedAddress; //被选择的收货地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_take_delivery_address);
        findViews();
        initAddress(1516332510603L);
    }

    /**
     * 加载收货地址
     */
    private void initAddress(Long UserId) {

        OkHttpHelper.getInstance().doGet(Api.QueryUserAllDeliveryAddress
                        +"/"+UserId,
                new BaseCallback<String>() {
                    @Override
                    public void OnSuccess(Response response, String s) {
                        Log.e("进入", "haha");
                        Log.e("onResponse", s);
                        UserAllReceiveAddressVO data = JSON.parseObject(s, UserAllReceiveAddressVO.class);
                        if (data.getData() == null) {//数据出错
                            Toast.makeText(SelectTakeDeliveryAddressActivity.this,
                                    "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (data.getCode() != 1) {//数据获取失败
                            Toast.makeText(SelectTakeDeliveryAddressActivity.this,
                                    "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        rvAddress.setAdapter(new ReceiveAddressRvAdapter(SelectTakeDeliveryAddressActivity.this, data));
                        rvAddress.setLayoutManager(new LinearLayoutManager(SelectTakeDeliveryAddressActivity.this));
                    }

                    @Override
                    public void onError(Response response, int errCode, Exception e) {
                        Log.e("onError", e.toString());
                    }

                    @Override
                    public void onRequestBefore(Request request) {

                    }

                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("onFailure", e.toString());
                    }

                    @Override
                    public void onBzError(Response response, int code, String hint, String data) {
                        Log.e("onBzError", hint);
                    }
                });
    }

    private void findViews() {
        rvAddress = findViewById(R.id.rv_address);
    }
}
