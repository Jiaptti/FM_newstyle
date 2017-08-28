package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/8/25.
 */

public class TrackListVH extends BaseViewHolder<TracksBeanList>{
    private SquareImageView mWaveFlag;
    private TextView mTrackName;
    private AnimationDrawable animation;
    private RealmHelper helper = AppContext.getRealmHelper();
    private RxManager manager = new RxManager();
    private AlbumPlayService.PlayBinder mBinder = AppContext.getMediaPlayService();

    public TrackListVH(View itemView) {
        super(itemView);
        if (itemView instanceof LinearLayout) {
            manager.on(AppConstant.MEDIA_START_PLAY, new Action1() {
                @Override
                public void call(Object o) {
                    if (o instanceof Integer) {
                        switch ((Integer) o) {
                            case AppConstant.STATUS_PLAY:
                            case AppConstant.STATUS_RESUME:
                                animation.start();
                                break;
                            case AppConstant.STATUS_PAUSE:
                                animation.stop();
                                mTrackName.setTextColor(Color.BLACK);
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void initViewHolder(View itemView) {
        mWaveFlag = (SquareImageView) itemView.findViewById(R.id.track_item_wave_flag);
        mTrackName = (TextView) itemView.findViewById(R.id.track_item_name);
    }

    @Override
    public int getType() {
        return R.layout.play_list_popup_item_layout;
    }

    @Override
    public void onBindViewHolder(View view, TracksBeanList entity) {
        animation = (AnimationDrawable) mWaveFlag.getBackground();
        mTrackName.setText(entity.getTitle());
        setPlayStatus(entity);
    }

    private void setPlayStatus(TracksBeanList entity) {
        if (helper.getNowPlayingTrack() != null &&
                helper.getNowPlayingTrack().getTitle().trim().equalsIgnoreCase(entity.getTitle().trim())) {
            if (mBinder.isPlaying()) {
                mTrackName.setTextColor(Color.RED);
                if (mBinder != null && mBinder.isPlaying() && !animation.isRunning()) {
                    animation.start();
                }
            } else {
                if (mBinder != null && !mBinder.isPlaying() && animation.isRunning()) {
                    animation.stop();
                }
            }
        } else {
            mWaveFlag.setVisibility(View.GONE);
            mTrackName.setTextColor(Color.BLACK);
        }
    }
}
