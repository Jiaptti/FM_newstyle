package com.fastapp.viroyal.fm_newstyle.ui.album;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/7.
 */

public class AlbumPresenter extends AlbumContract.Presenter{

    @Override
    void getAlbumsList(int albumId, int pageSize) {
        getManager().add(model.getAlbums(albumId, pageSize)
                .compose(RxSchedulers.<Data<HimalayanBean>>io_main())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        view.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data<HimalayanBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                Log.i(AppConstant.TAG, "AlbumPresenter error = " + throwable.getMessage());
                if (throwable instanceof SocketTimeoutException) {
                    ErrorBean errorBean = new ErrorBean();
                    errorBean.setClazz(AlbumVH.class);
                    getManager().post(AppConstant.LOADING_STATUS, null);
                    getManager().post(AppConstant.ERROR_MESSAGE, errorBean);
                } else if (throwable instanceof ConnectException) {

                } else {

                }
            }

            @Override
            public void onNext(Data<HimalayanBean> himalayanBeanData) {
//                view.showAlbumMessage(himalayanBeanData);
            }
        }));
    }
}
