package com.leonidas.zt.bycs.index.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.app.fragment.BaseFragment;
import com.leonidas.zt.bycs.index.adapter.RcvIndexAdapter;


@SuppressLint("ValidFragment")
public class IndexFragment extends BaseFragment {

    private static final String TAG = "IndexFragment";
    private final String mString;
    private Toolbar mToolBar;
    private RecyclerView mRcvIndex;

    @SuppressLint("ValidFragment")
    public IndexFragment(String s) {
        super();
        this.mString = s;
    }


    @Override
    public void initView(View view) {
        mToolBar = (Toolbar) view.findViewById(R.id.index_toolbar);
        mRcvIndex = (RecyclerView) view.findViewById(R.id.rcv_index);

        initToolbar();
        initRcvRcmShop();
    }

    /**
     * 初始化 商家列表
     */
    private void initRcvRcmShop() {
        Log.d(TAG, "initRcvRcmShop: ");
        mRcvIndex.setAdapter(new RcvIndexAdapter());
        mRcvIndex.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * 初始化 顶部 ToolBar
     */
    private void initToolbar() {
        mToolBar.inflateMenu(R.menu.index_toolbar);
        EditText mSearchEditText = (EditText) mToolBar.findViewById(R.id.edit_search);
        // mSearchEditText.clearFocus();
        mSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Search was clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.mebee_fragment_index;
    }


}
