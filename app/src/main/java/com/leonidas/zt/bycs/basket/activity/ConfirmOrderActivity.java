package com.leonidas.zt.bycs.basket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.adapter.OrderContentRecyclerViewAdapter;
import com.leonidas.zt.bycs.basket.vo.GoodsOfGroupPurchaseCartVO;
import com.leonidas.zt.bycs.basket.vo.UserAllReceiveAddressVO;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

import static com.leonidas.zt.bycs.basket.fragment.GroupPurchaseBasketFragment.Tag_GoodsTotalPrice;
import static com.leonidas.zt.bycs.basket.fragment.GroupPurchaseBasketFragment.Tag_SelectedCartOfGoodsList;

/**
 * Created by 华强 on 2018/3/22.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class ConfirmOrderActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "ConfirmOrderActivity";
    public static int RequestCode_SelectedAddress = 1; //选择地址的请求码
    public static final int ALI_PAY = 0; //支付宝支付
    public static final int WE_CHAT_PAY = 1; //微信支付

    private LinearLayout llDeliveryAddress; //修改收货地址
    private TextView tvTakeDeliveryName; //收货人姓名
    private TextView tvTakeDeliveryPhoneNum; //收货电话
    private TextView tvTakeDeliveryAddress; //收货地址

    private RadioGroup rgPayWay; //支付方式
    private RadioButton rbAlipay; //支付宝支付
    private RadioButton rbWeChatPay; //微信支付

    private RecyclerView rvOrderContent; //订单内容
    private TextView tvTotalMoney; //总价
    private Button btGoGroupPurchase; //提交订单

    //商品信息
    private List<GoodsOfGroupPurchaseCartVO.DataBean> OrderOfGoodsList;
    //地址信息
    private Long RecevingId; //收货地址ID
    private String TakeDeliveryName; //收货人姓名
    private String TakeDeliveryPhoneNum; //收货人电话
    private String TakeDeliveryAddress; //收货地址
    private int PayWay;//支付方式

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        findViews();
        initData();
    }

    private void initData() {
        rgPayWay.check(R.id.rb_alipay); //选择默认支付方式
        initDefaultAddress("1516332510603"); //获取默认收货地址信息
        initOrderOfGoodsList(); //获取订单中的商品信息
    }

    private void initOrderOfGoodsList() {
        //获取订单商品信息
        OrderOfGoodsList = (List<GoodsOfGroupPurchaseCartVO.DataBean>) getIntent()
                .getBundleExtra(Tag_SelectedCartOfGoodsList)
                .getSerializable(Tag_SelectedCartOfGoodsList);
        tvTotalMoney.setText(getIntent().getStringExtra(Tag_GoodsTotalPrice));
        rvOrderContent.setAdapter(new OrderContentRecyclerViewAdapter(ConfirmOrderActivity.this.getBaseContext()
                , OrderOfGoodsList));
        rvOrderContent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void findViews() {
        llDeliveryAddress = findViewById(R.id.ll_delivery_address);
        tvTakeDeliveryName = findViewById(R.id.tv_take_delivery_name);
        tvTakeDeliveryPhoneNum = findViewById(R.id.tv_take_delivery_phone_num);
        tvTakeDeliveryAddress = findViewById(R.id.tv_take_delivery_address);

        rgPayWay = findViewById(R.id.rg_pay_way);
        rbAlipay = findViewById(R.id.rb_alipay);
        rbWeChatPay = findViewById(R.id.rb_we_chat_pay);

        rvOrderContent = findViewById(R.id.rv_order_content);
        tvTotalMoney = findViewById(R.id.tv_total_money);
        btGoGroupPurchase = findViewById(R.id.bt_go_pg);

        rgPayWay.setOnCheckedChangeListener(this);
        btGoGroupPurchase.setOnClickListener(this);
        llDeliveryAddress.setOnClickListener(this);

    }

    /**加载默认地址信息
     *
     * @param UserId
     */
    private void initDefaultAddress(String UserId) {
        OkHttpHelper.getInstance().doGet(Api.QueryUserDefaultDeliveryAddress + "/" + UserId,
                new BaseCallback<String>() {
                    @Override
                    public void OnSuccess(Response response, String s) {
                            Log.e("onResponse", s);
                        JSONObject mJO = JSONObject.parseObject(s);
                        int ResultCode = mJO.getInteger(ApiParamKey.ResultCode);
                        if (ResultCode != 1) {//删除失败
                            Log.e("hahaha", "数据获取失败");
                            Toast.makeText(ConfirmOrderActivity.this, "数据获取失败!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONObject Data = mJO.getJSONObject(ApiParamKey.Data);

                        if (Data == null) {
                            Toast.makeText(ConfirmOrderActivity.this, "无默认收货地址，请自行添加!", Toast.LENGTH_SHORT).show();
                        }

                        RecevingId = Data.getLong("addressId"); //地址ID
                        TakeDeliveryName = Data.getString("consignee"); //收货人
                        TakeDeliveryPhoneNum = Data.getString("phone"); //电话号码
                        TakeDeliveryAddress = Data.getString("userAddress"); //收货地址
                        tvTakeDeliveryName.setText(TakeDeliveryName);
                        tvTakeDeliveryPhoneNum.setText(TakeDeliveryPhoneNum + "");
                        tvTakeDeliveryAddress.setText(TakeDeliveryAddress);
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

                    }

                    @Override
                    public void onBzError(Response response, int code, String hint, String data) {

                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v == llDeliveryAddress) {
            SelectedAddress();
        } else {
            if (v == btGoGroupPurchase) {
                GoGroupPurchase(RecevingId, OrderOfGoodsList);
            }
        }

    }

    private void GoGroupPurchase(Long AddressId,List<GoodsOfGroupPurchaseCartVO.DataBean> CartItemList) {
        //打开确认订单Activity
        Intent intent = new Intent(this, GroupPurchasePayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag_SelectedCartOfGoodsList, (Serializable) CartItemList);
        intent.putExtra(Tag_SelectedCartOfGoodsList, bundle);//被选中的商品
        intent.putExtra(GroupPurchasePayActivity.Tag_AddressId, AddressId);
        intent.putExtra(Tag_GoodsTotalPrice, tvTotalMoney.getText().toString().trim());
        this.startActivity(intent);
    }

    /**
     * 检查所有跟提交订单相关的参数的合法性
     */
    private boolean CheckAllParamLegality() {
        if (RecevingId == null || RecevingId.equals("")) {
            Toast.makeText(this, "请添加收货地址!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 选择收货地址
     */
    private void SelectedAddress() {
        Intent intent = new Intent(this, SelectTakeDeliveryAddressActivity.class);
        this.startActivityForResult(intent, RequestCode_SelectedAddress);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedID) {
        switch (checkedID) {
            case R.id.rb_alipay:
                PayWay = ALI_PAY;
            case R.id.rb_we_chat_pay:
                PayWay = WE_CHAT_PAY;
            default:
                PayWay = ALI_PAY;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode_SelectedAddress) {//获得修改后的地址信息
            if (resultCode == SelectTakeDeliveryAddressActivity.ResultCode_SelectedAddress) {
                //对返回的数据做处理
                Bundle bundle = data.getBundleExtra(SelectTakeDeliveryAddressActivity.Tag_ReceiveAddress);
                UserAllReceiveAddressVO.DataBean address = (UserAllReceiveAddressVO.DataBean) bundle.getSerializable(SelectTakeDeliveryAddressActivity.Tag_ReceiveAddress);
                RecevingId = address.getAddressId(); //地址ID
                TakeDeliveryName = address.getConsignee(); //收货人
                TakeDeliveryPhoneNum = address.getPhone(); //电话号码
                TakeDeliveryAddress = address.getUserAddress(); //收货地址
                tvTakeDeliveryName.setText(TakeDeliveryName);
                tvTakeDeliveryPhoneNum.setText(TakeDeliveryPhoneNum + "");
                tvTakeDeliveryAddress.setText(TakeDeliveryAddress);

            }
        }

    }

}
