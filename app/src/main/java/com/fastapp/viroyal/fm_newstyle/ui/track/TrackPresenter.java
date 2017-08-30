package com.fastapp.viroyal.fm_newstyle.ui.track;


import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackPresenter extends TrackContract.Presenter {
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
//                        view.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<TrackInfoBean>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                throwable.printStackTrace();
                                Log.i(AppConstant.TAG, "getTrack error = " + throwable.getMessage());
                                if (throwable instanceof SocketTimeoutException) {
                                    getManager().post(AppConstant.LOADING_STATUS, null);
                                    ErrorBean errorBean = new ErrorBean();
                                    errorBean.setClazz(TrackListVH.class);
                                    getManager().post(AppConstant.ERROR_MESSAGE, errorBean);
                                } else if (throwable instanceof ConnectException) {

                                } else {

                                }
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
                            public void onError(Throwable throwable) {
                                throwable.printStackTrace();
                                Log.i(AppConstant.TAG, "getAlumList error = " + throwable.getMessage());
                                if (throwable instanceof SocketTimeoutException) {
                                    getManager().post(AppConstant.LOADING_STATUS, null);
                                    ErrorBean errorBean = new ErrorBean();
                                    errorBean.setClazz(TrackListVH.class);
                                    getManager().post(AppConstant.ERROR_MESSAGE, errorBean);
                                } else if (throwable instanceof ConnectException) {

                                } else {

                                }
                            }

                            @Override
                            public void onNext(Data<HimalayanBean> himalayanBeanData) {
                                view.loadAlbumList(himalayanBeanData.getData().getTracks().getList());
                            }
                        }
                ));
    }

    @Override
    void getNowTrack() {
        view.setNowPlayerTrack(model.getNowPlayTrack());
    }
}
