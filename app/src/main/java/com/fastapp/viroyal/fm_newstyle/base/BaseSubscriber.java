package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.util.NetWorkUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by hanjiaqi on 2017/9/15.
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private TRecyclerView recycle;

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context, TRecyclerView mTRecyclerView) {
        mContext = context;
        this.recycle = mTRecyclerView;
    }

    @Override
    public void onStart() {
        if (!NetWorkUtils.hasNet()) {
            return;
        }

    }

    @Override
    public void onCompleted() {
        recycle.setRefreshLoadedState();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof SocketTimeoutException) {
            recycle.begin = 0;
            recycle.setRefreshLoadedState();
            recycle.mRxManager.post(AppConstant.LOADING_STATUS, null);
            recycle.errorBean.setCode(AppConstant.RECYCLE_ERROR_CODE);
            recycle.mRxManager.post(AppConstant.ERROR_MESSAGE, recycle.errorBean);
        } else if (throwable instanceof ConnectException) {

        } else if (throwable instanceof NullPointerException) {

        }
    }

    @Override
    public void onNext(T t) {

    }
}
