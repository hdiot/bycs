package com.leonidas.zt.bycs.index.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.Category;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shops;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mebee on 2018/1/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RcvIndexAdapter";
    private RcvShopAdapter mShopsAdapter = new RcvShopAdapter(null);
    private RcvSortAdapter mCategoryAdapter = new RcvSortAdapter(null);

    private enum ITEM_VIEW_TYPE {
        SORT,
        SHOP
    }

    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == ITEM_VIEW_TYPE.SORT.ordinal()) {
            view = layoutInflater.inflate(R.layout.mebee_rcv_view, parent, false);
            return new SortViewHolder(view);
        } else {
            view = layoutInflater.inflate(R.layout.mebee_xrcv_view, parent, false);
            return new ShopsViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_VIEW_TYPE.SORT.ordinal() : ITEM_VIEW_TYPE.SHOP.ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SortViewHolder) {
            ((SortViewHolder) holder).rcvSort
                    .setAdapter(mCategoryAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
            // gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((SortViewHolder) holder).rcvSort.setLayoutManager(gridLayoutManager);
            getSortData();

        } else if (holder instanceof ShopsViewHolder) {
            ((ShopsViewHolder) holder).xrcvShops
                    .setAdapter(mShopsAdapter);
            ((ShopsViewHolder) holder).xrcvShops
                    .setLayoutManager(new LinearLayoutManager(mContext));
            ((ShopsViewHolder) holder).xrcvShops.setPullRefreshEnabled(false);
            ((ShopsViewHolder) holder).xrcvShops.setLoadingMoreEnabled(false);

            ((ShopsViewHolder) holder).xrcvShops
                    .setLoadingListener(new XRecyclerView.LoadingListener() {
                        @Override
                        public void onRefresh() {

                        }

                        @Override
                        public void onLoadMore() {

                        }
                    });

            getShopsData("1", "15");

        }
    }

    private void getShopsData(String pageNum, String size) {
        String params = formParams(pageNum, size);
        Log.d(TAG, "getShopsData: " + params);
        OkHttpHelper.getInstance().
                doGet(Constant.API.getShops + params,
                        new BaseCallback<ResMessage<Data<Shops>>>() {

                            @Override
                            public void OnSuccess(Response response,
                                                  ResMessage<Data<Shops>> dataResMessage) {
                                if (dataResMessage.getCode() == 1) {
                                    Log.d(TAG, "OnSuccess: " + dataResMessage
                                                    .getData()
                                                    .getShops()
                                                    .getList()
                                                    .get(0)
                                                    .toString());
                                    mShopsAdapter.loadMore(dataResMessage
                                            .getData()
                                            .getShops()
                                            .getList());
                                } else {

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
                                Dialog dialog = new Dialog(mContext);

                            }

                            @Override
                            public void onBzError(Response response,
                                                  int code,
                                                  String hint,
                                                  String data) {
                                Log.d(TAG, "onBzError: " + code + hint + data);
                            }
                        });
    }

    private void getSortData() {
        Type type = new TypeReference<Category>() {}.getType();
        List<Category> categories = JSON.parseArray(mContext.getResources().getString(R.string.category_list), Category.class);
        Log.d(TAG, "getSortData: " + categories.get(0).getCategoryName());
        mCategoryAdapter.refresh(categories);
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    class SortViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rcvSort;

        public SortViewHolder(View itemView) {
            super(itemView);
            rcvSort = itemView.findViewById(R.id.rcv_index_sort);
        }
    }

    class ShopsViewHolder extends XRecyclerView.ViewHolder {

        XRecyclerView xrcvShops;

        public ShopsViewHolder(View itemView) {
            super(itemView);
            xrcvShops = itemView.findViewById(R.id.xrcv_index_shops);
        }
    }

    private String formParams(String pageNum, String size) {
        return "?isRecom=" + true + "&pageNum=" + pageNum + "&pageSize=" + size;
    }

}
