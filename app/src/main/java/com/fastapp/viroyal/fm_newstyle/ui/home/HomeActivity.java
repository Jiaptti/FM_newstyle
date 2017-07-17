package com.fastapp.viroyal.fm_newstyle.ui.home;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;
import com.fastapp.viroyal.fm_newstyle.util.test.TestUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.FragmentAdapter;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CategoryVH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Action1;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View{
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.content_view_pager)
    ViewPager mContentPager;

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void showTabList(String[] tabs) {
        final List<Fragment> fragments = new ArrayList<>();
        Observable.from(tabs).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                fragments.add(BaseListFragment.newInstance(CategoryVH.class, getType(s)));
            }
        });
        mContentPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(tabs)));
        mTabLayout.setupWithViewPager(mContentPager);
//        TestUtils.testData1();
    }

    private int getType(String str){
        int type = AppConstant.PAGE_CROSSTALK;
        if(str.equalsIgnoreCase(getString(R.string.tabs_crosstalk_sketch))){
            type =  AppConstant.PAGE_CROSSTALK;
        } else if(str.equalsIgnoreCase(getString(R.string.tabs_exquisite_article))){
            type = AppConstant.PAGE_STORY;
        } else if(str.equalsIgnoreCase(getString(R.string.tabs_literati_writings))){
            type = AppConstant.PAGE_BOOK;
        }
        return type;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
