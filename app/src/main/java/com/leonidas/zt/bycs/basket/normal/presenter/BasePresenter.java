package com.leonidas.zt.bycs.basket.normal.presenter;

import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by mebee on 2018/3/17.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public abstract class BasePresenter<T> {
    protected Reference<T> mViewRef;

    /**
     * View 接口弱类型应用
     *
     * @param view
     */
    public void attachView(T view) {
        // 建立关联
        mViewRef = new WeakReference<T>(view);
        Log.e("view", "attachView: " + view.toString());
    }

    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView(){
        if (mViewRef == null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
