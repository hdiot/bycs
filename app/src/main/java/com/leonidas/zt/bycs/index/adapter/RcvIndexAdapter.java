package com.leonidas.zt.bycs.index.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ProductCategories;
import com.leonidas.zt.bycs.index.bean.RequestDataCase;
import com.leonidas.zt.bycs.index.bean.RequestDataResult;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.Shops;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;

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

    private Context mContext;

    private RcvShopAdapter mShopsAdapter = new RcvShopAdapter(null);
    private RcvSortAdapter mCategoryAdapter = new RcvSortAdapter(null);
    private Shops mShops;
    private ProductCategories mCategories;


    private int shopsCurrentPage = 1;
    private int shopsTotalPage = 1;
    private final int SHOPS_PAGE_SIZE = 15;
    private boolean isShopsLastpage = true;

    private int sortsCurrentPage = 1;
    private int sortsTotalPage = 1;
    private final int SORTS_PAGE_SIZE = 16;
    private boolean isSortsLastpage = true;

    private RequestDataCase shopsReqCase = RequestDataCase.FIRST;
    private RequestDataCase sortsReqCase = RequestDataCase.FIRST;

    private RequestDataResult reqDataRst = RequestDataResult.ERROR;

    private RequestDataListener mReqShopsDataListener;
    private RequestDataListener mReqSortsDataListener;


    public interface RequestDataListener{
        public void resultState(RequestDataResult state);
    }


    private enum ITEM_VIEW_TYPE {
        SORT,
        SHOP
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        if (viewType == ITEM_VIEW_TYPE.SORT.ordinal()) {
            view = layoutInflater.inflate(R.layout.mebee_rcv_view, parent, false);
            return new SortViewHolder(view);
        } else {
            view = layoutInflater.inflate(R.layout.mebee_rcv_view, parent, false);
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

            ((SortViewHolder) holder).rcvSort.setLayoutManager(gridLayoutManager);
            getCategoriesData();

        } else if (holder instanceof ShopsViewHolder) {
            ((ShopsViewHolder) holder).rcvShops
                    .setAdapter(mShopsAdapter);
            ((ShopsViewHolder) holder).rcvShops
                    .setLayoutManager(new LinearLayoutManager(mContext));
            getShopsData(1);

        }
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

    class ShopsViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rcvShops;
        //RecyclerView rcvShops;

        public ShopsViewHolder(View itemView) {
            super(itemView);
            rcvShops = itemView.findViewById(R.id.rcv_index_sort);
        }
    }



    public void loadMore(){
        if (!isShopsLastpage){
            getShopsData(shopsCurrentPage +1);
        } else {
            mReqShopsDataListener.resultState(RequestDataResult.LAST_PAGE);
        }
    }

    public void refresh(){
        sortsReqCase = RequestDataCase.REFRESH;
        shopsReqCase = RequestDataCase.REFRESH;
        getCategoriesData();
        getShopsData(1);
    }


    public void setReqShopsDataListener(RequestDataListener listener) {
        this.mReqShopsDataListener = listener;
    }

    public void setReqSortsDataListener(RequestDataListener listener) {
        this.mReqSortsDataListener = listener;
    }


    private void getCategoriesData() {

        OkHttpHelper.getInstance()
                .doGet(Constant.API.getSorts, new BaseCallback<ResMessage<Data<ProductCategories>>>() {
                    @Override
                    public void OnSuccess(Response response, ResMessage<Data<ProductCategories>> dataResMessage) {
                        mCategories = dataResMessage.getData().getProductCategories();
                        sortsCurrentPage = mCategories.getPageNum();
                        sortsTotalPage = mCategories.getTotalPages();
                        isSortsLastpage = mCategories.isIsLastPage();
                        Log.d(TAG, "OnSuccess: "+ "sortsCurrentPage--"+sortsCurrentPage+ ",sortsTotalPage--" + sortsTotalPage+ ",isSortsLastpage--" + isSortsLastpage);
                        switch (sortsReqCase){
                            case FIRST:
                                sortsReqCase = RequestDataCase.LOAD_MORE;
                                mCategoryAdapter.loadMore(mCategories.getList());
                                break;
                            case REFRESH:
                                sortsReqCase = RequestDataCase.LOAD_MORE;
                                mCategoryAdapter.refresh(mCategories.getList());
                                break;
                            case LOAD_MORE:
                                mCategoryAdapter.loadMore(mCategories.getList());
                                break;
                        }

                        if (mReqSortsDataListener != null) {
                            mReqSortsDataListener.resultState(RequestDataResult.SUCCESS);
                        }
                    }

                    @Override
                    public void onError(Response response, int errCode, Exception e) {
                        if (mReqSortsDataListener != null) {
                            mReqSortsDataListener.resultState(RequestDataResult.ERROR);
                        }
                    }

                    @Override
                    public void onRequestBefore(Request request) {

                    }

                    @Override
                    public void onFailure(Request request, IOException e) {
                        if (mReqSortsDataListener != null) {
                            mReqSortsDataListener.resultState(RequestDataResult.FAIL);
                        }
                    }

                    @Override
                    public void onBzError(Response response, int code, String hint, String data) {
                        if (mReqSortsDataListener != null) {
                            mReqSortsDataListener.resultState(RequestDataResult.ERROR);
                        }
                    }
                });
    }

    private void getShopsData(final int pageNum) {
        String params = formParams(pageNum, SHOPS_PAGE_SIZE);
        OkHttpHelper.getInstance().
                doGet(Constant.API.getShops + params,
                        new BaseCallback<ResMessage<Data<Shops>>>() {

                            @Override
                            public void OnSuccess(Response response,
                                                  ResMessage<Data<Shops>> dataResMessage) {
                                mShops = dataResMessage
                                        .getData()
                                        .getShops();
                                shopsTotalPage = mShops.getTotalPages();
                                shopsCurrentPage = mShops.getPageNum();
                                shopsTotalPage = mShops.getTotalPages();
                                isShopsLastpage = mShops.isIsLastPage();
                                Log.d(TAG, "OnSuccess: "+ "sortsCurrentPage--"+sortsCurrentPage+ ",sortsTotalPage--" + sortsTotalPage+ ",isSortsLastpage--" + isSortsLastpage);
                                switch (shopsReqCase){
                                    case FIRST:
                                        shopsReqCase = RequestDataCase.LOAD_MORE;
                                        mShopsAdapter.refresh(mShops.getList());
                                        break;
                                    case REFRESH:
                                        shopsReqCase = RequestDataCase.LOAD_MORE;
                                        mShopsAdapter.refresh(mShops.getList());
                                        break;
                                    case LOAD_MORE:
                                        mShopsAdapter.loadMore(mShops.getList());
                                        break;
                                }

                                if (mReqShopsDataListener != null){
                                    mReqShopsDataListener.resultState(RequestDataResult.SUCCESS);
                                }
                            }

                            @Override
                            public void onError(Response response, int errCode, Exception e) {
                                if (mReqShopsDataListener != null) {
                                    mReqShopsDataListener.resultState(RequestDataResult.ERROR);
                                }
                            }

                            @Override
                            public void onRequestBefore(Request request) {

                            }

                            @Override
                            public void onFailure(Request request, IOException e) {
                                if (mReqShopsDataListener != null) {
                                    mReqShopsDataListener.resultState(RequestDataResult.FAIL);
                                }
                            }

                            @Override
                            public void onBzError(Response response,
                                                  int code,
                                                  String hint,
                                                  String data) {
                                Log.d(TAG, "onBzError: " + code + hint + data);

                                if (mReqShopsDataListener != null) {
                                    mReqShopsDataListener.resultState(RequestDataResult.ERROR);
                                }
                            }
                        });

    }



    private String formParams(int pageNum, int size) {
        return "?isRecom=" + true + "&pageNum=" + pageNum + "&pageSize=" + size;
    }

}
