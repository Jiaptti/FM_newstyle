package com.fastapp.viroyal.fm_newstyle.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by hanjiaqi on 2017/7/1.
 */

public class BaseListFragment extends Fragment{
    private TRecyclerView mTRecyclerView;

    /**
     * @param vh 传入VH的类名
     * @param type tab的名字
     * @return
     */
    public static BaseListFragment newInstance(Class<? extends BaseViewHolder> vh,int type){
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.TYPE, type);
        bundle.putString(AppConstant.VH_CLASS, vh.getCanonicalName());
        BaseListFragment fragment = new BaseListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTRecyclerView = new TRecyclerView(getContext());
        mTRecyclerView.setViewByTab(TUtils.forName(getArguments().getString(AppConstant.VH_CLASS)),getArguments().getInt(AppConstant.TYPE));
        return mTRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mTRecyclerView != null) mTRecyclerView.sendRequest();
    }
}
