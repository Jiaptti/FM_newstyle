package com.fastapp.viroyal.fm_newstyle.view.fragment.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.ui.navigation.NavigationActivity;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;

/**
 * Created by hanjiaqi on 2017/8/24.
 */

public class CategoryFragment extends BaseListFragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NavigationActivity)getActivity()).setSwitchState(View.VISIBLE);
        ((NavigationActivity)getActivity()).setSwitchListener(switchListener);
    }

    private View.OnClickListener switchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchLayout();
            switchIcon();
        }
    };

    public void switchLayout() {
        if (mTRecyclerView.mGridLayoutManager.getSpanCount() == AppConstant.SPAN_COUNT_ONE) {
            mTRecyclerView.mGridLayoutManager.setSpanCount(AppConstant.SPAN_COUNT_THREE);
        } else {
            mTRecyclerView.mGridLayoutManager.setSpanCount(AppConstant.SPAN_COUNT_ONE);
        }
        mTRecyclerView.getAdapter().notifyItemRangeChanged(0, mTRecyclerView.getAdapter().getItemCount());
    }

    public void switchIcon() {
        if (mTRecyclerView.mGridLayoutManager.getSpanCount() == AppConstant.SPAN_COUNT_THREE) {
            ((NavigationActivity)getActivity()).setSwitchBackground(R.drawable.ic_span_3);
        } else {
            ((NavigationActivity)getActivity()).setSwitchBackground(R.drawable.ic_span_1);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mTRecyclerView.setView(TUtils.forName(getArguments().getString(AppConstant.VH_CLASS)), getArguments().getInt(AppConstant.TYPE));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mTRecyclerView != null){
            mTRecyclerView.setTitle(((NavigationActivity)getActivity()).getActionTitle());
            mTRecyclerView.sendRequest();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((NavigationActivity)getActivity()).setSwitchState(View.GONE);
        ((NavigationActivity)getActivity()).setSwitchListener(null);
        ((NavigationActivity) getActivity()).setFragmentTitle(AppContext.getStringById(R.string.navigation_title));
    }
}
