package com.fastapp.viroyal.fm_newstyle.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.NavigationVH;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationAdapter extends RecyclerView.Adapter<NavigationVH>{
    private Context mContext;
    private List<NavigationBean> list;

    public NavigationAdapter(Context context, List<NavigationBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public NavigationVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NavigationVH(LayoutInflater.from(mContext).inflate(R.layout.navigation_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(NavigationVH holder, int position) {
        NavigationBean bean = list.get(position);
        ImageUtils.loadImage(mContext, bean.getCoverPath(), holder.navigationImg);
        holder.navigationTitle.setText(bean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
