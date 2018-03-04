package com.leonidas.zt.bycs.app.ui.error;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by mebee on 2018/3/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class ErrorLayout extends LinearLayout {

    public ErrorLayout(Context context) {
        this(context, null);
    }

    public ErrorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
