package com.fastapp.viroyal.fm_newstyle.base;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public class RxManager {
    private RxBus mRxBus = RxBus.newInstance();
    private ConcurrentHashMap<String, Observable<?>> mObservables = new ConcurrentHashMap<>();
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void on(String tag, Action1 action1) {
        Observable<?> observable = mRxBus.regiest(tag);
        mObservables.put(tag, observable);
        mCompositeSubscription.add(observable.compose(RxSchedulers.io_main()).subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void add(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    public void clear() {
        mCompositeSubscription.unsubscribe();
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mRxBus.unRegiest(entry.getKey(), entry.getValue());
        }
    }

    public void clear(String key) {
        mCompositeSubscription.unsubscribe();
        Observable observable = mObservables.get(key);
        mRxBus.unRegiest(key, observable);
    }

    public <T> void post(Object tag, T content) {
        mRxBus.post(tag, content);
    }
}
