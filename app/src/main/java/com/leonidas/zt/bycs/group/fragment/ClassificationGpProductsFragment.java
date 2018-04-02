package com.leonidas.zt.bycs.group.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.ui.error.ErrorLayout;
import com.leonidas.zt.bycs.app.ui.loader.MebeeLoader;
import com.leonidas.zt.bycs.group.adapter.ProductRvAdapter;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsListVO;
import com.leonidas.zt.bycs.index.bean.RequestDataCase;
import com.leonidas.zt.bycs.index.bean.RequestDataResult;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 华强 on 2018/4/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class ClassificationGpProductsFragment extends BaseFragment{
    private static final String TAG = "ClassiGpProduFragment";

    private final int DEFAULT_PAGE = 1;
    private XRecyclerView rvProductFragment;
    private ErrorLayout errorLayoutProductFragment;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private ProductRvAdapter productAdapter = new ProductRvAdapter(null);
    private GroupPurchaseGoodsListVO data;
    private String categoryId;

    private RequestDataResult requestDataResult = RequestDataResult.UNKNOW;
    private RequestDataCase requestDataCase = RequestDataCase.FIRST;
    private int currentPage = 1;
    private int totalPage = 1;
    private int pageSize = 15;
    private boolean isLastPage = true;

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void initView(View view) {
        MebeeLoader.showLoading(mContext);
        rvProductFragment =  view.findViewById(R.id.rv_product_fragment);
        errorLayoutProductFragment =  view.findViewById(R.id.error_layout_product_fragment);
        errorLayoutProductFragment.setVisibility(View.VISIBLE);
        errorLayoutProductFragment.setToDoBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: reload" );
            }
        });
        initRcv();
    }

    private void initRcv() {
        rvProductFragment.setArrowImageView(R.mipmap.arrow_down);
        rvProductFragment.setLoadingMoreEnabled(true);
        rvProductFragment.setPullRefreshEnabled(true);
        rvProductFragment.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        rvProductFragment.setLoadingMoreProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        rvProductFragment.setFootViewText("疯狂加载中...", "尴尬 |@ .. @| 数据加载完了");
        rvProductFragment.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        rvProductFragment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.leonidas_fragment_products;
    }

    @Override
    public void initData() {
        super.initData();
        rvProductFragment.setAdapter(productAdapter);
        rvProductFragment.setLayoutManager(new LinearLayoutManager(mContext));
        getData(DEFAULT_PAGE);
    }

    private void refresh() {
        requestDataCase = RequestDataCase.REFRESH;
        getData(DEFAULT_PAGE);
    }

    private void loadMore() {
        if (!isLastPage) {
            getData(currentPage + 1);
        } else {
            rvProductFragment.setNoMore(true);
        }
    }

    private void getUpdateState(RequestDataResult result) {

        rvProductFragment.refreshComplete();
        Log.d("result", "getUpdateState: " + result.name());
        rvProductFragment.loadMoreComplete();
        MebeeLoader.stopLoading();

    }

    private void getData(int page) {
        final Map<String, String> params = new WeakHashMap<>(3);
        params.put("categoryId", categoryId);
        params.put("pageNum", page + "");
        params.put("pageSize", pageSize + "");
        Log.e("map", "getData: " + params.entrySet().toString());
        String param = "?categoryId=" + Long.valueOf(categoryId) + "&pageNum=" + Integer.valueOf(page) + "&pageSize=" + Integer.valueOf(pageSize);
        okHttpHelper.doGet(Api.QueryGroupMultiPurchaseGoods + param, new BaseCallback<String>() {

            @Override
            public void OnSuccess(Response response, String s) {
                try {
                    data = JSON.parseObject(s, GroupPurchaseGoodsListVO.class);
                    if (data.getData() == null) {//数据出错
                        rvProductFragment.setNoMore(true);
                        return;
                    }

                    if (data.getCode() != 1) {//数据获取失败
                        Toast.makeText(mContext, "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentPage = data.getData().getGroupProducts().getPageNum();
                    pageSize = data.getData().getGroupProducts().getPageNum();
                    isLastPage = data.getData().getGroupProducts().isIsLastPage();
                    getUpdateState(RequestDataResult.SUCCESS);

                    switch (requestDataCase) {
                        case FIRST:
                            requestDataCase = RequestDataCase.LOAD_MORE;
                            productAdapter.loadMore(data.getData().getGroupProducts().getList());
                            MebeeLoader.stopLoading();
                            break;
                        case REFRESH:
                            requestDataCase = RequestDataCase.LOAD_MORE;
                            productAdapter.loadMore(data.getData().getGroupProducts().getList());
                            break;
                        case LOAD_MORE:
                            productAdapter.loadMore(data.getData().getGroupProducts().getList());
                            break;
                    }
                    errorLayoutProductFragment.setVisibility(View.GONE);
                } catch (RuntimeException e) {
                    rvProductFragment.refreshComplete();
                    rvProductFragment.loadMoreComplete();
                    Log.e("data", "exception: " + categoryId);
                    errorLayoutProductFragment.setLoadingIndicatorVisibility(View.GONE);
                    errorLayoutProductFragment.setErrorMessage("无对应的产品");
                    errorLayoutProductFragment.setToDoBtnVisibility(View.VISIBLE);
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

    }
}
