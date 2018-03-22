package com.leonidas.zt.bycs.app.ui.error;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.leonidas.zt.bycs.R;

import org.w3c.dom.Text;


/**
 * Created by mebee on 2018/3/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class ErrorLayout extends LinearLayout {

    private View errorLayout;
    private AVLoadingIndicatorView loadingIndicatorView;
    private TextView errorMessage;
    private Button toDoBtn;

    public ErrorLayout(Context context) {
        this(context, null);
    }

    public ErrorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (errorLayout == null) {
            errorLayout = LayoutInflater.from(context).inflate(R.layout.mebee_error_layout, this, true);
            loadingIndicatorView = (AVLoadingIndicatorView) errorLayout.findViewById(R.id.loading_indicator_error_layout);
            errorMessage = (TextView) errorLayout.findViewById(R.id.txt_error_message_error_layout);
            toDoBtn = (Button) errorLayout.findViewById(R.id.btn_error_layout);
        }

        initAttrs(context, attrs, defStyleAttr);
    }

    @SuppressLint("RestrictedApi")
    private void initAttrs(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            final TintTypedArray typedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.Error_Layout, defStyleAttr, 0);
            final CharSequence text_message = typedArray.getText(R.styleable.Error_Layout_error_message_text);
            final int size_message = typedArray.getInteger(R.styleable.Error_Layout_error_message_text_size, 12);
            final int color_message = typedArray.getColor(R.styleable.Error_Layout_error_message_text_color, Color.GRAY);
            final int visibility_message = typedArray.getInt(R.styleable.Error_Layout_error_message_visibility, 0);
            final int width_load_indicator = typedArray.getInteger(R.styleable.Error_Layout_load_indicator_width, 80);
            final int height_load_indicator = typedArray.getInteger(R.styleable.Error_Layout_load_indicator_height, 80);
            final int style_load_indicator = typedArray.getInt(R.styleable.Error_Layout_load_indicator_style, 0);
            final int visibility_load_indicator = typedArray.getInt(R.styleable.Error_Layout_load_indicator_visibility, 0);
            final Drawable drawable_todo_background = typedArray.getDrawable(R.styleable.Error_Layout_to_btn_background);
            final int visibility_todo_btn = typedArray.getInt(R.styleable.Error_Layout_to_btn_visibility, 8);
            Log.d("style_load_indicator", "initAttrs: " + style_load_indicator);
            setErrorMessage(text_message);
            setErrorMessageColor(color_message);
            setErrorMessageSize(size_message);
            setErrorMessageVisibility(visibility_message);
            setLoadingIndicatorViewHeight(height_load_indicator);
            setLoadingIndicatorViewWidth(width_load_indicator);
            setLoadingIndicatorVisibility(visibility_load_indicator);
            setLoadingIndicatorStyle(style_load_indicator);
            setToDoBtnVisibility(visibility_todo_btn);
            setToDoBtnBackground(drawable_todo_background);

        }
    }

    private void setLoadingIndicatorStyle(int indicator) {
        loadingIndicatorView.setIndicatorId(indicator);
    }

    private void setToDoBtnBackground(Drawable drawable) {
        toDoBtn.setBackground(drawable);
    }

    public void setErrorMessage(CharSequence message) {
        errorMessage.setText(message);
    }

    public void setErrorMessageColor(int color) {
        errorMessage.setTextColor(color);
    }

    public void setErrorMessageSize(float size) {
        errorMessage.setTextSize(size);
    }

    public void setLoadingIndicatorVisibility(int v) {
        Log.e("Visibility", "LoadVisibility: " + v);
        loadingIndicatorView.setVisibility(v);
    }

    public void setLoadingIndicatorViewWidth(int width) {
        if (loadingIndicatorView != null) {
            ViewGroup.LayoutParams layoutParams = loadingIndicatorView.getLayoutParams();
            layoutParams.width = width;
        }
    }

    public void setLoadingIndicatorViewHeight(int height) {
        if (loadingIndicatorView != null) {
            ViewGroup.LayoutParams layoutParams = loadingIndicatorView.getLayoutParams();
            layoutParams.height = height;
        }
    }

    public void setErrorMessageVisibility(int v) {
        Log.d("MessageVisibility", "setErrorMessageVisibility: " + v);
        errorMessage.setVisibility(v);
    }

    public void setToDoBtnVisibility(int v) {
        Log.e("Visibility", "setToDoBtnVisibility: " + v);
        toDoBtn.setVisibility(v);
    }

    public void setToDoBtnOnClickListener(OnClickListener listener) {
        toDoBtn.setOnClickListener(listener);
    }
}
