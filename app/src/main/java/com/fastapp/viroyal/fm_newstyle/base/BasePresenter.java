package com.fastapp.viroyal.fm_newstyle.base;


import android.content.Context;

import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;

public abstract class BasePresenter<V , M> {
    private RxManager manager;
    public V view;
    public M model;
    public ErrorBean errorBean;
    public Context mContext = AppContext.getAppContext();

    public void setVM(V view, M model){
        this.view = view;
        this.model = model;
        manager = new RxManager();
        errorBean = new ErrorBean();
        this.onStart();
    }

    public RxManager getManager(){
        if(manager == null){
            manager = new RxManager();
        }
        return manager;
    }

    protected abstract void onStart();

    /**
     * Created by hanjiaqi on 2017/6/26.
     */ public void destroy(){
        if(manager != null)
            manager.clear();
    }
}
