package com.fastapp.viroyal.fm_newstyle.view.adapter.recent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.ui.album.AlbumActivity;
import com.fastapp.viroyal.fm_newstyle.ui.track.TrackActivity;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.util.JsonUtils;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.RecentVH;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentAdapter extends RecyclerView.Adapter<RecentVH> {
    private Context mContext;
    private List<HimalayanEntity> list;

    public RecentAdapter(Context context, List<HimalayanEntity> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecentVH(LayoutInflater.from(mContext).inflate(R.layout.recent_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecentVH holder, int position) {
        final HimalayanEntity entity = list.get(position);
        if (!entity.isIsPaid()) {
            ImageUtils.loadImage(mContext, entity.getCoverSmall(), holder.mRecentImg);
            holder.mRecentTitle.setText(entity.getTitle());
            holder.mRecentIntro.setText(entity.getIntro());
            holder.mCounts.setText(CommonUtils.getOmitPlayCounts(entity.getPlaysCounts()));
            holder.mPlayCounts.setText(entity.getTracks() + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TrackActivity.class);
                    intent.putExtra(AppConstant.FROM_RECENT, true);
                    intent.putExtra(AppConstant.TRACK_ID, entity.getTrackId());
                    mContext.startActivity(intent);
                    JsonUtils.cleanData();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
