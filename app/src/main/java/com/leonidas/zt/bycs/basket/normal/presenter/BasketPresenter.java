package com.leonidas.zt.bycs.basket.normal.presenter;

import com.leonidas.zt.bycs.basket.normal.bean.CartBean;
import com.leonidas.zt.bycs.basket.normal.model.CartModelImp;
import com.leonidas.zt.bycs.basket.normal.view.BasketView;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.user.User;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mebee on 2018/3/18.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class BasketPresenter extends BasePresenter<BasketView> {

    private CartModelImp mMartModelImp;
    private String mUid;
    private CartBean mCartBean = null;
    private int mPageNum = 1;
    private int mPageSize = -1;


    private enum RequstState{
        FIRST,
        MORE,
        REFRESH
    }

    public BasketPresenter(){
        mMartModelImp = new CartModelImp();
        if (User.getInstance().isLogin()) {
            mUid = User.getInstance().getUserInfo().userId;
        } else {
            // TODO: 2018/3/21
        }
    }


    private void getData(int pageNum, final RequstState state){
        mMartModelImp.getCart(mUid, 1, 15, new BaseCallback<ResMessage<CartBean>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<CartBean> cartBeanResMessage) {
                mCartBean = cartBeanResMessage.getData();
                mPageNum = mCartBean.getPageNum();
                mPageSize = mCartBean.getPageSize();

                switch (state){
                    case REFRESH:
                        getView().refresh(mCartBean.getList());
                        getView().refreshFinish();
                        break;
                    case FIRST:
                        getView().loadMore(mCartBean.getList());
                        break;
                    case MORE:
                        getView().loadMore(mCartBean.getList());
                        getView().loadMoreFinish();
                        break;
                }

            }

            @Override
            public void onError(Response response, int errCode, Exception e) {
                switch (state){
                    case REFRESH:
                        getView().refreshFinish();
                        break;
                    case FIRST:
                        getView().loadMoreFinish();
                        break;
                    case MORE:
                        getView().loadMoreFinish();
                        break;
                }
            }

            @Override
            public void onRequestBefore(Request request) {
                switch (state){
                    case REFRESH:
                        getView().refreshFinish();
                        break;
                    case FIRST:
                        getView().loadMoreFinish();
                        break;
                    case MORE:
                        getView().loadMoreFinish();
                        break;
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                switch (state){
                    case REFRESH:
                        getView().refreshFinish();
                        break;
                    case FIRST:
                        getView().loadMoreFinish();
                        break;
                    case MORE:
                        getView().loadMoreFinish();
                        break;
                }
            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {
                switch (state){
                    case REFRESH:
                        getView().refreshFinish();
                        break;
                    case FIRST:
                        getView().loadMoreFinish();
                        break;
                    case MORE:
                        getView().loadMoreFinish();
                        break;
                }
            }
        });
    }

    private void netError(){
        getView().loadMoreFinish();
        getView().refreshFinish();
    }

    public void loadMore(){
        if (mPageSize == -1){
            getData(1, RequstState.FIRST);
        } else {
            if ((mPageNum +1) > mPageSize){
                getView().showNoMoreData();
            } else {
                getData(mPageNum +1, RequstState.MORE);
            }
        }
    }

    public void refresh(){
        getData(1, RequstState.REFRESH);
    }

    public void deleteItem(){

    }

    public void pullOrder() {

    }
}
