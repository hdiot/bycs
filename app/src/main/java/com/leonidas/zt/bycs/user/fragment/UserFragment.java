package com.leonidas.zt.bycs.user.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.user.User;
import com.leonidas.zt.bycs.user.activity.LoginActivity;
import com.leonidas.zt.bycs.user.activity.OrdersActivity;
import com.leonidas.zt.bycs.user.activity.SettingActivity;
import com.leonidas.zt.bycs.user.bean.UserInfo;
import com.leonidas.zt.bycs.user.ui.BadgeBar;

/**
 * Created by mebee on 2018/1/18.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "UserFragment";
    private Toolbar mToolbar;
    private BadgeBar mWait4Paying;
    private BadgeBar mWait4Grouping;
    private BadgeBar mWait4Posting;
    private BadgeBar mWait4Receiving;
    private BadgeBar mWait4Evaluating;
    private BadgeBar mPostAddr;
    private LinearLayout mAllOrderLayout;
    private SuperTextView mLoginOrUser;
    private boolean isLogin = false;

    @Override
    public void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_mine);
        mAllOrderLayout = (LinearLayout) view.findViewById(R.id.layout_all_order);

        mWait4Paying = (BadgeBar) view.findViewById(R.id.wait4paying);
        mWait4Grouping = (BadgeBar) view.findViewById(R.id.wait4grouping);
        mWait4Posting = (BadgeBar) view.findViewById(R.id.wait4posting);
        mWait4Receiving = (BadgeBar) view.findViewById(R.id.wait4receiving);
        mWait4Evaluating = (BadgeBar) view.findViewById(R.id.wait4evaluating);
        mAllOrderLayout = (LinearLayout) view.findViewById(R.id.layout_all_order);
        mLoginOrUser = (SuperTextView) view.findViewById(R.id.user_user_super_txt);
        mPostAddr = (BadgeBar) view.findViewById(R.id.post_addr);

        setEnable(false);
        initToolbar();
        initUser();
        setClickListener();
    }

    private void initUser(){
        try {
            User user = User.getInstance();
            UserInfo userInfo = user.getUserInfo();
            isLogin = user.isLogin();
            setEnable(isLogin);
            mLoginOrUser.getLeftTextView().setText(userInfo.getUserName());
            mLoginOrUser.getLeftBottomTextView().setText(encodePhone(userInfo.getUserPhone()));

        } catch (NullPointerException e){
            // TODO: 2018/3/15

        }
    }

    private void setClickListener() {
        mWait4Paying.setOnClickListener(this);
        mWait4Grouping.setOnClickListener(this);
        mWait4Posting.setOnClickListener(this);
        mWait4Receiving.setOnClickListener(this);
        mWait4Evaluating.setOnClickListener(this);
        mPostAddr.setOnClickListener(this);
        mAllOrderLayout.setOnClickListener(this);

        mLoginOrUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.getInstance().isLogin()) {
                    startActivity(new Intent(mContext, SettingActivity.class));
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });
    }

    private void setEnable(boolean enable) {

        mWait4Paying.setEnabled(enable);
        mWait4Grouping.setEnabled(enable);
        mWait4Posting.setEnabled(enable);
        mWait4Receiving.setEnabled(enable);
        mWait4Evaluating.setEnabled(enable);
        mPostAddr.setEnabled(enable);
        mAllOrderLayout.setEnabled(enable);

    }

    private String encodePhone(String phone){
        try{
            StringBuffer buffer = new StringBuffer(phone);
            buffer.replace(3,7, "****");
            return buffer.toString();
        }catch (StringIndexOutOfBoundsException e){

            return "";
        }
    }

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.toolbar_setting_only);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (User.getInstance() != null) {
                    Intent intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_mine;
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.wait4evaluating:
                intent = new Intent(getContext(), OrdersActivity.class);
                intent.putExtra("userId", "");
                intent.putExtra("index", 5);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Evaluating", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait4grouping:
                intent = new Intent(getContext(), OrdersActivity.class);
                intent.putExtra("index", 2);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Grouping", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait4paying:
                intent = new Intent(getContext(), OrdersActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Paying", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait4posting:
                intent = new Intent(getContext(), OrdersActivity.class);
                intent.putExtra("index", 3);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Posting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait4receiving:
                intent = new Intent(getContext(), OrdersActivity.class);
                intent.putExtra("index", 4);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Receiving", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_all_order:
                intent = new Intent(getContext(), OrdersActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Paying", Toast.LENGTH_SHORT).show();
                break;
            case R.id.post_addr:
                Toast.makeText(mContext, "mPostAddr", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUser();
    }


}
