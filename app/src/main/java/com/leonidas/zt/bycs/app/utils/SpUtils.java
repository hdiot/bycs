package com.leonidas.zt.bycs.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 华强 on 2018/2/8.
 * Version: V1.0
 * Description: SharedPreferences缓存工具类 --- 实际应用的时候最好保存在SD卡
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class SpUtils {

    /**
     * 得到保存的String类型的数据
     * @param context 当前APP的上下文
     * @param key 要获取的数据的键。
     * @return 查询到的数据
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("bycs",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

    /**
     * 保存String的数据
     * @param context 当前APP的上下文
     * @param key 键
     * @param value 值
     */
    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("bycs",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    /**
     * 得到保存的boolean类型的数据
     * @param context 当前APP的上下文
     * @param key 要获取的数据的键。
     * @return 查询到的数据
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("bycs",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    /**
     * 保存Boolean的数据
     * @param context 当前APP的上下文
     * @param key 键
     * @param value 值
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("bycs",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

}
