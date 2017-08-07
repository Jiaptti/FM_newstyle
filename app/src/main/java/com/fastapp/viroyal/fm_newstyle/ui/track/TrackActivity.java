package com.fastapp.viroyal.fm_newstyle.ui.track;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackActivity extends BaseActivity<TrackPresenter, TrackModel> implements TrackContract.View, View.OnClickListener{
    @Nullable
    @Bind(R.id.action_bar)
    Toolbar actionBar;

    private AlbumPlayService.PlayBinder mBinder;
    @Bind(R.id.track_image)
    SquareImageView trackImg;

    @Bind(R.id.play_popup_layout)
    LinearLayout playPopupLayout;

    @Override
    protected int layoutResID() {
        return R.layout.track_layout;
    }

    @Override
    protected void initView() {
        AppContext.getAppContext().bindService(new Intent(mContext, AlbumPlayService.class), connection, Context.BIND_AUTO_CREATE);
        ImageUtils.loadImage(mContext, AppContext.getRealmHelper().getNowPlayingTrack().getCoverLarge(), trackImg);
        trackImg.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if(playPopupLayout.getVisibility() == View.GONE){
            playPopupLayout.setVisibility(View.VISIBLE);
        } else {
            playPopupLayout.setVisibility(View.GONE);
        }

    }
}
