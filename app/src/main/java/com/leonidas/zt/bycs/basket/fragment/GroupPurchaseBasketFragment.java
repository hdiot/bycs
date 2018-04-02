package com.leonidas.zt.bycs.basket.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.basket.activity.ConfirmOrderActivity;
import com.leonidas.zt.bycs.basket.adapter.CartGoodsListRvAdapter;
import com.leonidas.zt.bycs.basket.vo.GoodsOfGroupPurchaseCartVO;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 华强 on 2018/1/29.
 * Version: V1.0
 * Description:拼购篮（拼购商城购物车）Fragment
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseBasketFragment extends BaseFragment implements View.OnClickListener {
    public static final String Tag_SelectedCartOfGoodsList = "SelectedCartOfGoodsList";
    public static final String Tag_GoodsTotalPrice = "GoodsTotalPrice";
    private static final String TAG = "GroupPurchaseBasket";

    private CheckBox cbAllSelect; //全选
    private TextView tvDelete; //删除按钮
    private RecyclerView rvCartGoodsList; //购物车内商品的列表
    private LinearLayout llCheckAll; //全选
    private TextView tvGroupPeopleNum; //拼购人数
    private TextView tvTotalPrice; //总价
    private Button btGroupPurchase; //拼单按钮

    private List<GoodsOfGroupPurchaseCartVO.DataBean> GoodsOfGroupPurchaseCartList = new ArrayList<>(); //拼购商品购物车数据源

    @Override
    public void initView(View view) {
        findViews(view);
        initListener();
        rvCartGoodsList.setAdapter(new CartGoodsListRvAdapter(getContext(), GoodsOfGroupPurchaseCartList, cbAllSelect, tvTotalPrice));
        rvCartGoodsList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        initData(); //加载数据
    }

    @Override
    public void initData() {
        super.initData();
        initGroupPurchaseCartGoods(1516332510603L); //加载购物车商品数据
        initGroupPurchaseTotalPeople(); //加载拼购组限制人数
    }

    private void initGroupPurchaseTotalPeople() {
        OkHttpUtils.get()
                .url(Api.QueryGroupPurchaseTotalPeople)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
                Toast.makeText(mContext, "服务器错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                JSONObject mJO = JSONObject.parseObject(response);
                int ResultCode = mJO.getInteger(ApiParamKey.ResultCode);

                if (ResultCode != 1) {
                    Toast.makeText(mContext, "获取信息拼购组信息失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvGroupPeopleNum.setText(mJO.getJSONObject(ApiParamKey.Data).getInteger(ApiParamKey.GroupPurchaseTotalPeople) + "");
            }
        });
    }

    private void initListener() {
        btGroupPurchase.setOnClickListener( this );
        cbAllSelect.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.leonidas_fragment_group_purchase_basket;
    }

    private void findViews(View view) {
        cbAllSelect = view.findViewById(R.id.cb_all_select);
        tvDelete = view.findViewById(R.id.tv_delete);
        rvCartGoodsList = view.findViewById(R.id.rv_cart_goods_list);
        llCheckAll = view.findViewById(R.id.ll_check_all);
        tvGroupPeopleNum = view.findViewById(R.id.tv_group_people_num);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btGroupPurchase = view.findViewById(R.id.bt_group_purchase);
    }

    @Override
    public void onClick(View v) {
        if ( v == btGroupPurchase ) {//拼单按钮
            Settlement();
        } else if (v == cbAllSelect) {//全选CheckBox
            CartGoodsListRvAdapter adapter = (CartGoodsListRvAdapter) rvCartGoodsList.getAdapter();
            adapter.modifyAllItemSelectStatus(cbAllSelect.isChecked());
            tvTotalPrice.setText(adapter.calculateTotalPrice() + ""); //计算总价);
        } else if (v == tvDelete) {//删除选中商品
            deleteGoods();
        }

    }

    /**
     * fragment显示的时候要对购物车内数据重新进行刷新
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 加载拼购购物车内的商品数据
     * @param UserId 用户ID
     */
    public void initGroupPurchaseCartGoods(long UserId) {
        OkHttpUtils.get()
                .url(Api.QueryUserPurchaseCart)
                .addParams(ApiParamKey.UserId, UserId + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
                Toast.makeText(mContext, "服务器错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                //解析数据
                GoodsOfGroupPurchaseCartVO data = JSON.parseObject(response, GoodsOfGroupPurchaseCartVO.class);

                if (data.getData() == null) {//数据出错
                    Toast.makeText(mContext, "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (data.getCode() != 1) {//数据获取失败
                    Toast.makeText(mContext, "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                    return;
                }
                GoodsOfGroupPurchaseCartList.addAll(data.getData());
                CartGoodsListRvAdapter adapter = (CartGoodsListRvAdapter) rvCartGoodsList.getAdapter();
                adapter.notifyDataSetChanged();
                tvTotalPrice.setText(adapter.calculateTotalPrice() + ""); //计算总价
                //校验当前是否“全选”
                if (adapter.isCheckAll()) {
                    cbAllSelect.setChecked(true);
                } else {
                    cbAllSelect.setChecked(false);
                }

            }
        });

    }

    /**
     * 结算
     */
    private void Settlement() {
        // 获取被选中的商品
        List<GoodsOfGroupPurchaseCartVO.DataBean> SelectedCartOfGoodsList = new ArrayList<>();
        for (GoodsOfGroupPurchaseCartVO.DataBean goods : GoodsOfGroupPurchaseCartList) {
            if (goods.isSelect()) {
                SelectedCartOfGoodsList.add(goods);
            }
        }

        if (SelectedCartOfGoodsList.size() <= 0) {
            Toast.makeText(mContext, "请至少选中一件商品", Toast.LENGTH_SHORT).show();
            return;
        }

        //打开确认订单Activity
        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag_SelectedCartOfGoodsList, (Serializable) SelectedCartOfGoodsList);
        intent.putExtra(Tag_SelectedCartOfGoodsList, bundle);//被选中的商品
        intent.putExtra(Tag_GoodsTotalPrice, tvTotalPrice.getText().toString());
        mContext.startActivity(intent);
    }


    /**
     * 删除(选中的) 商品
     */
    public void deleteGoods() {
        if (GoodsOfGroupPurchaseCartList != null && GoodsOfGroupPurchaseCartList.size() > 0) {
            //删除选中的数据
            for (int i = 0; i < GoodsOfGroupPurchaseCartList.size(); i++) {
                GoodsOfGroupPurchaseCartVO.DataBean goods = GoodsOfGroupPurchaseCartList.get(i);
                if (goods.isSelect()) {//如果勾选,则删除
                    //1.从内存中移除
                    GoodsOfGroupPurchaseCartList.remove(goods);
                    //2.从服务器删除数据
                    deleteGoodsFromServer(goods);
                    //3.刷新
                    CartGoodsListRvAdapter adapter = (CartGoodsListRvAdapter) rvCartGoodsList.getAdapter();
                    //adapter.notifyItemRemoved(i); //---调用时候发生错误java.lang.NoClassDefFoundError: android.support.v4.animation.AnimatorCompatHelper
                    adapter.notifyDataSetChanged();
                    //每移除一条数据datas的大小应该减一
                    i--;
                }
            }
        }
    }

    /**
     * 从服务器删除购物车商品数据
     * @param goods
     */
    private void deleteGoodsFromServer(GoodsOfGroupPurchaseCartVO.DataBean goods) {
        JSONObject mJO = new JSONObject();
        mJO.put(ApiParamKey.UserId, goods.getUserId());
        mJO.put(ApiParamKey.CartItemId, goods.getItemId() + "");
        //String json = mJO.toJSONString();
        OkHttpUtils.delete()
                .url(Api.DeleteGoodsByCartItemId)
                .requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJO.toJSONString()))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
                Toast.makeText(mContext, "删除失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                JSONObject mJO = JSONObject.parseObject(response);
                int ResultCode = mJO.getInteger(ApiParamKey.ResultCode);

                if (ResultCode != 1) {//删除失败
                    Toast.makeText(mContext, "删除失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });
    }

}
