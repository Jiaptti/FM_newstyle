package com.fastapp.viroyal.fm_newstyle.base;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public abstract class BasePresenter<V , M> {
    public RxManager manager = new RxManager();
    public V view;
    public M model;

    public void setVM(V view, M model){
        this.view = view;
        this.model = model;
        this.onStart();
    }

    protected abstract void onStart();

    public void destroy(){
        manager.clear();
    }
}
