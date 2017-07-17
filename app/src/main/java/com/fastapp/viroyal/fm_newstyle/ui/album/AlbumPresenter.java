package com.fastapp.viroyal.fm_newstyle.ui.album;

import com.fastapp.viroyal.fm_newstyle.data.base.Data;
import com.fastapp.viroyal.fm_newstyle.data.entity.HimalayanBean;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/7.
 */

public class AlbumPresenter extends AlbumContract.Presenter{

    @Override
    void getAlbumsList(int albumId) {
        manager.add(model.getAlbums(albumId).subscribe(new Action1<Data<HimalayanBean>>() {
            @Override
            public void call(Data<HimalayanBean> himalayanBeanData) {
                view.showAlbumMessage(himalayanBeanData);
            }
        }));
    }
}
