package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

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
    private AlbumPlayService.PlayBinder mBinder;
    private AnimationDrawable animation;
    private RealmHelper helper;

    public AlbumVH(View itemView) {
        super(itemView);
        AppContext.getAppContext().bindService(new Intent(mContext, AlbumPlayService.class), connection, Context.BIND_AUTO_CREATE);
        helper = AppContext.getRealmHelper();
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
    }

    @Override
    public void onBindViewHolder(View view, final TracksBeanList entity) {
        mAlbumName.setText(entity.getTitle());
        mPlayTimes.setText(CommonUtils.getOmitPlayCounts(entity.getPlaytimes()));
        ImageUtils.loadCircleImage(mContext, entity.getCoverSmall(), mAlbumImage);
        mAlbumImage.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        mTimesAgo.setText(CommonUtils.getIntervalDays(entity.getCreatedAt()));
        mTimeDuration.setText(CommonUtils.getPlayTime(entity.getDuration()));
        mFlagWave.setBackgroundResource(R.drawable.play_flag_wave);
        animation = (AnimationDrawable) mFlagWave.getBackground();
        mAlbumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinder != null) {
                    if (mBinder.isPlaying()) {
                        if (helper.getNowPlayingTrack().getTitle().equals(entity.getTitle())) {
                            mBinder.pauseMedia();
                        } else {
                            mBinder.stopMedia();
                            mBinder.playMedia(entity.getPlayUrl32());
                            helper.setNowPlayTrack(entity);
                        }
                    } else {
                        mBinder.playMedia(entity.getPlayUrl32());
                        helper.setNowPlayTrack(entity);
                    }
                }
            }
        });
        setPlayStatus(entity);
    }

    private void setPlayStatus(TracksBeanList entity) {
        if (helper.getNowPlayingTrack().getTitle().trim().equalsIgnoreCase(entity.getTitle().trim()) &&
                AppContext.getPlayState() == AppConstant.STATUS_PLAY) {
            mFlagWave.setVisibility(View.VISIBLE);
            mAlbumName.setTextColor(Color.RED);
            animation.start();
            mAlbumImage.clearColorFilter();
            mAlbumPlayStatus.setBackgroundResource(R.mipmap.player_toolbar_pause_adstatue);
        } else {
            animation.stop();
            mFlagWave.setVisibility(View.GONE);
            mAlbumName.setTextColor(Color.BLACK);
            mAlbumImage.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            mAlbumPlayStatus.setBackgroundResource(R.mipmap.notify_btn_light_play2_normal);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBinder = (AlbumPlayService.PlayBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
}
