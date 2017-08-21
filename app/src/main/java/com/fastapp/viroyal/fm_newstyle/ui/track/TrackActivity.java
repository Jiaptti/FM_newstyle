package com.fastapp.viroyal.fm_newstyle.ui.track;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.media.MediaPlayerManager;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackActivity extends BaseActivity<TrackPresenter, TrackModel> implements TrackContract.View, View.OnClickListener
        , MediaPlayerManager.PlayTimeChangeListener, MediaPlayerManager.PlayBufferingUpdate
        , SeekBar.OnSeekBarChangeListener {
    @Nullable
    @Bind(R.id.action_bar)
    Toolbar actionBar;
    @Bind(R.id.track_image)
    SquareImageView trackImg;
    @Bind(R.id.loading)
    SquareImageView playLoadingImg;
    @Bind(R.id.player_backward)
    SquareImageView backward;
    @Bind(R.id.player_forward)
    SquareImageView forward;
    @Bind(R.id.track_content)
    NestedScrollView trackContent;
    @Bind(R.id.play_popup_layout)
    RelativeLayout playPopupLayout;
    @Bind(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Bind(R.id.loading_img)
    SquareImageView loadingImg;
    @Bind(R.id.player_current_time)
    TextView playerCurrentTime;
    @Bind(R.id.player_duration)
    TextView playerDuration;
    @Bind(R.id.current_time)
    TextView currentTime;
    @Bind(R.id.total_time)
    TextView totalTime;
    @Bind(R.id.play_pause)
    ImageButton playPauseButton;
    @Bind(R.id.seek_bar)
    SeekBar playSeekBar;

    private AnimationDrawable animation;
    private Animation operatingAnim;
    private AlbumPlayService.PlayBinder mBinder;
    private NowPlayTrack nowPlayTrack;
    private int position;

    @Override
    protected int layoutResID() {
        return R.layout.track_layout;
    }

    @Override
    protected void initView() {
        mBinder = AppContext.getMediaPlayService();
        mBinder.setTimeListener(this);
        mBinder.setPlayBufferingUpdateListener(this);
        Bundle bundle = getIntent().getBundleExtra(AppConstant.TRACK_BUNDLE);
        if (bundle != null) {
            presenter.getTrack(bundle.getInt(AppConstant.TRACK_ID));
        } else {
            nowPlayTrack = AppContext.getRealmHelper().getNowPlayingTrack();
            ImageUtils.loadImage(mContext, nowPlayTrack.getCoverLarge(), trackImg);
            CommonUtils.setTotalTime(nowPlayTrack.getDuration(), totalTime);
            CommonUtils.setTotalTime(nowPlayTrack.getDuration(), playerDuration);
            playTimeChange(mBinder.getCurrentPosition());
        }
        trackImg.setOnClickListener(this);
        playPauseButton.setOnClickListener(this);
        backward.setOnClickListener(this);
        forward.setOnClickListener(this);
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.album_rotation);
        operatingAnim.setInterpolator(new LinearInterpolator());
        playSeekBar.setOnSeekBarChangeListener(this);
        if (mBinder.isPlaying()) {
            playPauseButton.setBackgroundResource(R.drawable.player_toolbar_pause_bg);
        } else {
            playPauseButton.setBackgroundResource(R.drawable.player_toolbar_play_bg);
        }
    }

    @Override
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        trackContent.setVisibility(View.GONE);
        animation = (AnimationDrawable) loadingImg.getBackground();
        animation.start();
    }

    @Override
    public void dismissLoading() {
        loadingLayout.setVisibility(View.GONE);
        trackContent.setVisibility(View.VISIBLE);
        animation.stop();
    }

    @Override
    public boolean systemUIFullScreen() {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.track_image:
                if (playPopupLayout.getVisibility() == View.GONE) {
                    playPopupLayout.setVisibility(View.VISIBLE);
                } else {
                    playPopupLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.play_pause:
                if (mBinder.isPlaying()) {
                    mBinder.pauseMedia();
                    playPauseButton.setBackgroundResource(R.drawable.player_toolbar_play_bg);
                } else {
                    mBinder.resumePlay();
                    playPauseButton.setBackgroundResource(R.drawable.player_toolbar_pause_bg);
                }
                break;
            case R.id.player_backward:
                mBinder.seekTo(mBinder.getCurrentPosition() - 15000);
                playTimeChange(mBinder.getCurrentPosition());
                break;
            case R.id.player_forward:
                mBinder.seekTo(mBinder.getCurrentPosition() + 15000);
                playTimeChange(mBinder.getCurrentPosition());
                break;
        }
    }

    @Override
    public void setNowPlayerMessage(TrackInfoBean trackInfoBean) {
        ImageUtils.loadImage(mContext, trackInfoBean.getCoverLarge(), trackImg);
        CommonUtils.setTotalTime(trackInfoBean.getDuration(), totalTime);
        CommonUtils.setTotalTime(trackInfoBean.getDuration(), playerDuration);
    }

    @Override
    public void loadAlbumList(Data<HimalayanBean> list) {

    }

    @Override
    public void playTimeChange(int time) {
        CommonUtils.setCurrentTime((time / 1000), currentTime);
        CommonUtils.setCurrentTime((time / 1000), playerCurrentTime);

        int position = mBinder.getCurrentPosition();
        int duration = mBinder.getDuration();
        if (duration > 0) {
            if (playSeekBar != null) {
                long pos = playSeekBar.getMax() * position / duration;
                playSeekBar.setProgress((int) pos);
            }
        }
    }

    @Override
    public void onPlayBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        if (playSeekBar != null)
            playSeekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int duration = mBinder.getDuration();
        position = ((progress * duration) / 100);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mBinder.seekTo(position);
    }
}
