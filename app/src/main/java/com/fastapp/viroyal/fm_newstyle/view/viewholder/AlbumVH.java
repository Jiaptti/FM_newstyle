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
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
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
    private TextView album_item_name;
    private TextView album_play_times;
    private SquareImageView album_item_image;
    private SquareImageView album_play_status;
    private SquareImageView play_flag_wave;
    private TextView times_ago;
    private TextView album_plays_duration;
    private RelativeLayout album_content_layout;
    private AlbumPlayService.PlayBinder mBinder = AppContext.getMediaPlayService();
    private AnimationDrawable animation;
    private RealmHelper helper = AppContext.getRealmHelper();
    private RxManager manager = new RxManager();
    private ErrorBean errorBean = new ErrorBean();

    public AlbumVH(View itemView) {
        super(itemView);
        errorBean.setClazz(AlbumVH.class);
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
                                album_item_name.setTextColor(Color.BLACK);
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getType() {
        return R.layout.album_item_layout;
    }

    @Override
    public void onBindViewHolder(View view, final TracksBeanList entity) {
        animation = (AnimationDrawable) play_flag_wave.getBackground();
        album_item_name.setText(entity.getTitle());
        play_flag_wave.setVisibility(View.VISIBLE);
        album_play_times.setText(CommonUtils.getOmitPlayCounts(entity.getPlaytimes()));
        ImageUtils.loadCircleImage(mContext, entity.getCoverSmall(), album_item_image);
        album_item_image.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        times_ago.setText(CommonUtils.getIntervalDays(entity.getCreatedAt()));
        album_plays_duration.setText(CommonUtils.getPlayTime(entity.getDuration()));
        album_item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinder != null) {
                    if (mBinder.isPlaying() || AppContext.getPlayState() == AppConstant.STATUS_RESUME
                            || AppContext.getPlayState() == AppConstant.STATUS_PLAY) {
                        if (helper.getNowPlayingTrack().getTrackId() == entity.getTrackId()) {
                            mBinder.pauseMedia();
                        } else {
                            mBinder.stopMedia();
                            mBinder.playMedia(entity.getPlayUrl32());
                            manager.post(AppConstant.SAVE_DATA, null);
                            helper.setNowPlayTrack(entity);
                            manager.post(AppConstant.SAVE_DATA, errorBean);
                        }
                    } else if (AppContext.getPlayState() == AppConstant.STATUS_NONE
                            || AppContext.getPlayState() == AppConstant.STATUS_PAUSE
                            || AppContext.getPlayState() == AppConstant.STATUS_STOP) {
                        AppContext.apply(AppContext.getEditor().putInt(AppConstant.CACHE_PAGEID, AppContext.getTempPageId()));
                        entity.setPosition(getPosition());
                        entity.setFromTrack(false);
                        helper.setNowPlayTrack(entity);
                        mBinder.playMedia(entity.getPlayUrl32());
                        manager.post(AppConstant.SAVE_DATA, errorBean);
                    }
                }
            }
        });

        album_content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helper.getNowPlayingTrack() == null){
                    entity.setPosition(getPosition());
                    entity.setFromTrack(false);
                    helper.setNowPlayTrack(entity);
                } else {
                    if(!mBinder.isPlaying() && helper.getNowPlayingTrack().getTrackId() != entity.getTrackId()){
                        entity.setPosition(getPosition());
                        entity.setFromTrack(false);
                        helper.setNowPlayTrack(entity);
                    }
                }
                Intent intent = new Intent(mContext, TrackActivity.class);
                intent.putExtra(AppConstant.TRACK_BUNDLE, entity);
                mContext.startActivity(intent);
                manager.post(AppConstant.SAVE_DATA, errorBean);
            }
        });
        setPlayStatus(entity);
    }

    private void setPlayStatus(TracksBeanList entity) {
        if (helper.getNowPlayingTrack() != null &&
                helper.getNowPlayingTrack().getTrackId() ==entity.getTrackId()) {
            if ((AppContext.getPlayState() == AppConstant.STATUS_PLAY || AppContext.getPlayState() == AppConstant.STATUS_RESUME)) {
                album_item_name.setTextColor(Color.RED);
                if (mBinder != null && mBinder.isPlaying() && !animation.isRunning()) {
                    animation.start();
                }
                album_play_status.setBackgroundResource(R.drawable.notify_btn_light_pause2_normal_xml);
            } else if (AppContext.getPlayState() == AppConstant.STATUS_PAUSE) {
                if (mBinder != null && !mBinder.isPlaying() && animation.isRunning()) {
                    animation.stop();
                }
                album_play_status.setBackgroundResource(R.drawable.notify_btn_light_play2_normal_xml);
            }
        } else {
            play_flag_wave.setVisibility(View.GONE);
            album_item_name.setTextColor(Color.BLACK);
            album_play_status.setBackgroundResource(R.drawable.notify_btn_light_play2_normal_xml);
        }
    }
}
