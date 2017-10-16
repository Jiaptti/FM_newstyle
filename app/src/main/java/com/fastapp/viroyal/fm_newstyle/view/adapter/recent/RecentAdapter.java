package com.fastapp.viroyal.fm_newstyle.view.adapter.recent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksInfo;
import com.fastapp.viroyal.fm_newstyle.ui.track.TrackActivity;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.util.JsonUtils;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.RecentVH;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentAdapter extends RecyclerView.Adapter<RecentVH> {
    private Context mContext;
    private List<TracksBeanList> list;
    private RealmHelper helper;
    private RxManager manager;

    public RecentAdapter(Context context, List<TracksBeanList> list,RxManager manager) {
        this.mContext = context;
        this.list = list;
        this.manager = manager;
        helper = AppContext.getRealmHelper();
    }

    @Override
    public RecentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecentVH(LayoutInflater.from(mContext).inflate(R.layout.recent_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecentVH holder, int position) {
        final TracksBeanList entity = list.get(position);
        if (!entity.isPaid()) {
            ImageUtils.loadImage(mContext, entity.getCoverSmall(), holder.mRecentImg);
            if(helper.getNowPlayingTrack().getTrackId() == entity.getTrackId()
                    && AppContext.getPlayState() == AppConstant.STATUS_PLAY){
                holder.mPlayState.setText(R.string.playing_state);
            } else {
                holder.mPlayState.setVisibility(View.GONE);
            }
            holder.mAlbumTitle.setText(entity.getAlbumTitle());
            holder.mTrackTitle.setText(entity.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TrackActivity.class);
                    intent.putExtra(AppConstant.TRACK_BUNDLE, entity);
                    mContext.startActivity(intent);
                    manager.post(AppConstant.PLAY_RECENT_STATE_CHANTE, null);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
