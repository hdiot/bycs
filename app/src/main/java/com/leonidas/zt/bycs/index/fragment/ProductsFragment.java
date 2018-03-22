package com.leonidas.zt.bycs.index.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvProductAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.Products;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mebee on 2018/2/28.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
@SuppressLint("ValidFragment")
public class ProductsFragment extends BaseFragment {
    private static final String TAG = "ProductsFragment";
    private final String mShopId;
    private XRecyclerView mXRCV;
    private RcvProductAdapter mAdapter;
    private OkHttpHelper mHttpHelper;

    public ProductsFragment(String shopId) {
        mShopId = shopId;
    }

    @Override
    public void initView(View view) {
        mXRCV = (XRecyclerView) view;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_shop_products;
    }

    @Override
    public void initData() {
        mHttpHelper = OkHttpHelper.getInstance();
        mAdapter = new RcvProductAdapter(null, mShopId);
        mXRCV.setLayoutManager(new LinearLayoutManager(mContext));
        mXRCV.setAdapter(mAdapter);
        getProductData();
    }

    private void getProductData(){
        Map<String,String> map = new LinkedHashMap<String,String>(1);
        map.put("shopId",mShopId);

        mHttpHelper.doGet(Constant.API.getProducts, map, new BaseCallback<ResMessage<Data<Products>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<Products>> dataResMessage) {
                mAdapter.loadMore(dataResMessage.getData().getProducts().getList());
                Log.d(TAG, "OnSuccess: " + dataResMessage.getData().getProducts().getList().get(0).getProductName());
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
