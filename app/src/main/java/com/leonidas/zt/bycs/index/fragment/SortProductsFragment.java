package com.leonidas.zt.bycs.index.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.ui.loader.MebeeLoader;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvProcductAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.Products;
import com.leonidas.zt.bycs.index.bean.RequestDataCase;
import com.leonidas.zt.bycs.index.bean.RequestDataResult;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mebee on 2018/3/3.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class SortProductsFragment extends BaseFragment {

    private final int DEFAULT_PAGE = 1;
    private XRecyclerView recyclerView;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private RcvProcductAdapter procductAdapter = new RcvProcductAdapter(null);
    private Products products = new Products();
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
        recyclerView = (XRecyclerView) view;
        initRcv();
    }

    private void initRcv() {
        recyclerView.setArrowImageView(R.mipmap.arrow_down);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        recyclerView.setLoadingMoreProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        recyclerView.setFootViewText("疯狂加载中...", "尴尬 |@ .. @| 数据加载完了");
        recyclerView.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        return R.layout.mebee_fragment_products;
    }

    @Override
    public void initData() {
        super.initData();
        recyclerView.setAdapter(procductAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
            recyclerView.setNoMore(true);
        }
    }

    private void getUpdateState(RequestDataResult result) {

        recyclerView.refreshComplete();
        Log.d("result", "getUpdateState: " + result.name());
        recyclerView.loadMoreComplete();
        MebeeLoader.stopLoading();

    }

    private void getData(int page) {
        final Map<String, String> params = new WeakHashMap<>(3);
        params.put("categoryId", categoryId);
        params.put("pageNum", page + "");
        params.put("pageSize", pageSize + "");
        Log.e("map", "getData: " + params.entrySet().toString());
        String param = "?categoryId=" + Long.valueOf(categoryId) + "&pageNum=" + Integer.valueOf(page) + "&pageSize=" + Integer.valueOf(pageSize);
        okHttpHelper.doGet(Constant.API.getProducts + param, new BaseCallback<ResMessage<Data<Products>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<Products>> dataResMessage) {
                try {
                    products = dataResMessage.getData().getProducts();
                } catch (NullPointerException e) {
                    Log.d("data", "OnSuccess: " + categoryId);
                }

                currentPage = products.getPageNum();
                pageSize = products.getPageSize();
                isLastPage = products.isIsLastPage();
                getUpdateState(RequestDataResult.SUCCESS);
                Log.d("data", "OnSuccess: " + "CurrentPage--" + currentPage + ",TotalPage--" + totalPage + ",isLastpage--" + isLastPage);
                switch (requestDataCase) {
                    case FIRST:
                        requestDataCase = RequestDataCase.LOAD_MORE;
                        procductAdapter.loadMore(products.getList());
                        MebeeLoader.stopLoading();
                        break;
                    case REFRESH:
                        requestDataCase = RequestDataCase.LOAD_MORE;
                        procductAdapter.loadMore(products.getList());
                        break;
                    case LOAD_MORE:
                        procductAdapter.loadMore(products.getList());
                        break;
                }

            }

            @Override
            public void onError(Response response, int errCode, Exception e) {
                getUpdateState(RequestDataResult.ERROR);
            }

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                getUpdateState(RequestDataResult.FAIL);
            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {
                getUpdateState(RequestDataResult.ERROR);
            }
        });

    }
}
