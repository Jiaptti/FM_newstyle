package com.fastapp.viroyal.fm_newstyle.ui.album;


import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class AlbumModel implements AlbumContract.Model{

//    @Override
//    public Observable<Data<HimalayanBean>> getAlbums(int albumId, int pageSize) {
//        return Api.getInstance().getApiService().getAlbumsList(albumId, pageSize).compose(RxSchedulers.<Data<HimalayanBean>>io_main());
//    }
}
