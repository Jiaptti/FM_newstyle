package com.fastapp.viroyal.fm_newstyle.base;

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

    public void on(String tag, Action1 action1){
        Observable<?> observable = mRxBus.regiest(tag);
        mObservables.put(tag, observable);
        mCompositeSubscription.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(action1, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        }));
    }

    public void add(Subscription subscription){
        mCompositeSubscription.add(subscription);
    }

    public void clear(){
        mCompositeSubscription.unsubscribe();
        for(Map.Entry<String, Observable<?>> entry : mObservables.entrySet()){
            mRxBus.unRegiest(entry.getKey(), entry.getValue());
        }
    }

    public void post(Object tag, Objects content){
        mRxBus.post(tag, content);
    }
}
