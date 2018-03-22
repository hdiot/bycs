package com.leonidas.zt.bycs.user.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;


/**
 * Created by mebee on 2018/2/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class BadgeBar extends RelativeLayout {

    private TextView mDescribeTxt;
    private TextView mNumTxt;
    private ImageView mImg;
    private int mNum = 0;
    private LayoutInflater mInflater;
    private View mView;

    public BadgeBar(Context context) {
        this(context,null);
    }

    public BadgeBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BadgeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);

        initAttrs(context,attrs,defStyleAttr);


    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            @SuppressLint("RestrictedApi")
            final TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs,
                    R.styleable.BadgeBar,defStyleAttr,0);
            @SuppressLint("RestrictedApi")
            final Drawable bar_src = tintTypedArray.getDrawable(R.styleable.BadgeBar_src);
            if (bar_src != null) {
                mImg.setImageDrawable(bar_src);
            }
            @SuppressLint("RestrictedApi")
            final String describe = tintTypedArray.getString(R.styleable.BadgeBar_describe);
            if (describe != null){
                mDescribeTxt.setText(describe);
            }
            @SuppressLint("RestrictedApi")
            final String num = tintTypedArray.getString(R.styleable.BadgeBar_num);
            if (num != null){
                mNumTxt.setText(num);
            }

            @SuppressLint("RestrictedApi")
            final int num_visibility = tintTypedArray.getInteger(R.styleable.BadgeBar_num_visibility,0x08);
            mNumTxt.setVisibility(num_visibility);


        }
    }

    private void initView(Context context) {
        mInflater = LayoutInflater.from(context);
        mView = mInflater.inflate(R.layout.mebee_img_txt_badge_bar,this,true);

        mDescribeTxt = mView.findViewById(R.id.txt_bar);
        mNumTxt = mView.findViewById(R.id.num_bar);
        mImg = mView.findViewById(R.id.img_bar);


    }


    public void setMessage(int num){

    }

    public boolean addNum(){
        return false;
    }

    public boolean subNum(){
        return false;
    }

    public int getNum(){
        return mNum;
    }

}
