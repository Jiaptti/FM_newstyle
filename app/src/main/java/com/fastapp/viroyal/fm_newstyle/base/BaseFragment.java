package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.util.TUtils;

import butterknife.ButterKnife;

/**
 * Created by hanjiaqi on 2017/9/19.
 */

public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    public Context mContext;
    public T presenter;
    public E model;
    public RealmHelper realmHelper;
    public RxManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.destroy();
        ButterKnife.unbind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResID(), container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        presenter = TUtils.getT(this, 0);
        model = TUtils.getT(this, 1);
        if (this instanceof BaseView) presenter.setVM(this, model);
        realmHelper = AppContext.getRealmHelper();
        manager = new RxManager();
        this.initView();
        return view;
    }


    protected abstract int layoutResID();

    protected abstract void initView();

}