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
    @Bind(R.id.track_image)
    SquareImageView trackImg;

    @Override
    protected int layoutResID() {
        return R.layout.track_layout;
    }

    @Override
    protected void initView() {
        AppContext.getAppContext().bindService(new Intent(mContext, AlbumPlayService.class), connection, Context.BIND_AUTO_CREATE);
        ImageUtils.loadImage(mContext, AppContext.getRealmHelper().getNowPlayingTrack().getCoverLarge(), trackImg);
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
    }

    @Override
    protected void onStop() {
        super.onStop();
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
