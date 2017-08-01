package com.fastapp.viroyal.fm_newstyle.base;



public abstract class BasePresenter<V , M> {
    private RxManager manager;
    public V view;
    public M model;

    public void setVM(V view, M model){
        this.view = view;
        this.model = model;
        manager = new RxManager();
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
        manager.clear();
    }
}
