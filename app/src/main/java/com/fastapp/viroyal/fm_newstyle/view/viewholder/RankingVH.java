package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.data.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.RankingTracksBean;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.ui.track.TrackActivity;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/9/10.
 */

public class RankingVH extends BaseViewHolder<RankingTracksBean>{
    private TextView ranking_item_name;
    private TextView ranking_play_times;
    private SquareImageView ranking_item_image;
    private SquareImageView ranking_play_status;
    private SquareImageView ranking_flag_wave;
    private TextView ranking_times_ago;
    private TextView ranking_plays_duration;
    private RelativeLayout ranking_content_layout;
    private AlbumPlayService.PlayBinder mBinder = AppContext.getMediaPlayService();
    private AnimationDrawable animation;
    private RealmHelper helper = AppContext.getRealmHelper();
    private RxManager manager = new RxManager();

    public RankingVH(View itemView) {
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
                            case AppConstant.STATUS_STOP:
                                animation.stop();
                                ranking_item_name.setTextColor(Color.BLACK);
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getType() {
        return R.layout.ranking_track_item_layout;
    }

    @Override
    public void onBindViewHolder(View view, final RankingTracksBean entity) {
        animation = (AnimationDrawable) ranking_flag_wave.getBackground();
        ranking_item_name.setText(entity.getTitle());
        ranking_flag_wave.setVisibility(View.VISIBLE);
        ranking_play_times.setText(CommonUtils.getOmitPlayCounts(entity.getPlaysCounts()));
        ImageUtils.loadCircleImage(mContext, entity.getCoverSmall(), ranking_item_image);
        ranking_item_image.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        ranking_times_ago.setText(CommonUtils.getIntervalDays(entity.getCreatedAt()));
        ranking_plays_duration.setText(CommonUtils.getPlayTime(entity.getDuration()));
        ranking_item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinder != null) {
                    if (mBinder.isPlaying() || AppContext.getPlayState() == AppConstant.STATUS_RESUME
                            || AppContext.getPlayState() == AppConstant.STATUS_PLAY) {
                        if (helper.getNowPlayingTrack().getTrackId() == entity.getTrackId()) {
                            mBinder.pauseMedia();
                        } else {
                            mBinder.stopMedia();
                            mBinder.playMedia(entity.getPlayPath32());
                            helper.setNowPlayTrack(entity);
                        }
                    } else if (AppContext.getPlayState() == AppConstant.STATUS_NONE
                            || AppContext.getPlayState() == AppConstant.STATUS_PAUSE
                            || AppContext.getPlayState() == AppConstant.STATUS_STOP) {
                        AppContext.apply(AppContext.getEditor().putInt(AppConstant.CACHE_PAGEID, AppContext.getTempPageId()));
                        mBinder.playMedia(entity.getPlayPath32());
                        helper.setNowPlayTrack(entity);
                        entity.setPosition(getPosition());
                    }
                }
            }
        });

        ranking_content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBinder.isPlaying() && helper.getNowPlayingTrack().getTrackId() == entity.getTrackId()){
                    Intent intent = new Intent(mContext, TrackActivity.class);
                    mContext.startActivity(intent);
                } else {
                    entity.setPosition(getPosition());
                    helper.setNowPlayTrack(entity);
                    Intent intent = new Intent(mContext, TrackActivity.class);
                    mContext.startActivity(intent);
                }
                AppContext.apply(AppContext.getEditor().putInt(AppConstant.CACHE_PAGEID, AppContext.getTempPageId()));
            }
        });
        setPlayStatus(entity);
    }

    private void setPlayStatus(RankingTracksBean entity) {
        if (helper.getNowPlayingTrack() != null &&
                helper.getNowPlayingTrack().getTrackId() ==entity.getTrackId()) {
            if ((AppContext.getPlayState() == AppConstant.STATUS_PLAY || AppContext.getPlayState() == AppConstant.STATUS_RESUME)) {
                ranking_item_name.setTextColor(Color.RED);
                if (mBinder != null && mBinder.isPlaying() && !animation.isRunning()) {
                    animation.start();
                }
                ranking_play_status.setBackgroundResource(R.drawable.notify_btn_light_pause2_normal_xml);
            } else if (AppContext.getPlayState() == AppConstant.STATUS_PAUSE) {
                if (mBinder != null && !mBinder.isPlaying() && animation.isRunning()) {
                    animation.stop();
                }
                ranking_play_status.setBackgroundResource(R.drawable.notify_btn_light_play2_normal_xml);
            }
        } else {
            ranking_flag_wave.setVisibility(View.GONE);
            ranking_item_name.setTextColor(Color.BLACK);
            ranking_play_status.setBackgroundResource(R.drawable.notify_btn_light_play2_normal_xml);
        }
    }

}
