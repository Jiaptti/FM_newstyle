package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.ui.track.TrackActivity;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class AlbumVH extends BaseViewHolder<TracksBeanList> {
    private TextView mAlbumName;
    private TextView mPlayTimes;
    private SquareImageView mAlbumImage;
    private SquareImageView mAlbumPlayStatus;
    private SquareImageView mFlagWave;
    private TextView mTimesAgo;
    private TextView mTimeDuration;
    private AlbumPlayService.PlayBinder mBinder = AppContext.getMediaPlayService();
    private AnimationDrawable animation;
    private RealmHelper helper = AppContext.getRealmHelper();
    private RelativeLayout albumContentLayout;
    private RxManager manager = new RxManager();

    public AlbumVH(View itemView) {
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
                                mAlbumName.setTextColor(Color.BLACK);
                                break;
                        }
                    }
                }
            });

            manager.on(AppConstant.MEDIA_START_PLAY, new Action1() {
                @Override
                public void call(Object o) {

                }
            });
        }
    }


    @Override
    public int getType() {
        return R.layout.album_item_layout;
    }

    @Override
    public void initViewHolder(View view) {
        mAlbumName = (TextView) view.findViewById(R.id.album_item_name);
        mPlayTimes = (TextView) view.findViewById(R.id.album_play_times);
        mTimesAgo = (TextView) view.findViewById(R.id.times_ago);
        mTimeDuration = (TextView) view.findViewById(R.id.album_plays_duration);
        mAlbumImage = (SquareImageView) view.findViewById(R.id.album_item_image);
        mFlagWave = (SquareImageView) view.findViewById(R.id.play_flag_wave);
        mAlbumPlayStatus = (SquareImageView) view.findViewById(R.id.album_play_status);
        albumContentLayout = (RelativeLayout) view.findViewById(R.id.album_content_layout);
    }

    @Override
    public void onBindViewHolder(View view, final TracksBeanList entity) {
        animation = (AnimationDrawable) mFlagWave.getBackground();
        mAlbumName.setText(entity.getTitle());
        mFlagWave.setVisibility(View.VISIBLE);
        mPlayTimes.setText(CommonUtils.getOmitPlayCounts(entity.getPlaytimes()));
        ImageUtils.loadCircleImage(mContext, entity.getCoverSmall(), mAlbumImage);
        mAlbumImage.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        mTimesAgo.setText(CommonUtils.getIntervalDays(entity.getCreatedAt()));
        mTimeDuration.setText(CommonUtils.getPlayTime(entity.getDuration()));
        mAlbumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinder != null) {
                    if (mBinder.isPlaying() || AppContext.getPlayState() == AppConstant.STATUS_RESUME
                            || AppContext.getPlayState() == AppConstant.STATUS_PLAY) {
                        if (helper.getNowPlayingTrack().getTitle().equals(entity.getTitle())) {
                            mBinder.pauseMedia();
                        } else {
                            mBinder.stopMedia();
                            mBinder.playMedia(entity.getPlayUrl32());
                            helper.setNowPlayTrack(entity);
                        }
                    } else if (AppContext.getPlayState() == AppConstant.STATUS_NONE
                            || AppContext.getPlayState() == AppConstant.STATUS_PAUSE) {
                        mBinder.playMedia(entity.getPlayUrl32());
                        helper.setNowPlayTrack(entity);
                    }
                }
            }
        });

        albumContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinder.playMedia(entity.getPlayUrl32());
                helper.setNowPlayTrack(entity);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.TRACK_ID, entity.getTrackId());
                Intent intent = new Intent(mContext, TrackActivity.class);
                intent.putExtra(AppConstant.TRACK_BUNDLE, bundle);
                mContext.startActivity(intent);
            }
        });
        setPlayStatus(entity);
    }

    private void setPlayStatus(TracksBeanList entity) {
        if (helper.getNowPlayingTrack() != null &&
                helper.getNowPlayingTrack().getTitle().trim().equalsIgnoreCase(entity.getTitle().trim())) {
            if ((AppContext.getPlayState() == AppConstant.STATUS_PLAY || AppContext.getPlayState() == AppConstant.STATUS_RESUME)) {
                mAlbumName.setTextColor(Color.RED);
                if (mBinder != null && mBinder.isPlaying() && !animation.isRunning()) {
                    animation.start();
                }
                mAlbumPlayStatus.setBackgroundResource(R.drawable.notify_btn_light_pause2_normal_xml);
            } else if (AppContext.getPlayState() == AppConstant.STATUS_PAUSE) {
                if (mBinder != null && !mBinder.isPlaying() && animation.isRunning()) {
                    animation.stop();
                }
                mAlbumPlayStatus.setBackgroundResource(R.drawable.notify_btn_light_play2_normal_xml);
            }
        } else {
            mFlagWave.setVisibility(View.GONE);
            mAlbumName.setTextColor(Color.BLACK);
            mAlbumPlayStatus.setBackgroundResource(R.drawable.notify_btn_light_play2_normal_xml);
        }
    }
}
