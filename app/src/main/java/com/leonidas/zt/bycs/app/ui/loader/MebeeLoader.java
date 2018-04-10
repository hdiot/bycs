package com.leonidas.zt.bycs.app.ui.loader;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.utils.DimenUtil;
//import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;


/**
 * Created by mebee on 2018/3/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class MebeeLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 8;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    //private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();
    private static final int DEFAULT_LOADER = AVLoadingIndicatorView.BallClipRotateMultiple;

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    //public static void showLoading(Context context, String type, String message) {
    public static void showLoading(Context context, int type, String message) {

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        final TextView messageTxt = new TextView(context);
        final LinearLayout linearLayout = new LinearLayout(context);
        messageTxt.setText(message);
        messageTxt.setTextColor(Color.WHITE);

        final LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        final LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(100,100);

        linearLayout.setGravity(Gravity.CENTER);
        avLoadingIndicatorView.setLayoutParams(layoutParams1);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);

        linearLayout.addView(avLoadingIndicatorView);
        linearLayout.addView(messageTxt);
        dialog.setContentView(linearLayout);

        //dialog.setContentView(avLoadingIndicatorView);
        //dialog.addContentView(messageTxt,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));



        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showLoading(Context context, String message) {
        showLoading(context, DEFAULT_LOADER, message);}

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER,"");
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}
