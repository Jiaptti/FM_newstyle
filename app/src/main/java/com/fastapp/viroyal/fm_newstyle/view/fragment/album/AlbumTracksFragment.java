package com.fastapp.viroyal.fm_newstyle.view.fragment.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.util.JsonUtils;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;


import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/8/24.
 */

public class AlbumTracksFragment extends BaseListFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments().getInt(AppConstant.TYPE) > 100){
            manager.on(AppConstant.UPDATE_ITEM_STATUS, new Action1() {
                @Override
                public void call(Object o) {
                    mTRecyclerView.getAdapter().notifyDataSetChanged();
                }
            });
            manager.on(AppConstant.SAVE_DATA, new Action1() {
                @Override
                public void call(Object o) {
                    if(o instanceof ErrorBean && ((ErrorBean)o).getClazz() == AlbumVH.class){
                        JsonUtils.saveData(mTRecyclerView.getAdapter().getData(), mTRecyclerView.getMaxCount(),
                                mTRecyclerView.getMaxPageId(),AppContext.getTempPageId());
                    }
                }
            });
        }
        return mTRecyclerView.setView(TUtils.forName(getArguments().getString(AppConstant.VH_CLASS)), getArguments().getInt(AppConstant.TYPE));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mTRecyclerView != null){
            mTRecyclerView.sendRequest();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        manager.clear(AppConstant.UPDATE_ITEM_STATUS);
        manager.clear(AppConstant.SAVE_DATA);
    }
}
