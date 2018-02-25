package com.leonidas.zt.bycs.group.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonidas.zt.bycs.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 华强 on 2018/1/5.
 * Version: V1.0
 * Description: 拼购搜索Activity
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseSearchActivity extends AppCompatActivity {

    private ImageView iv_back; //返回
    private EditText et_search_content; //搜索的内容
    private ImageView iv_search; //搜索按钮
    private TagFlowLayout tfl_hot_search; //热搜
    private LayoutInflater mInflater = getLayoutInflater();
    private Context mContext = this;
    List<String> HotSearchList = new ArrayList<>(); //热搜关键词
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leonidas_activity_group_purchase_search);
        //初始化
        iv_back = findViewById(R.id.iv_back);
        et_search_content = findViewById(R.id.et_search_content);
        iv_search = findViewById(R.id.iv_search);
        tfl_hot_search = findViewById(R.id.tfl_hot_search);
        tfl_hot_search.setBackgroundColor(Color.parseColor("#FFFFFF"));
        initHotSearchData(); //加载热销数据
    }

    /**
     * 加载热销数据
     */
    private void initHotSearchData() {
        //1.加载数据


        //2.设置适配器 --- 先加载完成数据后再设置适配器
        tfl_hot_search.setAdapter(new TagAdapter(HotSearchList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.leonidas_tv_hot_search, tfl_hot_search, false);
                tv.setText(HotSearchList.get(position));
                return tv;
            }
        });
    }
}
