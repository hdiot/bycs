package com.leonidas.zt.bycs.group.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.leonidas.zt.bycs.R;
import com.leonidas.zt.bycs.group.utils.Api;
import com.leonidas.zt.bycs.group.utils.ApiParamKey;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsClassificationVO;
import com.leonidas.zt.bycs.group.vo.GroupPurchaseGoodsListVO;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 华强 on 2018/1/6.
 * Version: V1.0
 * Description: “拼购商城”模块RecyclerView的适配器
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseRvAdapter extends XRecyclerView.Adapter{
    private static final String TAG = "GroupPurchaseRvAdapter";

    //绑定到此Adapter上的XRecyclerView
    private XRecyclerView mRvGroupPurchase;

    //分类数据集
    private List<GroupPurchaseGoodsClassificationVO.DataBean.ProductCategoriesBean.ListBean> ClassificationDataList = new ArrayList<>();
    //拼购商品数据的数据集
    private List<GroupPurchaseGoodsListVO.DataBean.GroupProductsBean.ListBean> GroupPurchaseGoodsDataList = new ArrayList<>();

    //分类RecyclerView的的分页信息
    private int CNexPageNum = 1;
    private int CPageSize = 10;
    //拼购商品RecyclerView的的分页信息
    private int GpgNextPageNum = 1;
    private int GpgPageSize = 10;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private RecyclerView rvClassification; //分类RecyclerView
    private RecyclerView rvGroupPurchaseGoods; //拼购商品XRecyclerView

    public GroupPurchaseRvAdapter(Context context, XRecyclerView RvGroupPurchase) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRvGroupPurchase = RvGroupPurchase;
    }

    /**
     * 获取“分类RecyclerView”下一页面“页数”
     * @return 下一页面“页数”
     */
    public int getCNexPageNum() {
        return CNexPageNum;
    }

    /**
     * 获取“分类RecyclerView”的每一页的页面大小
     * @return 页面大小
     */
    public int getCPageSize() {
        return CPageSize;
    }

    /**
     * 获取“拼购商品RecyclerView”下一页面“页数”
     * @return 下一页面“页数”
     */
    public int getGpgNextPageNum() {
        return GpgNextPageNum;
    }

    /**
     * 获取“拼购商品RecyclerView”的每一页的页面大小
     * @return 页面大小
     */
    public int getGpgPageSize() {
        return GpgPageSize;
    }


    /**
     * 获取“分类分类RecyclerView”
     * @return 分类RecyclerView
     */
    public RecyclerView getRvClassification() {
        return rvClassification;
    }

    /**
     * 获取“拼购商品XRecyclerView”
     * @return 拼购商品XRecyclerView
     */
    public RecyclerView getRvGroupPurchaseGoods() {
        return rvGroupPurchaseGoods;
    }

    /**
     * ViewHolder类型
     */
    public enum VHType {
        Classification, //“商品分类”
        GroupPurchaseGoods //“拼购商品”
    }

    /**
     * 当XRecyclerView从网络请求数据操作时的不同操作，分别有：1.第一次加载（FIRST）；2.下拉刷新(REFRESH)；3.加载更多（MORE）。
     */
    public enum DataOperation {
        FIRST, //第一次加载数据操作
        REFRESH, //下拉刷新操作
        MORE //加载更多操作
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VHType.Classification.ordinal()) {
            return new ClassificationViewHolder(mLayoutInflater.inflate(R.layout.leonidas_rv_normal, null));
        } else if (viewType == VHType.GroupPurchaseGoods.ordinal()) {
            return new GroupPurchaseGoodsViewHolder(mLayoutInflater.inflate(R.layout.leonidas_rv_normal, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int ItemViewType = getItemViewType(position); //获取ViewHolder类型
        if (ItemViewType == VHType.Classification.ordinal()) {
            ClassificationViewHolder mHolder = (ClassificationViewHolder) holder;
            mHolder.setData();
        } else if (ItemViewType == VHType.GroupPurchaseGoods.ordinal()) {
            GroupPurchaseGoodsViewHolder mHolder = (GroupPurchaseGoodsViewHolder) holder;
            mHolder.setData();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int ItemViewType = 0;

        if (position == 0)
            ItemViewType = VHType.Classification.ordinal();
        else if (position == 1)
            ItemViewType = VHType.GroupPurchaseGoods.ordinal();

        return ItemViewType;
    }

    /**
     * 分类ViewHolder
     */
    private class ClassificationViewHolder extends RecyclerView.ViewHolder {

        public ClassificationViewHolder(View itemView) {
            super(itemView);
            //初始化RecyclerView
            rvClassification = itemView.findViewById(R.id.rv_normal);
            rvClassification.setAdapter(new ClassificationRvAdapter(mContext, ClassificationDataList));
            rvClassification.setLayoutManager(new GridLayoutManager(mContext, 4));
        }

        /**
         * 给ViewHolder添加数据
         */
        public void setData() {
            initClassificationDate(CNexPageNum, CPageSize, DataOperation.FIRST.ordinal());
        }
    }//VH结束

    /**
     * 拼购商品ViewHolder
     */
    private class GroupPurchaseGoodsViewHolder extends RecyclerView.ViewHolder {

        public GroupPurchaseGoodsViewHolder(View itemView) {
            super(itemView);
            //初始化RecyclerView
            rvGroupPurchaseGoods = itemView.findViewById(R.id.rv_normal);
            rvGroupPurchaseGoods.setAdapter(new GroupPurchaseGoodsRvAdapter(mContext, GroupPurchaseGoodsDataList));
            rvGroupPurchaseGoods.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        }

        /**
         * 给ViewHolder添加数据
         */
        public void setData() {
            initGroupPurchaseGoodsDate(GpgNextPageNum, GpgPageSize, 1515557064589L, DataOperation.FIRST.ordinal());
        }
    }//VH结束

    /**
     * 加载分类数据
     *
     * @param PageNum   页面数
     * @param PageSize  页面大小
     * @param Operation 操作方式（有三种操作方式：1.第一次加载（FIRST）；2.下拉刷新(REFRESH)；3.加载更多（MORE）。）
     */
    public void initClassificationDate(final int PageNum, int PageSize, final int Operation) {
        OkHttpUtils.get()
                .url(Api.QueryGroupPurchaseGoodsClassification)
                .addParams(ApiParamKey.PageNum, PageNum + "")
                .addParams(ApiParamKey.PageSize, PageSize + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                //解析数据
                GroupPurchaseGoodsClassificationVO datas = JSON.parseObject(response, GroupPurchaseGoodsClassificationVO.class);

                if (datas.getData() == null) {//数据出错
                    Toast.makeText(mContext, "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (datas.getCode() != 1) {//数据获取失败
                    Toast.makeText(mContext, "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Operation == DataOperation.FIRST.ordinal()) {//第一次获取数据
                    if (rvClassification.getAdapter() != null) {
                        ClassificationDataList.addAll(datas.getData().getProductCategories().getList());
                        rvClassification.getAdapter().notifyDataSetChanged();
                        CNexPageNum++;
                    }
                } else if (Operation == DataOperation.REFRESH.ordinal()) {//下拉刷新数据
                    if (rvClassification.getAdapter() == null) {
                        ClassificationDataList.addAll(datas.getData().getProductCategories().getList());
                        rvClassification.getAdapter().notifyDataSetChanged();
                        CNexPageNum++;
                    } else {
                        ClassificationDataList.clear();
                        ClassificationDataList.addAll(datas.getData().getProductCategories().getList());
                        rvClassification.getAdapter().notifyDataSetChanged();
                    }
                    //初始化分类部分的页面信息
                    CNexPageNum = 1;
                    mRvGroupPurchase.refreshComplete(); //RecyclerView的下拉刷新完成

                } else if (Operation == DataOperation.MORE.ordinal()) {//(上拉)加载更多的数据
                    RecyclerView.Adapter adapter = rvClassification.getAdapter();
                    if (adapter != null) {
                        ClassificationDataList.addAll(datas.getData().getProductCategories().getList());
                        adapter.notifyDataSetChanged();
                        //更新分类部分的页面信息
                        CNexPageNum++;
                    }
                    mRvGroupPurchase.loadMoreComplete();//RecyclerView的加载更多完成
                }

            }
        });
    }

    /**
     * 加载拼购商品数据
     *
     * @param PageNum    页面数
     * @param PageSize   页面大小
     * @param CategoryId 分类ID
     * @param Operation  操作方式（有三种操作方式：1.第一次加载（FIRST）；2.下拉刷新(REFRESH)；3.加载更多（MORE）。）
     */
    public void initGroupPurchaseGoodsDate(int PageNum, int PageSize, long CategoryId, final int Operation) {
        OkHttpUtils.get()
                .url(Api.QueryGroupMultiPurchaseGoods)
                .addParams(ApiParamKey.PageNum, PageNum + "")
                .addParams(ApiParamKey.PageSize, PageSize + "")
                .addParams(ApiParamKey.CategoryId, CategoryId + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, response);
                GroupPurchaseGoodsListVO datas = JSON.parseObject(response, GroupPurchaseGoodsListVO.class);

                if (datas.getData() == null) {//数据出错
                    //Toast.makeText(mContext, "数据错误，请联系客服！", Toast.LENGTH_SHORT).show();
                    mRvGroupPurchase.setNoMore(true);
                    return;
                }

                if (datas.getCode() != 1) {//数据获取失败
                    Toast.makeText(mContext, "数据获取失败，请联系客服解决！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Operation == DataOperation.FIRST.ordinal()) {//第一次获取数据
                    if (rvGroupPurchaseGoods.getAdapter() != null) {
                        GroupPurchaseGoodsDataList.addAll(datas.getData().getGroupProducts().getList());
                        rvGroupPurchaseGoods.getAdapter().notifyDataSetChanged();
                        GpgNextPageNum++;

                    }
                } else if (Operation == DataOperation.REFRESH.ordinal()) {//下拉刷新的数据
                    if (rvGroupPurchaseGoods.getAdapter() == null) {
                        GroupPurchaseGoodsDataList.addAll(datas.getData().getGroupProducts().getList());
                        rvGroupPurchaseGoods.getAdapter().notifyDataSetChanged();
                        GpgNextPageNum++;
                    } else {
                        GroupPurchaseGoodsDataList.clear();
                        GroupPurchaseGoodsDataList.addAll(datas.getData().getGroupProducts().getList());
                        rvGroupPurchaseGoods.getAdapter().notifyDataSetChanged();
                    }
                    //初始化分类部分的页面信息
                    GpgNextPageNum = 1;
                    mRvGroupPurchase.refreshComplete(); //RecyclerView的下拉刷新完成

                } else if (Operation == DataOperation.MORE.ordinal()) {//加载更多的数据
                    RecyclerView.Adapter adapter = rvGroupPurchaseGoods.getAdapter();
                    if (adapter != null) {
                        GroupPurchaseGoodsDataList.addAll(datas.getData().getGroupProducts().getList());
                        adapter.notifyDataSetChanged();
                        //更新分类部分的页面信息
                        GpgNextPageNum++;
                    }
                    mRvGroupPurchase.loadMoreComplete();//RecyclerView的加载更多完成
                }

            }
        });
    }

}
