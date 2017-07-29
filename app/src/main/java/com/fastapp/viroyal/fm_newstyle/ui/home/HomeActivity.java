package com.fastapp.viroyal.fm_newstyle.ui.home;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.fastapp.viroyal.fm_newstyle.view.layout.FragmentAdapter;
import com.fastapp.viroyal.fm_newstyle.view.popuwindow.NowPlayingWindow;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View{
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.content_view_pager)
    ViewPager mContentPager;
    private NowPlayingWindow mPlayingWindow;
    private long firstClickTime;

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return;
            } else {

            }
        } else {
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void dismissLoading() {
        if(mPlayingWindow == null){
            mPlayingWindow = NowPlayingWindow.getPlayingWindow(mContext);
            mPlayingWindow.show();
        }
    }

    @Override
    public void showTabFragment(List<Fragment> fragments) {
        mContentPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(CommonUtils.getAllTabs())));
        mContentPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mContentPager);
    }

    @Override
    protected boolean supportActionBar() {
        return false;
    }


    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - firstClickTime < 2000){
            super.onBackPressed();
        } else {
            firstClickTime = System.currentTimeMillis();
            AppContext.toastShort(R.string.tip_click_back_again_to_exist);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPlayingWindow != null){
            mPlayingWindow.dismiss();
        }
    }
}
