package com.fastapp.viroyal.fm_newstyle.ui.navigation;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.view.adapter.NavigationAdapter;
import com.fastapp.viroyal.fm_newstyle.view.layout.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationActivity extends BaseActivity<NavigationPresenter, NavigationModel> implements NavigationContract.View{
    @Bind(R.id.navigation_content)
    RecyclerView mRecycleView;

    private GridLayoutManager mGridLayoutManager;
    private NavigationAdapter mAdapter;

    @Override
    protected int layoutResID() {
        return R.layout.navigation_layout;
    }

    @Override
    protected void initView() {
        mGridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.HORIZONTAL));
        mRecycleView.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.VERTICAL));
    }


    @Override
    public void showNavigation(List<NavigationBean> list) {
        mAdapter = new NavigationAdapter(mContext, list);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
