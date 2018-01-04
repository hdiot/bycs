package com.leonidas.zt.bycs.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 华强 on 2018/1/4.
 * Version: V1.0
 * Description: BaseFragment
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;//上下文

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 加载视图（强制重写）
     * @return 此Fragment的视图
     */
    public abstract View initView();

    /**
     * 加载数据，网络请求（可选）
     */
    public void initData() {

    }
}
