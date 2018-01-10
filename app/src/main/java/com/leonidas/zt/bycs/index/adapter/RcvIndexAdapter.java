package com.leonidas.zt.bycs.index.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leonidas.zt.bycs.R;

/**
 * Created by mebee on 2018/1/7.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */
public class RcvIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum ITEM_VIEW_TYPE{
        SORT,
        SHOP
    }

    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == ITEM_VIEW_TYPE.SORT.ordinal()){
            view = layoutInflater.inflate(R.layout.mebee_rcv_index_view,parent,false);
            return new SortViewHolder(view);
        } else {
            view = layoutInflater.inflate(R.layout.mebee_rcv_index_view,parent,false);
            return new ShopsViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_VIEW_TYPE.SORT.ordinal() : ITEM_VIEW_TYPE.SHOP.ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SortViewHolder) {
            ((SortViewHolder) holder).recyclerView.setAdapter(new RcvSortAdapter());
            ((SortViewHolder) holder).recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        } else if (holder instanceof ShopsViewHolder) {
            ((ShopsViewHolder) holder).recyclerView.setAdapter(new RcvShopAdapter());
            ((ShopsViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }


    class SortViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerView;

        public SortViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rcv_index_rcv_content);
        }
    }

    class ShopsViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerView;

        public ShopsViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rcv_index_rcv_content);
        }
    }
}
