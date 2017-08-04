package com.fastapp.viroyal.fm_newstyle.ui.track;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Formatter;
import java.util.Locale;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackActivity extends BaseActivity<TrackPresenter, TrackModel> implements TrackContract.View{
    @Nullable
    @Bind(R.id.action_bar)
    Toolbar actionBar;

    private AlbumPlayService.PlayBinder mBinder;
    private SimpleExoPlayer mSimpleExoPlayer;


    @Bind(R.id.exoView)
    SimpleExoPlayerView mExoPlayerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
//    @Bind(R.id.track_image)
//    SquareImageView trackImg;

    @Override
    protected int layoutResID() {
        return R.layout.track_layout;
    }

    @Override
    protected void initView() {
        AppContext.getAppContext().bindService(new Intent(mContext, AlbumPlayService.class), connection, Context.BIND_AUTO_CREATE);
//        ImageUtils.loadImage(mContext, AppContext.getRealmHelper().getNowPlayingTrack().getCoverMiddle(), trackImg);
        initExoPlayer();
        playVideo();
    }

    private void initExoPlayer(){
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        //2.创建ExoPlayer
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector,loadControl);
        //3.创建SimpleExoPlayerView
        //4.为SimpleExoPlayer设置播放器
        mExoPlayerView.setPlayer(mSimpleExoPlayer);
        ImageUtils.loadImageByBitmap(AppContext.getAppContext(),
                AppContext.getRealmHelper().getNowPlayingTrack().getCoverMiddle(),mExoPlayerView);
    }

    private void playVideo() {
        //测量播放过程中的带宽。 如果不需要，可以为null。
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 生成加载媒体数据的DataSource实例。
        DataSource.Factory dataSourceFactory
                = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext,"useExoplayer"),bandwidthMeter);
        // 生成用于解析媒体数据的Extractor实例。
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        // MediaSource代表要播放的媒体。
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(AppContext.getRealmHelper().getNowPlayingTrack().getPlayUrl32())
                ,dataSourceFactory,extractorsFactory,
                null,null);
        //Prepare the player with the source.
        mSimpleExoPlayer.prepare(videoSource);
        //添加监听的listener
//        mSimpleExoPlayer.setVideoListener(mVideoListener);
        mSimpleExoPlayer.addListener(eventListener);
//        mSimpleExoPlayer.setTextOutput(mOutput);
        mSimpleExoPlayer.setPlayWhenReady(true);
    }


    private ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.i(AppConstant.TAG, "onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.i(AppConstant.TAG, "onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(AppConstant.TAG, "onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.i(AppConstant.TAG, "onPlayerStateChanged: playWhenReady = "+String.valueOf(playWhenReady)
                    +" playbackState = "+playbackState);
            switch (playbackState){
                case ExoPlayer.STATE_ENDED:
                    Log.i(AppConstant.TAG, "Playback ended!");
                    //Stop playback and return to start position
                    setPlayPause(false);
                    mSimpleExoPlayer.seekTo(0);
                    break;
                case ExoPlayer.STATE_READY:
                    Log.i(AppConstant.TAG, "ExoPlayer ready! pos: "+mSimpleExoPlayer.getCurrentPosition()
                            +" max: "+stringForTime((int)mSimpleExoPlayer.getDuration()));
                    mProgressBar.setVisibility(View.GONE);
                    setProgress(0);
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    Log.i(AppConstant.TAG, "Playback buffering!");
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case ExoPlayer.STATE_IDLE:
                    Log.i(AppConstant.TAG, "ExoPlayer idle!");
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i(AppConstant.TAG, "onPlaybackError: "+error.getMessage());
        }

        @Override
        public void onPositionDiscontinuity() {
            Log.i(AppConstant.TAG, "onPositionDiscontinuity");
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Log.i(AppConstant.TAG, "MainActivity.onPlaybackParametersChanged."+playbackParameters.toString());
        }
    };

    private void setPlayPause(boolean play){
        mSimpleExoPlayer.setPlayWhenReady(play);
    }

    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds =  timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    @Override
    protected boolean supportActionBar() {
        return false;
    }

    @Override
    protected boolean supportBottomPlay() {
        return false;
    }

    @Override
    public boolean hasBackButton() { return false; }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void onPause() {
        super.onPause();super.onPause();
        mSimpleExoPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSimpleExoPlayer.release();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBinder = (AlbumPlayService.PlayBinder) service;
            if(mBinder != null)
                Log.i(AppConstant.TAG, "isPlaying = " + mBinder.isPlaying());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
}
