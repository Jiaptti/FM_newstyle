package com.fastapp.viroyal.fm_newstyle.view.fragment.recent;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseFragment;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.ui.settings.SettingsActivity;
import com.fastapp.viroyal.fm_newstyle.view.adapter.recent.RecentAdapter;
import com.fastapp.viroyal.fm_newstyle.view.layout.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentFragment extends BaseFragment<RecentPresenter, RecentModel> implements RecentContract.View{
    @Bind(R.id.recent_no_content)
    TextView mNoContent;
    @Bind(R.id.recent_content)
    RecyclerView mRecycleView;

    private GridLayoutManager mGridLayoutManager;
    private RecentAdapter mAdapter;


    public static RecentFragment newInstance(int type) {
        RecentFragment fragment = new RecentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int layoutResID() {
        return R.layout.recent_layout;
    }

    @Override
    protected void initView() {
        ((SettingsActivity) getActivity()).setFragmentTitle(AppContext.getStringById(R.string.settings_recent_listener));
        mGridLayoutManager = new GridLayoutManager(mContext, AppConstant.SPAN_COUNT_ONE);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL));
        updateUI();
    }

    private void updateUI(){
        if(AppContext.getRealmHelper().getAllRecent().size() == 0){
            mNoContent.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.GONE);
        } else {
            mNoContent.setVisibility(View.GONE);
            mRecycleView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRecentCategory(List<HimalayanEntity> data) {
        mAdapter = new RecentAdapter(mContext, data);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
