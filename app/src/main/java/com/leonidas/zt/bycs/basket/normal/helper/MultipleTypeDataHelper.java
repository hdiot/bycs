package com.leonidas.zt.bycs.basket.normal.helper;

import android.support.annotation.NonNull;

import com.leonidas.zt.bycs.basket.normal.bean.MultipleTypeBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mebee on 2018/3/19.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class MultipleTypeDataHelper {
    public List<MultipleTypeBean> getDatas() {
        return datas;
    }

    public void add(@NonNull MultipleTypeBean bean) {
        datas.add(bean);
    }

    public void add(int type, Object data) {
        MultipleTypeBean bean = new MultipleTypeBean();
        bean.setmType(type);
        bean.setmData(data);
        datas.add(bean);
    }

    public void add(int index, MultipleTypeBean element) {
        datas.add(index, element);
    }

    public void add(int index, Collection<? extends MultipleTypeBean> collection){
        datas.addAll(index, collection);
    }

    public void remove(MultipleTypeBean bean) {
        datas.remove(bean);
    }

    public void remove(int index) {
        datas.remove(index);
    }

    public void clear() {
        datas.clear();
    }

    public void setDatas(@NonNull ArrayList<MultipleTypeBean> datas) {
        this.datas = datas;
    }

    private ArrayList<MultipleTypeBean> datas = new ArrayList<MultipleTypeBean>();

}
