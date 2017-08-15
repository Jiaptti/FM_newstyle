package com.fastapp.viroyal.fm_newstyle.ui.track;


import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackPresenter extends TrackContract.Presenter{
    @Override
    protected void onStart() {
    }

    @Override
    void getTrack(int trackId) {
        getManager().add(model.getTrackInfoBean(trackId)
                .compose(RxSchedulers.<TrackInfoBean>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                new Subscriber<TrackInfoBean>() {
                    @Override
                    public void onCompleted() {
                        view.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i(AppConstant.TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(TrackInfoBean trackInfoBean) {
                        view.setNowPlayerMessage(trackInfoBean);
                    }
                }
        ));
    }

    @Override
    void getAlumList(int albumId, int pageSize) {
        getManager().add(model.getAlbumList(albumId, pageSize)
                .compose(RxSchedulers.<Data<HimalayanBean>>io_main()).subscribe(
                        new Subscriber<Data<HimalayanBean>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.i(AppConstant.TAG, e.getMessage());
                            }

                            @Override
                            public void onNext(Data<HimalayanBean> himalayanBeanData) {
                                view.loadAlbumList(himalayanBeanData);
                            }
                        }
                ));
    }
}
