package com.fastapp.viroyal.fm_newstyle.ui.track;


import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksInfo;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observer;
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
    void getTracksInfo(int trackId) {
        getManager().add(model.getTracksInfo(trackId)
                .compose(RxSchedulers.<TracksInfo>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TracksInfo>() {
                    @Override
                    public void onCompleted() {
                        view.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        if (throwable instanceof SocketTimeoutException) {
                            ErrorBean errorBean = new ErrorBean();
                            errorBean.setClazz(TrackListVH.class);
                            getManager().post(AppConstant.LOADING_STATUS, null);
                            getManager().post(AppConstant.ERROR_MESSAGE, errorBean);
                        } else if (throwable instanceof ConnectException) {

                        } else {

                        }
                    }

                    @Override
                    public void onNext(TracksInfo tracksInfo) {
                        view.initTracksInfo(tracksInfo);
                    }
                }));

    }

    @Override
    void getNowTrack() {
        view.setNowPlayerTrack(model.getNowPlayTrack());
    }
}
