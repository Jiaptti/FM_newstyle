package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.util.NetWorkUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by hanjiaqi on 2017/9/15.
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private ErrorBean errorBean;
    private RxManager manager = new RxManager();

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context, ErrorBean errorBean) {
        mContext = context;
        this.errorBean = errorBean;
    }

    @Override
    public void onStart() {
        if (!NetWorkUtils.hasNet()) {
            return;
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        manager.post(AppConstant.ERROR_MESSAGE, errorBean);
    }

    @Override
    public void onNext(T t) {
    }
}
