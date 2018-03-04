package com.leonidas.zt.bycs.app.ui.loader;

import android.content.Context;


import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView.Indicator;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;

import java.util.WeakHashMap;

/**
 * Created by mebee on 2018/3/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    //static AVLoadingIndicatorView create(String type, Context context) {
    static AVLoadingIndicatorView create(int type, Context context) {

        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        /*if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));*/
        if (type>25 || type < 0){
            avLoadingIndicatorView.setIndicatorId(AVLoadingIndicatorView.Pacman);
        } else {
            avLoadingIndicatorView.setIndicatorId(type);
        }
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
