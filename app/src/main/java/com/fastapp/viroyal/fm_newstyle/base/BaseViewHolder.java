package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fastapp.viroyal.fm_newstyle.util.ViewUtil;

import butterknife.ButterKnife;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{
    public Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        ViewUtil.autoFind(this, itemView);//id与name一致
    }

    /**
     * ViewHolder的Type，同时也是它的LayoutId
     */
    public abstract int getType();

    /**
     * 绑定ViewHolder
     */
    public abstract void onBindViewHolder(View view, T obj);
}
