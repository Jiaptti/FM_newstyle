package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.fastapp.viroyal.fm_newstyle.view.layout.SwipeBackLayout;

import butterknife.Bind;
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

//    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    private SquareImageView nowPlayingImg;
    private SquareImageView nowPlayingStatus;
    private RealmHelper realmHelper;
    private Animation operatingAnim;

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
        nowPlayingImg = (SquareImageView) findViewById(R.id.now_playing_image);
        nowPlayingStatus = (SquareImageView) findViewById(R.id.now_playing_status);

        if(supportBottomPlay()){
            setPlayViewStatue();
            presenter.getManager().on(AppConstant.UPDATE_ITEM_STATUS, new Action1() {
                @Override
                public void call(Object obj) {
                    if (obj instanceof NowPlayTrack) {
                        setPlayViewStatue();
                    }
                }
            });
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

    private void setPlayViewStatue(){
        if (AppContext.getPlayState() == AppConstant.STATUS_PLAY
                || AppContext.getPlayState() == AppConstant.STATUS_RESUME) {
            String url = String.valueOf(nowPlayingImg.getTag());
            if (!url.equalsIgnoreCase(realmHelper.getNowPlayingTrack().getCoverSmall())) {
                ImageUtils.loadCircleImage(AppContext.getAppContext(), realmHelper.getNowPlayingTrack().getCoverSmall(), nowPlayingImg);
            }
            if(operatingAnim != null )
                nowPlayingImg.startAnimation(operatingAnim);
            nowPlayingStatus.setBackgroundResource(R.mipmap.play_icon_bg);
        } else if (AppContext.getPlayState() == AppConstant.STATUS_PAUSE) {
            nowPlayingStatus.setBackgroundResource(R.mipmap.play_icon_pause);
            nowPlayingImg.clearAnimation();
        } else if (AppContext.getPlayState() == AppConstant.STATUS_NONE){
            NowPlayTrack nowPlayTrack = realmHelper.getNowPlayingTrack();
            if(nowPlayTrack != null){
                nowPlayingImg.setTag(R.id.tracks_image_tag, nowPlayTrack.getCoverSmall());
                ImageUtils.loadCircleImage(AppContext.getAppContext(), nowPlayTrack.getCoverSmall(), nowPlayingImg);
            }
        }
    }

    private void initActionBar(Toolbar actionBar) {
        if (actionBar == null)
            return;
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBar.setTitle("");
        mActionTitle = (TextView) actionBar.findViewById(R.id.action_bar_title);
        int titleRes = getActionBarTitle();
        if (titleRes != 0 && mActionTitle != null) {
            mActionTitle.setText(titleRes);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.destroy();
        ButterKnife.unbind(this);
        AppContext.setPlayState(AppConstant.STATUS_NONE);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
//        if (layoutResID == R.layout.activity_main) {
//            super.setContentView(layoutResID);
//        } else {
//            super.setContentView(getContainer());
//            View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
//            contentView.setBackgroundColor(getResources().getColor(R.color.window_background));
//            swipeBackLayout.addView(contentView);
//        }
    }

//    private View getContainer() {
//        RelativeLayout relativeLayout = new RelativeLayout(this);
//        swipeBackLayout = new SwipeBackLayout(this);
//        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
//        ivShadow = new ImageView(this);
//        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
//        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT);
//        relativeLayout.addView(ivShadow, param);
//        relativeLayout.addView(swipeBackLayout);
//        swipeBackLayout.setOnSwipeBackListener(new SwipeBackLayout.SwipeBackListener() {
//            @Override
//            public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
//                ivShadow.setAlpha(1 - fractionScreen);
//            }
//        });
//        return relativeLayout;
//    }


    protected abstract int layoutResID();

    protected abstract void initView();

    protected abstract boolean supportActionBar();

    protected abstract boolean supportBottomPlay();

    public int getActionBarTitle() {
        return R.string.app_name;
    }

    ;

    public boolean hasBackButton() {
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
