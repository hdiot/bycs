package com.leonidas.zt.bycs.user.fragment;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.user.activity.LoginActivity;
import com.leonidas.zt.bycs.user.activity.OrdersActivity;
import com.leonidas.zt.bycs.user.ui.BadgeBar;

import java.util.logging.Handler;

/**
 * Created by mebee on 2018/1/18.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class UserFragment extends BaseFragment {
    private static final String TAG = "UserFragment";
    private Toolbar mToolbar;
    private BadgeBar mWait4Paying;
    private BadgeBar mWait4Grouping;
    private BadgeBar mWait4Posting;
    private BadgeBar mWait4Receiving;
    private BadgeBar mWait4Evaluating;
    private BadgeBar mPostAddr;
    private LinearLayout mAllOrderLayout;
    private LinearLayout mLoginOrUser;

    @Override
    public void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar_mine);
        mAllOrderLayout = view.findViewById(R.id.layout_all_order);

        mWait4Paying = view.findViewById(R.id.wait4paying);
        mWait4Grouping = view.findViewById(R.id.wait4grouping);
        mWait4Posting = view.findViewById(R.id.wait4posting);
        mWait4Receiving = view.findViewById(R.id.wait4receiving);
        mWait4Evaluating = view.findViewById(R.id.wait4evaluating);
        mAllOrderLayout = view.findViewById(R.id.layout_all_order);
        mLoginOrUser = view.findViewById(R.id.layout_login);
        mPostAddr = view.findViewById(R.id.post_addr);

        initToLogin();
        initToolabr();
        initOrderCategories();
    }

    private void initToLogin() {
        mLoginOrUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }

    private void initOrderCategories() {
        mAllOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrdersActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Paying", Toast.LENGTH_SHORT).show();
            }
        });

        mWait4Paying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OrdersActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Paying", Toast.LENGTH_SHORT).show();
            }
        });
        mWait4Grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OrdersActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Grouping", Toast.LENGTH_SHORT).show();
            }
        });
        mWait4Posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OrdersActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Posting", Toast.LENGTH_SHORT).show();
            }
        });
        mWait4Receiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OrdersActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Receiving", Toast.LENGTH_SHORT).show();
            }
        });
        mWait4Evaluating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OrdersActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "mWait4Evaluating", Toast.LENGTH_SHORT).show();
            }
        });
        mPostAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "mPostAddr", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolabr() {
        mToolbar.inflateMenu(R.menu.toolbar_setting_only);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(mContext, "setting clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_mine;
    }
}
