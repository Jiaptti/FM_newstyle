package com.fastapp.viroyal.fm_newstyle.view.fragment.recent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseFragment;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.ui.settings.SettingsActivity;
import com.fastapp.viroyal.fm_newstyle.util.DialogUtils;
import com.fastapp.viroyal.fm_newstyle.view.adapter.recent.RecentAdapter;
import com.fastapp.viroyal.fm_newstyle.view.layout.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentFragment extends BaseFragment<RecentPresenter, RecentModel> implements RecentContract.View{
    @Bind(R.id.recent_no_content)
    ImageView mNoContent;
    @Bind(R.id.recent_content)
    RecyclerView mRecycleView;

    private GridLayoutManager mGridLayoutManager;
    private RecentAdapter mAdapter;
    private RealmHelper helper;

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
        helper = AppContext.getRealmHelper();
        ((SettingsActivity) getActivity()).setFragmentTitle(AppContext.getStringById(R.string.settings_recent_listener));
        ((SettingsActivity) getActivity()).setActionClearState(View.VISIBLE);
        ((SettingsActivity) getActivity()).setActionClearListener(ClearRecordListener);
        mGridLayoutManager = new GridLayoutManager(mContext, AppConstant.SPAN_COUNT_ONE);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL));
        presenter.getManager().on(AppConstant.PLAY_RECENT_STATE_CHANTE, new Action1() {
            @Override
            public void call(Object o) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    View.OnClickListener ClearRecordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogUtils.getConfirmDialog(mContext, AppContext.getStringById(R.string.confirm_clear_record),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            helper.removeAllRecent();
                            presenter.getRecentList();
                            presenter.getManager().post(AppConstant.RESET_DATA, null);
                            dialogInterface.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    };

    @Override
    public void showRecentCategory(List<TracksBeanList> data) {
        ((SettingsActivity) getActivity()).setActionClearEnable(true);
        mAdapter = new RecentAdapter(mContext, data, presenter.getManager());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void noContent() {
        ((SettingsActivity) getActivity()).setActionClearEnable(false);
        mNoContent.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((SettingsActivity) getActivity()).setActionClearState(View.GONE);
        ((SettingsActivity) getActivity()).setActionClearListener(null);
        ((SettingsActivity) getActivity()).setActionBarTitle(AppContext.getStringById(R.string.settings_title));
    }
}
