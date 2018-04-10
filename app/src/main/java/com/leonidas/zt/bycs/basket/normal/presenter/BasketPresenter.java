package com.leonidas.zt.bycs.basket.normal.presenter;


import com.leonidas.zt.bycs.basket.normal.bean.CartBean;
import com.leonidas.zt.bycs.basket.normal.bean.ShopItem;
import com.leonidas.zt.bycs.basket.normal.model.CartModelImp;
import com.leonidas.zt.bycs.basket.normal.view.BasketView;
import com.leonidas.zt.bycs.index.bean.Data;
import com.leonidas.zt.bycs.index.bean.ResMessage;
import com.leonidas.zt.bycs.index.utils.BaseCallback;
import com.leonidas.zt.bycs.user.User;

import java.io.IOException;
import java.util.List;

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
    private List<ShopItem> mShopItems = null;
    private int mPageNum = 1;
    private int mPageSize = -1;

    public void selectShop(String id, boolean select) {
        mMartModelImp.selectShop(mUid, id, select, new BaseCallback<ResMessage<Data<String>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<String>> dataResMessage) {
                getView().selectShopSucceed();
                getView().hideProgress();
            }

            @Override
            public void onError(Response response, int errCode, Exception e) {
                getView().selectShopFail();
            }

            @Override
            public void onRequestBefore(Request request) {
                getView().showProgress();
            }

            @Override
            public void onFailure(Request request, IOException e) {
                getView().selectShopFail();

            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {
                getView().selectShopFail();
            }
        });
    }

    public void selectProduct(List<String> pids, boolean select) {
        mMartModelImp.selectProduct(mUid, pids, select, new BaseCallback<ResMessage<Data<String>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<Data<String>> dataResMessage) {
                getView().selectProductSucceed();
                getView().hideProgress();
            }

            @Override
            public void onError(Response response, int errCode, Exception e) {
                getView().selectProductFail();
                getView().onError("服务器出错！code：" + errCode, 2);
            }

            @Override
            public void onRequestBefore(Request request) {
                getView().showProgress();
            }

            @Override
            public void onFailure(Request request, IOException e) {
                getView().selectProductFail();
                getView().onError("服务器出错！", 1);
            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {
                getView().selectProductFail();
                getView().onError( hint, code);
            }
        });
    }


    private enum RequestState {
        FIRST,
        MORE,
        REFRESH
    }

    public BasketPresenter() {
        mMartModelImp = new CartModelImp();
    }

    public boolean checkLoginState() {
        if (User.getInstance().isLogin()) {
            mUid = User.getInstance().getUserInfo().userId;
            return true;
        }
        getView().refreshFinish();
        getView().loadMoreFinish();
        getView().onError("登录后才能使用购物车哦！",0);
        return false;
    }

    private void getData(int pageNum, final RequestState state) {

        if (!checkLoginState()) {

            return;
        }

        mMartModelImp.getCart(mUid, new BaseCallback<ResMessage<CartBean>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<CartBean> cartBeanResMessage) {
                mCartBean = cartBeanResMessage.getData();
                mPageNum = mCartBean.getPageNum();
                mPageSize = mCartBean.getPages();
                getView().hideProgress();
                switch (state) {
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
                switch (state) {
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

            }

            @Override
            public void onFailure(Request request, IOException e) {
                switch (state) {
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
                switch (state) {
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

    private void getData() {

        if (!checkLoginState()) {

            return;
        }

        mMartModelImp.getCart(mUid, new BaseCallback<ResMessage<List<ShopItem>>>() {

            @Override
            public void OnSuccess(Response response, ResMessage<List<ShopItem>> listResMessage) {
                mShopItems = listResMessage.getData();
                getView().refresh(mShopItems);
                getView().refreshFinish();
                getView().hideProgress();
            }

            @Override
            public void onError(Response response, int errCode, Exception e) {
                getView().refreshFinish();
                getView().loadMoreFinish();
            }

            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                getView().refreshFinish();
            }

            @Override
            public void onBzError(Response response, int code, String hint, String data) {
                getView().refreshFinish();
            }
        });
    }

    private void netError() {
        getView().loadMoreFinish();
        getView().refreshFinish();
    }

    public void loadMore() {
        if (mPageSize == -1) {
            getData(1, RequestState.FIRST);
        } else {
            if ((mPageNum + 1) > mPageSize) {
                getView().showNoMoreData();
            } else {
                getData(mPageNum + 1, RequestState.MORE);
            }
        }
    }

    public void refresh() {
        getData();
    }

    public void deleteItem(String cartId) {

        mMartModelImp.deleteItem(mUid, cartId, new BaseCallback() {
            @Override
            public void OnSuccess(Response response, Object o) {

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

    public void pullOrder() {

    }
}
