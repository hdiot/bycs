package com.leonidas.zt.bycs.basket.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.adapter.GroupPurchaseGroupRvAdapter;
import com.leonidas.zt.bycs.basket.vo.GoodsOfGroupPurchaseCartVO;
import com.leonidas.zt.bycs.basket.vo.GroupPurchaseGroupVO;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;
import com.leonidas.zt.bycs.pay.PayResult;
import com.leonidas.zt.bycs.pay.useless.AuthResult;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.leonidas.zt.bycs.basket.fragment.GroupPurchaseBasketFragment.Tag_GoodsTotalPrice;
import static com.leonidas.zt.bycs.basket.fragment.GroupPurchaseBasketFragment.Tag_SelectedCartOfGoodsList;


public class
GroupPurchasePayActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SDK_PAY_FLAG = 1;  //支付宝支付标记
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(GroupPurchasePayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(GroupPurchasePayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(GroupPurchasePayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(GroupPurchasePayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public static final String Tag_AddressId = "AddressId";
    private static final String TAG = "GroupPurchasePay";

    List<GoodsOfGroupPurchaseCartVO.DataBean> OrderOfGoodsList; //订单中的商品
    Long AddressId; //收货地址
    GroupPurchaseGroupVO.DataBean Group = new GroupPurchaseGroupVO.DataBean(); //拼购组

    private LinearLayout llCurrentGroup; //已加入的拼购组
    private XRecyclerView rvGroup;   //拼购组
    private TextView tvTotalMoney;  //总价
    private Button btPay;   //支付按钮
    private ImageView ivSelectedImg;
    private TextView tvSelectedGroupId; //拼购组ID
    private TextView tvSelectedPgStartTimeAndLakePeople; //拼购组创建时间及缺乏人数
    private List<GroupPurchaseGroupVO.DataBean> GroupPurchaseGroupList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_purchase_pay);
        findViews();
        initData();
        rvGroup.setLoadingMoreEnabled(true);
        rvGroup.setPullRefreshEnabled(true);
        rvGroup.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotateMultiple);
        rvGroup.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleMultiple);
        rvGroup.setArrowImageView(R.mipmap.arrow_down);
        rvGroup.setFootViewText("疯狂加载中...","我是有底线的 -_-|");
        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        rvGroup.setAdapter(new GroupPurchaseGroupRvAdapter(this, GroupPurchaseGroupList, Group, llCurrentGroup, ivSelectedImg, tvSelectedPgStartTimeAndLakePeople, tvSelectedGroupId));
        rvGroup.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getGroupPurchaseGroups(0, 0);
            }

            @Override
            public void onLoadMore() {
                rvGroup.loadMoreComplete();
                rvGroup.setNoMore(true);
            }
        });
        btPay.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        OrderOfGoodsList = (List<GoodsOfGroupPurchaseCartVO.DataBean>) intent.getBundleExtra(Tag_SelectedCartOfGoodsList)
                .getSerializable(Tag_SelectedCartOfGoodsList);

        Log.e("listsize", OrderOfGoodsList.size() + "");

        AddressId = intent.getLongExtra(Tag_AddressId, 0);
        tvTotalMoney.setText(intent.getStringExtra(Tag_GoodsTotalPrice));
        getGroupPurchaseGroups(0, 0);
    }

    /**
     * @param groupStatus  拼购组状态 0未完成 1已完成
     * @param activeStatus 拼购组活动状态 0有效 1无效
     */
    private void getGroupPurchaseGroups(int groupStatus, int activeStatus) {
        OkHttpUtils.get()
                .url(Api.QueryGroupPurchaseGroup)
                .addParams(ApiParamKey.GroupStatus, groupStatus + "")
                .addParams(ApiParamKey.ActiveStatus, activeStatus + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                GroupPurchaseGroupVO data = JSON.parseObject(response, GroupPurchaseGroupVO.class);
                rvGroup.refreshComplete();
                if (data.getData() == null) {//数据出错
                    Toast.makeText(GroupPurchasePayActivity.this, "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (data.getCode() != 1) {//数据获取失败
                    Toast.makeText(GroupPurchasePayActivity.this, "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                    return;
                }
                GroupPurchaseGroupList.clear();
                GroupPurchaseGroupList.addAll(data.getData());
                GroupPurchaseGroupRvAdapter adapter = (GroupPurchaseGroupRvAdapter) rvGroup.getAdapter();
                adapter.notifyDataSetChanged();

            }
        });
    }

    private void findViews() {
        llCurrentGroup = findViewById(R.id.ll_current_group);
        rvGroup = findViewById(R.id.rv_group);
        tvTotalMoney = findViewById(R.id.tv_total_money);
        btPay = findViewById(R.id.bt_pay);
        ivSelectedImg = findViewById(R.id.iv_selected_img);
        tvSelectedPgStartTimeAndLakePeople = findViewById(R.id.tv_selected_pg_start_time_and_lake_people);
        tvSelectedGroupId = findViewById(R.id.tv_selected_group_id);
    }

    @Override
    public void onClick(View view) {
        if (view == btPay) {
            List<String> CartItemIdList = new ArrayList<>();
            for (GoodsOfGroupPurchaseCartVO.DataBean data :OrderOfGoodsList ) {
                CartItemIdList.add(data.getItemId());
            }
            CommitNotSignOrderToAppServer(1516332510603L, AddressId, Group.getGroupId(), CartItemIdList);
        }
    }

    /**
     * 提交未签名订单到App的服务器，应该使用同步请求，这时应该有过渡动画，等待服务器返回签名后的订单信息再进行下一步操作。
     */
    private void CommitNotSignOrderToAppServer(Long UserId, Long AddressId
            , String GroupId, List<String> CartItemIdList) {
        if (!CheckAllParamLegality()) { //检查所有参数的合法性
            return;
        }
        //创建订单JSON对象
        JSONObject mJO = new JSONObject();
        mJO.put("userId", UserId); //用户ID
        mJO.put("addressId", AddressId); //地址ID
        mJO.put("groupId", GroupId); //拼购组ID
        mJO.put("cartItemIdList", CartItemIdList); //订单商品列表
        Log.e("jstr", mJO.toJSONString());
        //提示用户请稍后，用户等待时间过长（网络环境很差的情况）时可以取消此次支付。
        Log.e("mjson", mJO.toJSONString());
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("支付").setMessage("请稍后...")
                .setCancelable(false).setNegativeButton("取消支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(GroupPurchasePayActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }).show();
        Log.e("继续？", "yes");
        //2.执行签名请求
        OkHttpHelper.getInstance().doPost(Api.SignGroupPurchaseOder,mJO.toJSONString(),
                new BaseCallback<String>() {
                    @Override
                    public void OnSuccess(Response response, String s) {
                        Log.e("onResponse", s);
                        dialog.dismiss();
                        //Toast.makeText(GroupPurchasePayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //获取OrderInfo
                        JSONObject mJO = JSONObject.parseObject(s);
                        int ResultCode = mJO.getInteger(ApiParamKey.ResultCode);
                        if (ResultCode != 1) {//支付失败
                            Toast.makeText(GroupPurchasePayActivity.this, "支付失败!", Toast.LENGTH_SHORT).show();
                            Log.e("hint", mJO.getString("hint"));
                            return;
                        }

                        final String orderInfo = mJO.getJSONObject("data").getString("orderInfo");
                        //调用payTask方法进行支付
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(GroupPurchasePayActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                Log.i("msp", result.toString());
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                        //提交订单到App服务器
                    }

                    @Override
                    public void onError(Response response, int errCode, Exception e) {
                        Log.e("onError", e.toString());
                        if (e instanceof java.net.SocketTimeoutException) { //请求超时
                            Toast.makeText(GroupPurchasePayActivity.this, "网络繁忙，请稍后再试!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
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

    private boolean CheckAllParamLegality() {
        if (AddressId == null || AddressId.equals("")) {
            Toast.makeText(this, "请添加收货地址!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Group == null) {
            Toast.makeText(this, "请选择拼购组!", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.e("objectId", Group.hashCode() + "");
        return true;
    }

}
