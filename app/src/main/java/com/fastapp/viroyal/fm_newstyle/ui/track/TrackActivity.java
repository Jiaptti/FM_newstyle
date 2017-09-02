package com.fastapp.viroyal.fm_newstyle.ui.track;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.media.MediaPlayerManager;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.fastapp.viroyal.fm_newstyle.view.popup.PlayListPopupWindow;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;

import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackActivity extends BaseActivity<TrackPresenter, TrackModel> implements TrackContract.View, View.OnClickListener
        , MediaPlayerManager.PlayTimeChangeListener, MediaPlayerManager.PlayBufferingUpdate
        , SeekBar.OnSeekBarChangeListener {
    @Nullable
    @Bind(R.id.action_bar)
    Toolbar actionBar;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout mainContent;
    @Bind(R.id.track_image)
    SquareImageView trackImg;
    @Bind(R.id.loading)
    SquareImageView playLoadingImg;
    @Bind(R.id.play_btn_bg)
    SquareImageView playBtnBg;
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
    @Bind(R.id.playlist)
    TextView playList;
    @Bind(R.id.play_pause)
    ImageButton playPauseButton;
    @Bind(R.id.seek_bar)
    SeekBar playSeekBar;
    @Bind(R.id.net_error_layout)
    LinearLayout errorLayout;
    @Bind(R.id.reload)
    TextView reload;
    @Bind(R.id.previous)
    ImageButton prev;
    @Bind(R.id.next)
    ImageButton next;

    private Animation operatingAnim;
    private AlbumPlayService.PlayBinder mBinder;
    private NowPlayTrack nowPlayTrack;
    private int position;
    private RxManager manager = new RxManager();
    private PlayListPopupWindow listPopupWindow;

    @Override
    protected int layoutResID() {
        return R.layout.track_layout;
    }

    @Override
    protected void initView() {
        mBinder = AppContext.getMediaPlayService();
        listPopupWindow = new PlayListPopupWindow(mContext);
        if(mBinder != null){
            mBinder.setTimeListener(this);
            mBinder.setPlayBufferingUpdateListener(this);
        }
        presenter.getNowTrack();
        trackImg.setOnClickListener(this);
        playPauseButton.setOnClickListener(this);
        backward.setOnClickListener(this);
        forward.setOnClickListener(this);
        playList.setOnClickListener(this);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.album_rotation);
        operatingAnim.setInterpolator(new LinearInterpolator());
        playSeekBar.setOnSeekBarChangeListener(this);
        updatePlayUI();
        manager.on(AppConstant.MEDIA_START_PLAY, new Action1() {
            @Override
            public void call(Object o) {
                if (o instanceof Integer) {
                    switch ((Integer) o) {
                        case AppConstant.STATUS_PLAY:
                        case AppConstant.STATUS_RESUME:
                            updatePlayUI();
                            presenter.getNowTrack();
                            break;
                        case AppConstant.STATUS_PAUSE:
                            playPauseButton.setBackgroundResource(R.drawable.player_toolbar_play_bg);
                            break;
                    }
                }
            }
        });

        presenter.getManager().on(AppConstant.ERROR_MESSAGE, new Action1() {
            @Override
            public void call(Object o) {
                ErrorBean errorBean = (ErrorBean) o;
                if (errorBean.getClazz() == TrackListVH.class) {
                    errorLayout.setVisibility(View.VISIBLE);
                    trackContent.setVisibility(View.GONE);
                }
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorLayout.setVisibility(View.GONE);
                trackContent.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void dismissLoading() {
    }


    @Override
    public boolean systemUIFullScreen() {
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        listPopupWindow.onDestroy();
        listPopupWindow = null;
        manager.clear(AppConstant.MEDIA_START_PLAY);
        manager.clear(AppConstant.UPDATE_ITEM_STATUS);
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
                if(mBinder != null){
                    if (mBinder.isPlaying()) {
                        mBinder.pauseMedia();
                        playPauseButton.setBackgroundResource(R.drawable.player_toolbar_play_bg);
                    } else {
                        mBinder.resumePlay();
                        playPauseButton.setBackgroundResource(R.drawable.player_toolbar_pause_bg);
                    }
                }
                break;
            case R.id.player_backward:
                if(mBinder != null){
                    mBinder.seekTo(mBinder.getCurrentPosition() - 15000);
                    playTimeChange(mBinder.getCurrentPosition());
                }
                break;
            case R.id.player_forward:
                if(mBinder != null){
                    mBinder.seekTo(mBinder.getCurrentPosition() + 15000);
                    playTimeChange(mBinder.getCurrentPosition());
                }
                break;
            case R.id.playlist:
                listPopupWindow.show(mainContent);
                break;
            case R.id.previous:

                break;
            case R.id.next:

                break;
        }
    }

//    @Override
//    public void setNowPlayerMessage(TrackInfoBean trackInfoBean) {
//        ImageUtils.loadImage(mContext, trackInfoBean.getCoverLarge(), trackImg);
//        CommonUtils.setTotalTime(trackInfoBean.getDuration(), totalTime);
//        CommonUtils.setTotalTime(trackInfoBean.getDuration(), playerDuration);
//    }

    @Override
    public void setNowPlayerTrack(NowPlayTrack nowPlayerTrack) {
        nowPlayTrack = AppContext.getRealmHelper().getNowPlayingTrack();
        ImageUtils.loadImage(mContext, nowPlayTrack.getCoverLarge(), trackImg);
        CommonUtils.setTotalTime(nowPlayTrack.getDuration(), totalTime);
        CommonUtils.setTotalTime(nowPlayTrack.getDuration(), playerDuration);
        if(mBinder != null){
            playTimeChange(mBinder.getCurrentPosition());
        }
        mBinder.playMedia(nowPlayTrack.getPlayUrl32());
    }


    @Override
    public void loadAlbumList(List<TracksBeanList> data) {
    }

    public void playTimeChange(int time) {
        CommonUtils.setCurrentTime((time / 1000), currentTime);
        CommonUtils.setCurrentTime((time / 1000), playerCurrentTime);

        if(mBinder != null){
            int position = mBinder.getCurrentPosition();
            int duration = mBinder.getDuration();
            if (duration > 0) {
                if (playSeekBar != null) {
                    long pos = playSeekBar.getMax() * position / duration;
                    playSeekBar.setProgress((int) pos);
                }
            }
        }
    }

    private void updatePlayUI(){
        if(mBinder != null && playBtnBg != null){
            if(mBinder.isPlaying()){
                if(playLoadingImg != null && playLoadingImg.getAnimation() != null){
                    playLoadingImg.clearAnimation();
                }
                playBtnBg.setVisibility(View.VISIBLE);
                playPauseButton.setBackgroundResource(R.drawable.player_toolbar_pause_bg);
                playPauseButton.setEnabled(true);
            } else {
                playBtnBg.setVisibility(View.GONE);
                playLoadingImg.setVisibility(View.VISIBLE);
                playLoadingImg.startAnimation(operatingAnim);
                playPauseButton.setEnabled(false);
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
        if(mBinder != null){
            int duration = mBinder.getDuration();
            position = ((progress * duration) / 100);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mBinder.seekTo(position);
    }
}
