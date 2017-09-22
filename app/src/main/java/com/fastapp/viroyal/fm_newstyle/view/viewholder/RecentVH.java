package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentVH extends RecyclerView.ViewHolder{
    @Bind(R.id.recent_image)
    public ImageView mRecentImg;
    @Bind(R.id.recent_title)
    public TextView mRecentTitle;
    @Bind(R.id.recent_intro)
    public TextView mRecentIntro;
    @Bind(R.id.recent_plays_counts)
    public TextView mPlayCounts;
    @Bind(R.id.recent_tracks_counts)
    public TextView mCounts;

    public RecentVH(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
