package com.fastapp.viroyal.fm_newstyle.ui.home;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.view.fragment.adapter.FragmentAdapter;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CategoryVH;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View{
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.content_view_pager)
    ViewPager mContentPager;
    @Bind(R.id.net_error_layout)
    LinearLayout errorLayout;
    @Bind(R.id.reload)
    TextView reload;
    private long firstClickTime;


    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        presenter.getManager().on(AppConstant.ERROR_MESSAGE, new Action1() {
            @Override
            public void call(Object o) {
                ErrorBean errorBean = (ErrorBean)o;
                if(errorBean.getClazz() == CategoryVH.class){
                    errorLayout.setVisibility(View.VISIBLE);
                    mContentPager.setVisibility(View.GONE);
                }
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorLayout.setVisibility(View.GONE);
                mContentPager.setVisibility(View.VISIBLE);
                presenter.getTabList();
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
    public void showTabFragment(List<Fragment> fragments) {
        mContentPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(CommonUtils.getAllTabs())));
        mContentPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mContentPager);
    }

    @Override
    public boolean supportActionBar() {
        return false;
    }

    @Override
    public boolean supportBottomPlay() {
        return true;
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - firstClickTime < 2000){
            AppContext.getInstance().unBindMediaService();
            super.onBackPressed();
        } else {
            firstClickTime = System.currentTimeMillis();
            AppContext.toastShort(R.string.tip_click_back_again_to_exist);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
