package com.fastapp.viroyal.fm_newstyle.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;


import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/1.
 */

public class BaseListFragment extends Fragment{
    public TRecyclerView mTRecyclerView;
    public RxManager manager = new RxManager();

    /**
     * @param vh 传入VH的类名
     * @param type tab的名字
     * @return
     */
    public static BaseListFragment newInstance(BaseListFragment fragment, Class<? extends BaseViewHolder> vh, int type){
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.TYPE, type);
        bundle.putString(AppConstant.VH_CLASS, vh.getCanonicalName());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTRecyclerView = new TRecyclerView(getContext());
    }

    public interface IFragmentTitle{
        void setFragmentTitle(String title);
    }
}

