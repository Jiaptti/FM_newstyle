package com.fastapp.viroyal.fm_newstyle.ui.track;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.media.MediaPlayerManager;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackActivity extends BaseActivity<TrackPresenter, TrackModel> implements TrackContract.View, View.OnClickListener
        ,MediaPlayerManager.PlayTimeChangeListener{
    @Nullable
    @Bind(R.id.action_bar)
    Toolbar actionBar;
    @Bind(R.id.track_image)
    SquareImageView trackImg;
    @Bind(R.id.loading)
    SquareImageView playLoadingImg;
    @Bind(R.id.track_content)
    NestedScrollView trackContent;
    @Bind(R.id.play_popup_layout)
    RelativeLayout playPopupLayout;
    @Bind(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Bind(R.id.loading_img)
    SquareImageView loadingImg;
    private AnimationDrawable animation;

    private Animation operatingAnim;
    private AlbumPlayService.PlayBinder mBinder;

    @Override
    protected int layoutResID() {
        return R.layout.track_layout;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra(AppConstant.TRACK_BUNDLE);
        if (bundle != null) {
            presenter.getTrack(bundle.getInt(AppConstant.TRACK_ID));
        }
        AppContext.getAppContext().bindService(new Intent(mContext, AlbumPlayService.class), connection, Context.BIND_AUTO_CREATE);
        trackImg.setOnClickListener(this);
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.album_rotation);
        operatingAnim.setInterpolator(new LinearInterpolator());
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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBinder = (AlbumPlayService.PlayBinder) service;
            if(mBinder != null)
                mBinder.setTimeListener(TrackActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    public void onClick(View view) {
        if(playPopupLayout.getVisibility() == View.GONE){
            playPopupLayout.setVisibility(View.VISIBLE);
        } else {
            playPopupLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void setNowPlayerMessage(TrackInfoBean trackInfoBean) {
        ImageUtils.loadImage(mContext, trackInfoBean.getCoverLarge(), trackImg);
    }

    @Override
    public void loadAlbumList(Data<HimalayanBean> list) {

    }

    @Override
    public void playTimeChange(int time) {
        Log.i("test","time = " + time);
    }
}
