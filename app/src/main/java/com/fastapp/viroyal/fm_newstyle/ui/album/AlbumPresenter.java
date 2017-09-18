package com.fastapp.viroyal.fm_newstyle.ui.album;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.base.BaseSubscriber;
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
//    @Override
//    void getAlbumsList(int albumId, int pageSize) {
//        getManager().add(model.getAlbums(albumId, pageSize)
//                .compose(RxSchedulers.<Data<HimalayanBean>>io_main())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
////                        view.showLoading();
//                        errorBean.setClazz(AlbumVH.class);
//                        errorBean.setCode(AppConstant.REQUEST_BODY_ERROR);
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<Data<HimalayanBean>>(mContext,errorBean) {
//            @Override
//            public void onCompleted() {
//            }
//            @Override
//            public void onError(Throwable throwable) {
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void onNext(Data<HimalayanBean> himalayanBeanData) {
////                view.showAlbumMessage(himalayanBeanData);
//            }
//        }));
//    }
}
