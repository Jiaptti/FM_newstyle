package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.ui.ranking.RankingActivity;
import com.fastapp.viroyal.fm_newstyle.ui.track.TrackActivity;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {
    public Context mContext;
    public T presenter;
    public E model;
    private Toolbar mActionBar;
    private TextView mActionTitle;
    private ImageButton mActionSwitch;

    private RealmHelper realmHelper;

    private SquareImageView nowPlayingImg;
    private SquareImageView nowPlayingStatus;
    private Animation operatingAnim;
    private FrameLayout nowPlayingLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!supportActionBar()) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        this.setContentView(layoutResID());
        ButterKnife.bind(this);
        mContext = this;
        presenter = TUtils.getT(this, 0);
        model = TUtils.getT(this, 1);
        if (this instanceof BaseView) presenter.setVM(this, model);

        realmHelper = AppContext.getRealmHelper();
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.album_rotation);
        operatingAnim.setInterpolator(new LinearInterpolator());

        mActionBar = (Toolbar) findViewById(R.id.tool_bar);
        mActionSwitch = (ImageButton) findViewById(R.id.actionbar_switch);

        nowPlayingLayout = (FrameLayout) findViewById(R.id.now_playing_layout);
        if (nowPlayingLayout != null) {
            nowPlayingLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity();
                }
            });
        }
        nowPlayingImg = (SquareImageView) findViewById(R.id.now_playing_image);
        nowPlayingStatus = (SquareImageView) findViewById(R.id.now_playing_status);

        if (presenter != null && supportBottomPlay()) {
            setPlayViewStatue();
            playNowImgAnimation();
            presenter.getManager().on(AppConstant.MEDIA_START_PLAY, new Action1() {
                @Override
                public void call(Object obj) {
                    playNowImgAnimation();
                    setPlayViewStatue();
                }
            });
        }

        if (systemUIFullScreen()) {
            fullScreen();
        }

        if (supportActionBar()) {
            initActionBar(mActionBar);
        }
        if (hasBackButton()) {
            mActionBar.setNavigationIcon(R.mipmap.main_titlebar_back_normal);
            mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mActionBar.setPadding(0, 0, 0, 0);
        }

        this.initView();
    }

    private void startActivity() {
        NowPlayTrack entity = realmHelper.getNowPlayingTrack();
        if (entity != null) {
            Intent intent = new Intent(mContext, TrackActivity.class);
            mContext.startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, RankingActivity.class);
            mContext.startActivity(intent);
        }
    }

    public void setSwitchState(int visiable) {
        mActionSwitch.setVisibility(visiable);
    }

    public void setSwitchListener(View.OnClickListener onClickListener) {
        mActionSwitch.setOnClickListener(onClickListener);
    }

    public void setSwitchBackground(int resId) {
        mActionSwitch.setBackgroundResource(resId);
    }

    private void playNowImgAnimation() {
        if ((AppContext.getPlayState() == AppConstant.STATUS_PLAY
                || AppContext.getPlayState() == AppConstant.STATUS_RESUME) && operatingAnim != null) {
            nowPlayingImg.startAnimation(operatingAnim);
            nowPlayingStatus.setBackgroundResource(R.mipmap.play_icon_bg);
        } else if (AppContext.getPlayState() == AppConstant.STATUS_PAUSE || AppContext.getPlayState() == AppConstant.STATUS_STOP) {
            nowPlayingImg.clearAnimation();
        }
    }

    private void setPlayViewStatue() {
        if (realmHelper.getNowPlayingTrack() == null) {
            nowPlayingStatus.setBackgroundResource(R.mipmap.play_icon_default);
        } else {
            if (AppContext.getPlayState() == AppConstant.STATUS_PAUSE || AppContext.getPlayState() == AppConstant.STATUS_STOP
                    || AppContext.getPlayState() == AppConstant.STATUS_NONE) {
                nowPlayingStatus.setBackgroundResource(R.mipmap.play_icon_pause);
            } else {
                nowPlayingStatus.setBackgroundResource(R.mipmap.play_icon_bg);
            }
        }
        if (realmHelper.getNowPlayingTrack() != null) {
            ImageUtils.loadCircleImage(AppContext.getAppContext(), realmHelper.getNowPlayingTrack().getCoverSmall(), nowPlayingImg);
        }
    }

    private void initActionBar(Toolbar actionBar) {
        if (actionBar == null)
            return;
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        actionBar.setTitle("");
        mActionTitle = (TextView) actionBar.findViewById(R.id.action_bar_title);
        int titleRes = getActionBarTitle();
        if (titleRes != 0 && mActionTitle != null) {
            mActionTitle.setText(titleRes);
        }
    }

    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.destroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }


    protected abstract int layoutResID();

    protected abstract void initView();

    public boolean supportActionBar() {
        return false;
    }

    public boolean supportBottomPlay() {
        return false;
    }

    public int getActionBarTitle() {
        return R.string.app_name;
    }

    public boolean hasBackButton() {
        return false;
    }

    public boolean systemUIFullScreen() {
        return false;
    }

    public void setActionBarTitle(String title) {
        if (supportActionBar()) {
            if (mActionTitle != null) {
                mActionTitle.setText(title);
            } else if (mActionBar != null) {
                mActionBar.setTitle(title);
            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
