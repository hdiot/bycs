package com.leonidas.zt.bycs.user.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.ui.error.ErrorLayout;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.RequestDataCase;
import com.leonidas.zt.bycs.index.bean.RequestDataResult;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;
import com.leonidas.zt.bycs.user.User;
import com.leonidas.zt.bycs.user.activity.LoginActivity;
import com.leonidas.zt.bycs.user.adapter.OrderAdapter;
import com.leonidas.zt.bycs.user.bean.Orders;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mebee on 2018/2/2.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
@SuppressLint("ValidFragment")
public class OrdersFragment extends BaseFragment {

    private XRecyclerView xRecyclerView;
    private ErrorLayout errorLayout;
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();

    private RequestDataResult requestDataResult = RequestDataResult.UNKNOW;
    private RequestDataCase requestDataCase = RequestDataCase.FIRST;

    private final int DEFAULT_PAGE = 1;
    private int currentPage = 1;
    private int totalPage = 1;
    private int pageSize = 15;
    private boolean isLastPage = true;

    private OrderAdapter orderAdapter;
    private Orders orders = new Orders();

    public OrdersFragment(ORDER_CLASSIFICATIONS orderClass) {
        this.orderClass = orderClass;
    }

    private ORDER_CLASSIFICATIONS orderClass = ORDER_CLASSIFICATIONS.ALL;

    public enum ORDER_CLASSIFICATIONS {
        ALL,
        WAIT4PAY,
        WAIT4GROUP,
        WAIT4DELIVER,
        WAIT4RECEIVE,
        WAIT4EVALUATE
    }

    private void refresh() {
        requestDataCase = RequestDataCase.REFRESH;
        getData(DEFAULT_PAGE);
    }

    private void loadMore() {
        if (!isLastPage) {
            getData(currentPage + 1);
        } else {
            xRecyclerView.setNoMore(true);
        }
    }

    private void getUpdateState(RequestDataResult result) {
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
    }

    @Override
    public void initView(View view) {

        xRecyclerView = (XRecyclerView) view.findViewById(R.id.xr_order_item);
        errorLayout = (ErrorLayout) view.findViewById(R.id.error_layout_order_fragment);
        errorLayout.setVisibility(View.VISIBLE);
        orderAdapter = new OrderAdapter(null, orderClass);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        xRecyclerView.setAdapter(orderAdapter);
        initRcv();
    }

    private void initRcv() {
        xRecyclerView.setLoadingMoreProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        xRecyclerView.setRefreshProgressStyle(AVLoadingIndicatorView.BallScaleRipple);
        xRecyclerView.setArrowImageView(R.mipmap.arrow_down);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        return R.layout.mebee_fragment_orders;
    }

    @Override
    public void initData() {

        if (requestDataCase == RequestDataCase.FIRST) {
            getData(DEFAULT_PAGE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
    }

    private void getData(int page) {

        String params = "?userId=" + User.getInstance().getUserInfo().getUserId() + "&pageNum=" + Integer.valueOf(page) + "&pageSize=" + Integer.valueOf(pageSize);

        switch (orderClass) {
            case ALL:
                break;
            case WAIT4PAY:
                params += "&payStatus=0";
                break;
            case WAIT4EVALUATE:
                params += "&orderStatus=1&commentStatus=0";
                break;
            case WAIT4GROUP:
                params += "&commentStatus=0";
                break;
            case WAIT4DELIVER:
                params += "&deliveryStatus=0";
                break;
            case WAIT4RECEIVE:
                params += "&deliveryStatus=1";
                break;
        }
        Log.e("params", "getData: " + params);
        okHttpHelper.doGet(Constant.API.getOrders + params, new BaseCallback<ResMessage<Orders>>() {
            @Override
            public void OnSuccess(Response response, ResMessage<Orders> ordersResMessage) {
                try {
                    orders = ordersResMessage.getData();
                    currentPage = orders.getPageNum();
                    totalPage = orders.getTotal();
                    pageSize = orders.getPageSize();
                    isLastPage = orders.isLastPage();
                    errorLayout.setVisibility(View.GONE);
                    getUpdateState(RequestDataResult.SUCCESS);

                    if (orders.getList() != null && orders.getList().size() != 0) {
                        Log.e("null", "yes: ");
                        switch (requestDataCase) {
                            case FIRST:
                                requestDataCase = RequestDataCase.LOAD_MORE;
                                orderAdapter.loadMore(orders.getList());
                                break;
                            case REFRESH:
                                requestDataCase = RequestDataCase.LOAD_MORE;
                                orderAdapter.refresh(orders.getList());
                                break;
                            case LOAD_MORE:
                                orderAdapter.loadMore(orders.getList());
                                break;
                        }
                        errorLayout.setVisibility(View.GONE);
                    } else if (requestDataCase == RequestDataCase.FIRST ||
                            requestDataCase == RequestDataCase.REFRESH) {
                        errorOrNull("没有订单");
                    }

                } catch (NullPointerException e) {
                    errorOrNull("没有订单");
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
                if (code == 111){
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                errorOrNull(hint);
                getUpdateState(RequestDataResult.ERROR);

            }
        });
    }

    private void errorOrNull(String msg) {

        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
        errorLayout.setVisibility(View.VISIBLE);
        errorLayout.setLoadingIndicatorVisibility(View.GONE);
        errorLayout.setErrorMessage(msg);
        errorLayout.setToDoBtnVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}
