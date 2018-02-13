package com.leonidas.zt.bycs.index.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;

public class SearchActivity extends AppCompatActivity {

    private Toolbar mToobar;
    private SearchView mSearchView;
    private TabLayout mTab;
    private XRecyclerView mXrcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mebee_activity_search);
        initView();
    }

    private void initView() {
        mToobar = findViewById(R.id.toolbar_sort);
        mTab = findViewById(R.id.tab_sort);
        mSearchView = findViewById(R.id.searchView_sort);
        mXrcv = findViewById(R.id.xrcv_sort);

        initToolbar();
        initSearchView();
        initTab();
        initXRCV();
    }

    private void initSearchView() {

    }

    private void initXRCV() {

    }

    private void initTab() {
    }

    private void initToolbar() {
    }
}
