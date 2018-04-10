package com.leonidas.zt.bycs.basket.normal.callback;


import java.util.List;

/**
 * Author: mebee.
 * Time: 2018/4/9 14:58
 * Description: Null
 */
public interface ShopCheckedCallBack {
    void onChecked(String id, int position);
    void onUnchecked(String id, int position);
    void onItemCheckedChange(List<String> ids, boolean check);
    void onAllCheck(boolean allCheck);
}
