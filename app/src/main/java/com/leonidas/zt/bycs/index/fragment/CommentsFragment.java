package com.leonidas.zt.bycs.index.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.app.utils.Constant;
import com.leonidas.zt.bycs.index.adapter.RcvCommentAdapter;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.bean.ShopComments;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.index.utils.OkHttpHelper;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

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
public class CommentsFragment extends BaseFragment {

    private final String mShopId;
    private XRecyclerView mXRCV;
    private RcvCommentAdapter mCommentAdapter;

    public CommentsFragment(String id) {
        mShopId = id;
    }

    @Override
    public void initView(View view) {
        mXRCV = (XRecyclerView) view;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_shop_comments;
    }

    @Override
    public void initData() {
        getCommentsData();
        mCommentAdapter = new RcvCommentAdapter(null);
        mXRCV.setLayoutManager(new LinearLayoutManager(mContext));
        mXRCV.setAdapter(mCommentAdapter);
    }

    public void refrashData(){

    }

    private void getCommentsData(){
        Map<String,String> params = new WeakHashMap<>(0);
        params.put("shopId", mShopId);
        OkHttpHelper.getInstance()
                .doGet(Constant.API.getComment, params, new BaseCallback<ResMessage<Data<ShopComments>>>() {

                    @Override
                    public void OnSuccess(Response response, ResMessage<Data<ShopComments>> dataResMessage) {
                        mCommentAdapter.loadMore(dataResMessage.getData().getShopComments().getList());
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
