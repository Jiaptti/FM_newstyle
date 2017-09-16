package com.fastapp.viroyal.fm_newstyle.ui.track;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.media.MediaPlayerManager;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksInfo;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.fastapp.viroyal.fm_newstyle.view.popup.PlayListPopupWindow;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackActivity extends BaseActivity<TrackPresenter, TrackModel> implements TrackContract.View, View.OnClickListener
        , MediaPlayerManager.PlayTimeChangeListener, MediaPlayerManager.PlayBufferingUpdate
        , SeekBar.OnSeekBarChangeListener, MediaPlayerManager.PlayCompleteListener {
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
    @Bind(R.id.previous)
    ImageButton prev;
    @Bind(R.id.next)
    ImageButton next;
    @Bind(R.id.track_info_image)
    ImageView trackInfoImage;
    @Bind(R.id.track_info_title)
    TextView trackInfoTitle;
    @Bind(R.id.track_title)
    TextView trackTitle;
    @Bind(R.id.track_play_counts)
    TextView trackPlayCounts;
    @Bind(R.id.track_create_time)
    TextView trackCreateTime;
    @Bind(R.id.track_real_content)
    TextView trackInfo;
    @Bind(R.id.track_info_content)
    LinearLayout trackInfoContent;
    @Bind(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Bind(R.id.loading_img)
    SquareImageView loadingImg;
    @Bind(R.id.track_info_error)
    LinearLayout trackInfoError;
    @Bind(R.id.track_reload)
    TextView trackReload;

    private Animation operatingAnim;
    private AnimationDrawable animation;
    private AlbumPlayService.PlayBinder mBinder;
    private int position;
    private PlayListPopupWindow listPopupWindow;
    private RealmHelper helper;
    private TracksBeanList beanList;

    @Override
    protected int layoutResID() {
        return R.layout.track_layout;
    }

    @Override
    protected void initView() {
        mBinder = AppContext.getMediaPlayService();
        listPopupWindow = new PlayListPopupWindow(mContext);
        helper = AppContext.getRealmHelper();
        if (mBinder != null) {
            mBinder.setTimeListener(this);
            mBinder.setPlayCompleteListener(this);
            mBinder.setPlayBufferingUpdateListener(this);
        }
        beanList = (TracksBeanList) getIntent().getSerializableExtra(AppConstant.TRACK_BUNDLE);
        if (helper.getNowPlayingTrack() != null) {
            if (beanList != null && beanList.getTrackId() != helper.getNowPlayingTrack().getTrackId()) {
                presenter.getTracksInfo(beanList.getTrackId());
            } else {
                presenter.getNowTrack();
            }
        } else {
            presenter.getTracksInfo(beanList.getTrackId());
        }
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
        if (!mBinder.isPlaying()) {
            prepareLoadUI();
        } else {
            finishLoadUI();
        }
        presenter.getManager().on(AppConstant.MEDIA_START_PLAY, new Action1() {
            @Override
            public void call(Object o) {
                if (o instanceof Integer) {
                    switch ((Integer) o) {
                        case AppConstant.STATUS_PLAY:
                        case AppConstant.STATUS_RESUME:
                            finishLoadUI();
                            break;
                        case AppConstant.STATUS_PAUSE:
                            finishLoadUI();
                            break;
                        case AppConstant.STATUS_STOP:
                            finishLoadUI();
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
                    loadingLayout.setVisibility(View.GONE);
                    animation.stop();
                    trackInfoError.setVisibility(View.VISIBLE);
                }
            }
        });

        trackReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackInfoError.setVisibility(View.GONE);
                presenter.getTracksInfo(helper.getNowPlayingTrack().getTrackId());
            }
        });

        presenter.getManager().on(AppConstant.UPDATE_TRACKS_UI, new Action1() {
            @Override
            public void call(Object o) {
                prepareLoadUI();
            }
        });
    }

    @Override
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        trackInfoContent.setVisibility(View.GONE);
        animation = (AnimationDrawable) loadingImg.getBackground();
        animation.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void dismissLoading() {
        loadingLayout.setVisibility(View.GONE);
        trackInfoContent.setVisibility(View.VISIBLE);
        animation.stop();
    }


    @Override
    public boolean systemUIFullScreen() {
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        listPopupWindow.destroyManager();
        listPopupWindow = null;
        presenter.getManager().clear(AppConstant.MEDIA_START_PLAY);
        presenter.getManager().clear(AppConstant.ERROR_MESSAGE);
        presenter.getManager().clear(AppConstant.UPDATE_TRACKS_UI);
        AppContext.setFromWindow(false);
        if (operatingAnim != null)
            operatingAnim = null;
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
                if (mBinder != null) {
                    if (mBinder.isPlaying()) {
                        mBinder.pauseMedia();
                    } else {
                        if (AppContext.getPlayState() != AppConstant.STATUS_STOP)
                            prepareLoadUI();
                        mBinder.resumePlay();
                    }
                    finishLoadUI();
                }
                break;
            case R.id.player_backward:
                if (mBinder != null) {
                    mBinder.seekTo(mBinder.getCurrentPosition() - 15000);
                    playTimeChange(mBinder.getCurrentPosition());
                }
                break;
            case R.id.player_forward:
                if (mBinder != null) {
                    mBinder.seekTo(mBinder.getCurrentPosition() + 15000);
                    playTimeChange(mBinder.getCurrentPosition());
                }
                break;
            case R.id.playlist:
                listPopupWindow.show(mainContent);
                break;
            case R.id.previous:
                listPopupWindow.playPrev();
                prepareLoadUI();
                presenter.getTracksInfo(helper.getNowPlayingTrack().getTrackId());
                break;
            case R.id.next:
                listPopupWindow.playNext();
                prepareLoadUI();
                presenter.getTracksInfo(helper.getNowPlayingTrack().getTrackId());
                break;
        }
    }

    @Override
    public void setNowPlayerTrack(NowPlayTrack nowPlayerTrack) {
        listPopupWindow.initListData(nowPlayerTrack.getAlbumId());
        if (helper.getNowPlayingTrack().getCoverLarge() == null) {
            ImageUtils.loadImage(mContext, nowPlayerTrack.getCoverSmall(), trackImg);
        } else {
            ImageUtils.loadImage(mContext, nowPlayerTrack.getCoverLarge(), trackImg);
        }
        CommonUtils.setTotalTime(nowPlayerTrack.getDuration(), totalTime);
        CommonUtils.setTotalTime(nowPlayerTrack.getDuration(), playerDuration);
        if (mBinder != null) {
            playTimeChange(mBinder.getCurrentPosition());
        }
        if (AppContext.getPlayState() != AppConstant.STATUS_STOP) {
            mBinder.playMedia(nowPlayerTrack.getPlayUrl32());
        }
        ImageUtils.loadImage(mContext, nowPlayerTrack.getCoverSmall(), trackInfoImage);
        trackInfoTitle.setText(nowPlayerTrack.getAlbumTitle());
        trackTitle.setText(nowPlayerTrack.getTitle());
        trackPlayCounts.setText(CommonUtils.getOmitOrderCounts(nowPlayerTrack.getPlaytimes()));
        trackCreateTime.setText(CommonUtils.getCreatedTime(nowPlayerTrack.getCreatedAt()));
        trackInfo.setText(nowPlayerTrack.getIntro());
    }

    @Override
    public void initTracksInfo(TracksInfo tracksInfo) {
        listPopupWindow.initListData(tracksInfo.getAlbumId());
        if (tracksInfo.getCoverLarge() == null) {
            ImageUtils.loadImage(mContext, tracksInfo.getCoverSmall(), trackImg);
        } else {
            ImageUtils.loadImage(mContext, tracksInfo.getCoverLarge(), trackImg);
        }
        CommonUtils.setTotalTime(tracksInfo.getDuration(), totalTime);
        CommonUtils.setTotalTime(tracksInfo.getDuration(), playerDuration);
        if (mBinder != null) {
            playTimeChange(mBinder.getCurrentPosition());
        }
        if (AppContext.getPlayState() != AppConstant.STATUS_STOP) {
            mBinder.playMedia(tracksInfo.getPlayUrl32());
        }
        ImageUtils.loadImage(mContext, tracksInfo.getCoverSmall(), trackInfoImage);
        trackInfoTitle.setText(tracksInfo.getAlbumTitle());
        trackTitle.setText(tracksInfo.getTitle());
        trackPlayCounts.setText(CommonUtils.getOmitOrderCounts(tracksInfo.getPlaytimes()));
        trackCreateTime.setText(CommonUtils.getCreatedTime(tracksInfo.getCreatedAt()));
        trackInfo.setText(tracksInfo.getIntro());
        beanList.setIntro(tracksInfo.getIntro());
        helper.setNowPlayTrack(beanList);
    }


    public void playTimeChange(int time) {
        CommonUtils.setCurrentTime((time / 1000), currentTime);
        CommonUtils.setCurrentTime((time / 1000), playerCurrentTime);

        if (mBinder != null) {
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


    private void finishLoadUI() {
        if (playLoadingImg != null && playLoadingImg.getAnimation() != null) {
            playLoadingImg.clearAnimation();
        }
        playBtnBg.setVisibility(View.VISIBLE);
        if (mBinder.isPlaying()) {
            playPauseButton.setBackgroundResource(R.drawable.player_toolbar_pause_bg);
        } else {
            playPauseButton.setBackgroundResource(R.drawable.player_toolbar_play_bg);
        }
        playList.setEnabled(true);
        playPauseButton.setEnabled(true);
        if (helper.getNowPlayingTrack().getPosition() == 0) {
            prev.setEnabled(false);
        } else {
            prev.setEnabled(true);
        }
        if (listPopupWindow.hasNext()) {
            next.setEnabled(true);
        } else {
            next.setEnabled(false);
        }
    }

    private void prepareLoadUI() {
        playBtnBg.setVisibility(View.GONE);
        playLoadingImg.setVisibility(View.VISIBLE);
        playLoadingImg.startAnimation(operatingAnim);
        playPauseButton.setEnabled(false);
        playList.setEnabled(false);
        prev.setEnabled(false);
        next.setEnabled(false);
    }

    @Override
    public void onPlayBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        if (playSeekBar != null)
            playSeekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mBinder != null) {
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

    @Override
    public void playMusicComplete() {
        if (listPopupWindow.hasNext()) {
            listPopupWindow.playNext();
            prepareLoadUI();
        } else {
            mBinder.stopMedia();
            presenter.getNowTrack();
        }
    }
}
