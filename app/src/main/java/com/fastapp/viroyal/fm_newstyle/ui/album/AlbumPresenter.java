package com.fastapp.viroyal.fm_newstyle.ui.album;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/7.
 */

public class AlbumPresenter extends AlbumContract.Presenter{

    @Override
    void getAlbumsList(int albumId) {
        getManager().add(model.getAlbums(albumId).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<Data<HimalayanBean>>io_main()).subscribe(new Observer<Data<HimalayanBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Data<HimalayanBean> himalayanBeanData) {
                view.showAlbumMessage(himalayanBeanData);
            }
        }));
    }
}
