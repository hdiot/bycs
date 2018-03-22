package com.leonidas.zt.bycs.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mebee on 2018/3/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class DateUtils {
    public static String Date2ms(long _data){
        SimpleDateFormat format =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.format(new Date(_data));
        }catch(Exception e){
            return "";
        }
    }

}
