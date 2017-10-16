package com.fastapp.viroyal.fm_newstyle.view.fragment.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseFragment;
import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.cache.CacheCleanManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.util.DialogUtils;
import com.fastapp.viroyal.fm_newstyle.view.fragment.recent.RecentFragment;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hanjiaqi on 2017/9/22.
 */

public class SettingsFragment extends BaseFragment<BasePresenter, BaseModel> {
    @Bind(R.id.settings_collect_layout)
    LinearLayout mCollectLayout;
    @Bind(R.id.settings_recent_layout)
    LinearLayout mRecentLayout;
    @Bind(R.id.settings_cache_size)
    TextView mCacheSize;
    @Bind(R.id.settings_cache_layout)
    LinearLayout mCacheLayout;
    @Bind(R.id.app_version)
    TextView mVersion;
    @Bind(R.id.settings_update_layout)
    LinearLayout mUpdateLayout;
    @Bind(R.id.settings_recent_count)
    TextView mRecentCount;
    @Bind(R.id.settings_collect_count)
    TextView mCollectCount;

    private RealmHelper helper;
    private RxManager manager = new RxManager();

    @Override
    protected int layoutResID() {
        return R.layout.settings_layout;
    }

    public static SettingsFragment newInstance(int type) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {
        helper = AppContext.getRealmHelper();
        setNumAsync(mRecentCount, getRecentNum());
        setNumAsync(mCollectCount, getCollectNum());
        mCacheSize.setText(CacheCleanManager.getFormatSize(CacheCleanManager.getFolderSize(new File(AppConstant.NET_DATA_PATH))));
        manager.on(AppConstant.RESET_DATA, new Action1() {
            @Override
            public void call(Object o) {
                setNumAsync(mRecentCount, getRecentNum());
                setNumAsync(mCollectCount, getCollectNum());
            }
        });
    }

    private void setNumAsync(final TextView tv, int num) {
        Observable.just(num).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer + "";
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                tv.setText(s);
            }
        });
    }

    @OnClick({R.id.settings_collect_layout, R.id.settings_recent_layout, R.id.settings_cache_layout, R.id.settings_update_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_collect_layout:
                break;
            case R.id.settings_recent_layout:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(this);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(R.id.settings_main, RecentFragment.newInstance(AppConstant.SETTINGS_RECENT));
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.settings_cache_layout:
                showClearDialog();
                break;
            case R.id.settings_update_layout:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        manager.clear(AppConstant.RESET_DATA);
    }

    private int getRecentNum() {
        return helper.getAllRecent().size();
    }

    private int getCollectNum() {
        return helper.getAllCollect().size();
    }

    private void showClearDialog() {
        DialogUtils.getConfirmDialog(mContext, getString(R.string.confirm_clear_record), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CacheCleanManager.cleanCustomCache(new File(AppConstant.NET_DATA_PATH));
                mCacheSize.setText(CacheCleanManager.getFormatSize(CacheCleanManager.getFolderSize(new File(AppConstant.NET_DATA_PATH))));
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
