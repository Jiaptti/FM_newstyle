package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationVH extends RecyclerView.ViewHolder {
    @Bind(R.id.navigation_img)
    public ImageView navigationImg;
    @Bind(R.id.navigation_title)
    public TextView navigationTitle;

    public NavigationVH(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
