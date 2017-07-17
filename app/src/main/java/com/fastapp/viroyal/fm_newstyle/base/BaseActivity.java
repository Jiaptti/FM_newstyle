package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.SwipeBackLayout;

import butterknife.ButterKnife;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public abstract class BaseActivity <T extends BasePresenter, E extends BaseModel> extends AppCompatActivity{
    public Context mContext;
    public T presenter;
    public E model;
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutResID());
        ButterKnife.bind(this);
        mContext = this;
        presenter = TUtils.getT(this, 0);
        model = TUtils.getT(this, 1);
        if(this instanceof BaseView) presenter.setVM(this, model);
        this.initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null)presenter.destroy();
        ButterKnife.unbind(this);
    }

    public void reload(){
        Intent intent = getIntent();
        overridePendingTransition(0 , 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0 , 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if(layoutResID == R.layout.activity_main){
            super.setContentView(layoutResID);
        } else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.window_background));
            swipeBackLayout.addView(view);
        }
    }

    private View getContainer(){
        RelativeLayout relativeLayout = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(ivShadow, param);
        relativeLayout.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener(new SwipeBackLayout.SwipeBackListener() {
            @Override
            public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
                ivShadow.setAlpha(1 - fractionScreen);
            }
        });
        return relativeLayout;
    }

    protected abstract int layoutResID();
    protected abstract void initView();
}
