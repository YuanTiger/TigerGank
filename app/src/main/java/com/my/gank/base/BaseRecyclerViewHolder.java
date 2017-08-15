package com.my.gank.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/8/31/下午5:20
 * DESC:
 */
public class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {



    public BaseRecyclerViewHolder(int viewId, ViewGroup parent, int viewType) {
        super(((LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE)).inflate(viewId, parent,false));
        ButterKnife.bind(this,itemView);
    }

    public void refreshData(T data, int position) {

    }



}
