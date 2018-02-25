package com.leonidas.zt.bycs.basket.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.basket.adapter.CartGoodsListRvAdapter;

/**
 * Created by 华强 on 2018/1/29.
 * Version: V1.0
 * Description:拼购篮（拼购商城购物车）Fragment
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseBasketFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "GroupPurchaseBasketFragment";

    //private CheckBox cbAllSelect; //全选
    private TextView tvDelete; //删除按钮
    private RecyclerView rvCartGoodsList; //购物车内商品的列表
    private LinearLayout llCheckAll; //全选
    private TextView tvGroupPeopleNum; //拼购人数
    private TextView tvTotalPrice; //总价
    private Button btGroupPurchase; //拼单按钮
    @Override
    public void initView(View view) {
        findViews(view);
        initListener();
        rvCartGoodsList.setAdapter(new CartGoodsListRvAdapter(getContext()));
        rvCartGoodsList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
       // initData(); //加载数据
    }

    @Override
    public void initData() {
        super.initData();
        CartGoodsListRvAdapter adapter = (CartGoodsListRvAdapter) rvCartGoodsList.getAdapter();
        adapter.initGroupPurchaseCartGoods(1, 1);//加载拼购购物车内的数据
    }

    private void initListener() {
        btGroupPurchase.setOnClickListener( this );
    }

    @Override
    public int getLayoutResource() {
        return R.layout.leonidas_fragment_group_purchase_basket;
    }

    private void findViews(View view) {
        //cbAllSelect = view.findViewById(R.id.cb_all_select);
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
            // Handle clicks for btGroupPurchase
        }
    }

    /**
     * fragment显示的时候要对购物车内数据重新进行刷新
     */
    @Override
    public void onStart() {
        super.onStart();

    }

}
