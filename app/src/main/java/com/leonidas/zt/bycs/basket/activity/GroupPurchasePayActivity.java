package com.leonidas.zt.bycs.basket.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.basket.adapter.GroupPurchaseGroupRvAdapter;
import com.leonidas.zt.bycs.basket.vo.GoodsOfGroupPurchaseCartVO;
import com.leonidas.zt.bycs.basket.vo.GroupPurchaseGroupVO;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

import static com.leonidas.zt.bycs.basket.fragment.GroupPurchaseBasketFragment.Tag_GoodsTotalPrice;
import static com.leonidas.zt.bycs.basket.fragment.GroupPurchaseBasketFragment.Tag_SelectedCartOfGoodsList;


public class GroupPurchasePayActivity extends AppCompatActivity implements View.OnClickListener {

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
    private TextView tvSelectedPgStartTimeAndLakePeople;
    private List<GroupPurchaseGroupVO.DataBean> GroupPurchaseGroupList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_purchase_pay);
        findViews();
        initData();
        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        rvGroup.setAdapter(new GroupPurchaseGroupRvAdapter(this, GroupPurchaseGroupList, Group,llCurrentGroup,ivSelectedImg,tvSelectedPgStartTimeAndLakePeople));
        btPay.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        OrderOfGoodsList = (List<GoodsOfGroupPurchaseCartVO.DataBean>) intent.getBundleExtra(Tag_SelectedCartOfGoodsList)
                .getSerializable(Tag_SelectedCartOfGoodsList);
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

                if (data.getData() == null) {//数据出错
                    Toast.makeText(GroupPurchasePayActivity.this, "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (data.getCode() != 1) {//数据获取失败
                    Toast.makeText(GroupPurchasePayActivity.this, "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                    return;
                }
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
    }

    @Override
    public void onClick(View view) {
        if (view == btPay) {
            CommitNotSignOrderToAppServer(1516332510603L, AddressId, OrderOfGoodsList);
        }
    }

    /**
     * 提交未签名订单到App的服务器，应该使用同步请求，这时应该有过渡动画，等待服务器返回签名后的订单信息再进行下一步操作。
     */
    private void CommitNotSignOrderToAppServer(Long UserId, Long AddressId, List<GoodsOfGroupPurchaseCartVO.DataBean> CartItemList) {
        if (!CheckAllParamLegality()) { //检查所有参数的合法性
            return;
        }
        //创建订单JSON对象
        JSONObject mJO = new JSONObject();
        mJO.put("userId", UserId); //用户ID
        mJO.put("addressId", AddressId); //地址ID
        mJO.put("cartItemList", CartItemList); //订单商品列表

        //提示用户请稍后


        //1.发送订单签名请求
        final RequestCall call = OkHttpUtils.postString()
                .url(Api.AddGroupPurchaseOder)
                .content(mJO.toJSONString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build();

        //用户等待时间过长（网络环境很差的情况）时可以取消此次支付
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("支付").setMessage("请稍后...")
                .setCancelable(false).setNegativeButton("取消支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.cancel();  //取消支付请求
                        Toast.makeText(GroupPurchasePayActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                    }
                }).show();

        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("onError", e.toString());
                if (e instanceof java.net.SocketTimeoutException) { //请求超时
                    Toast.makeText(GroupPurchasePayActivity.this, "网络繁忙，请稍后再试!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("onResponse", response);
                dialog.dismiss();
                Toast.makeText(GroupPurchasePayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                //调用payTask方法进行支付

                //提交订单到App服务器

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
