package com.leonidas.zt.bycs.index.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.leonidas.zt.bycs.R;

public class ProductDetialActivity extends AppCompatActivity {
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_product_detial);
        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar_product_detail);
        initToolbar();
    }

    private void initToolbar() {
        mToolBar.inflateMenu(R.menu.product_detail_toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
