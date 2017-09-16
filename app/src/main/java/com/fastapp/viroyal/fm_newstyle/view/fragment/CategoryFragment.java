package com.fastapp.viroyal.fm_newstyle.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;

/**
 * Created by hanjiaqi on 2017/8/24.
 */

public class CategoryFragment extends BaseListFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTRecyclerView.setFragmentTitle(CommonUtils.getTtile(getArguments().getInt(AppConstant.TYPE)));
        return mTRecyclerView.setView(TUtils.forName(getArguments().getString(AppConstant.VH_CLASS)), getArguments().getInt(AppConstant.TYPE));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mTRecyclerView != null) mTRecyclerView.sendRequest();
    }
}
