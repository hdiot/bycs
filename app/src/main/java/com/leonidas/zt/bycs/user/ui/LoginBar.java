package com.leonidas.zt.bycs.user.ui;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leonidas.zt.bycs.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mebee on 2018/2/8.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class LoginBar extends LinearLayout {
    private EditText mTelEdit;
    private EditText mCodeEdit;
    private ImageView mTelClearImg;
    private ImageView mCodeClearImg;
    private ImageView mVisibleImg;
    private View mView;
    private Button mGetCodeBtn;

    private GetCodeListener mGetCodeListener;

    private CountTimer mCountTimer = new CountTimer(60000,1000);
    private boolean isGetCodeTimerFinish = true;

    public interface GetCodeListener{
        void getCode();
    }

    public void setGetCodeListener(@NonNull GetCodeListener getCodeListener) {
        this.mGetCodeListener = getCodeListener;
    }


    public LoginBar(Context context) {
        this(context,null);
    }

    public LoginBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {

    }


    private void initView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.mebee_login_bar,this, true);
        mCodeEdit = mView.findViewById(R.id.edit_code);
        mTelEdit = mView.findViewById(R.id.edit_tel);
        mVisibleImg = mView.findViewById(R.id.img_visible);
        mCodeClearImg = mView.findViewById(R.id.img_delete_code);
        mTelClearImg = mView.findViewById(R.id.img_delete_tel);
        mGetCodeBtn = mView.findViewById(R.id.btn_get_code);

        initCodeEdit();
        initTelEdit();
        initClearImg();
        initVisibleImg();
        initGetCodeBtn();
    }

    private void initGetCodeBtn() {
        mGetCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountTimer.start();
                isGetCodeTimerFinish = false;
                mGetCodeBtn.setEnabled(false);
                if (mGetCodeListener != null) {
                    mGetCodeListener.getCode();
                }
            }
        });
    }


    private void initVisibleImg() {
        mVisibleImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initClearImg() {
        mCodeClearImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeEdit.setText("");
                mCodeEdit.clearFocus();
            }
        });

        mTelClearImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTelEdit.setText("");
                mTelEdit.clearFocus();
            }
        });
    }

    private void initTelEdit() {
        mTelEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                mGetCodeBtn.setEnabled(checkMobile(content) && isGetCodeTimerFinish);
                if (content == null || content.equals("")) {
                    mTelClearImg.setVisibility(GONE);
                } else {
                    mTelClearImg.setVisibility(VISIBLE);
                }
            }
        });
    }

    public boolean checkMobile(String mobiles) {
        Pattern p = Pattern
                .compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    private void initCodeEdit() {
        mCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (content == null || content.equals("")) {
                    mCodeClearImg.setVisibility(GONE);
                } else {
                    mCodeClearImg.setVisibility(VISIBLE);
                }
            }
        });
    }

    public String getCodeTxt(){
        return mCodeEdit.getText().toString();
    }

    public String getTelTxt(){
        return mTelEdit.getText().toString();
    }

    private class CountTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mGetCodeBtn.setText(millisUntilFinished/1000+"秒后重新获取");
        }

        @Override
        public void onFinish() {
            mGetCodeBtn.setEnabled(checkMobile(mTelEdit.getText().toString()));
            mGetCodeBtn.setText("重新获取");
            isGetCodeTimerFinish = true;
        }
    }

}
