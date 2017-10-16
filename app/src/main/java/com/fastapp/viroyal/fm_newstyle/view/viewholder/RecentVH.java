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
    @Bind(R.id.recent_album_title)
    public TextView mAlbumTitle;
    @Bind(R.id.recent_track_title)
    public TextView mTrackTitle;
    @Bind(R.id.recent_play_state)
    public TextView mPlayState;

    public RecentVH(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
